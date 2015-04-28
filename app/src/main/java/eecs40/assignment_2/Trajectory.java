package eecs40.assignment_2;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Trajectory implements TimeConscious {

    private static final String TAG = "Trajectory";

    private ArrayList<Point2D> points = new ArrayList<>();
    private float screenWidth;
    private float screenHeight;
    private float dx=-30f;  //x-velocity of screen

    public Trajectory(View view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
    }

    @Override
    public void tick ( Canvas canvas ) {
        // As time ticks , append more points to the trajectory and
        // discard those points that have crossed the entire
        // span of the screen .

        float OFFSET = screenWidth/3;
        if(points.isEmpty()){
            //Initialize first point
            points.add(new Point2D(screenWidth/2,screenHeight/2));
        }
        else{
            //Create new points offscreen only when x-distance is greater than distant between screenWidth+OFFSET and last point
            if((screenWidth+OFFSET)-points.get(points.size()-1).x>=screenWidth/5) {
                Random ran = new Random();
                float xPos = points.get(points.size() - 1).x + OFFSET;
                float yPos = (float) ran.nextInt((int) screenHeight);
                points.add(new Point2D(xPos, yPos));
            }
        }

        //Discards points offscreen
        for(int i = 0; i<points.size(); i++){
            if(points.get(i).x < -OFFSET){
                points.remove(i);
                i--;
            }
            points.get(i).x+=dx;
        }

        //Log.v(TAG,""+points);
        draw ( canvas ) ;
    }

    //Draws Trajectory path
    //@param c Canvas that Trajectory path is drawn on
    protected void draw ( Canvas c ) {

        Path path = new Path () ;
        path.moveTo(points.get(0).x, points.get(0).y); // Move to first point
        for ( int i = 1; i < points.size(); ++ i ) {
            Point2D p = points.get(i);
            path.lineTo ( p.x, p.y ) ;
        }

        Paint paint = new Paint ();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.MAGENTA);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[] {100,50}, 0));
        paint.setAlpha(255);
        paint.setAntiAlias(true);
        c.drawPath( path, paint );
    }
}