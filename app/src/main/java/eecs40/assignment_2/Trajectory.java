package eecs40.assignment_2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class Trajectory implements TimeConscious {
    private ArrayList<Point2D> points = new ArrayList<>();
    private float screenWidth;
    private float screenHeight;

    public Trajectory(View view){
        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
    }

    @Override
    public void tick ( Canvas canvas ) {
        // As time ticks , append more points to the trajectory and
        // discard those points that have crossed the entire
        // span of the screen .
        //...
        if(points.isEmpty()){
            points.add(new Point2D(screenWidth/2,screenHeight/2));
        }
        Random ran = new Random();
        float xPos = (float)ran.nextInt((int)(screenWidth-points.get(points.size()-1).x))+points.get(points.size()-1).x;
        float yPos = (float)ran.nextInt((int) screenHeight);
        Point2D p = new Point2D(xPos,yPos);
        points.add(p);

        for(int i = 0; i<points.size(); i++){
            if(points.get(i).x<0){
                points.remove(i);
                i--;
            }
        }

        draw ( canvas ) ;
    }

    protected void draw ( Canvas c ) {
        //...
        Path path = new Path () ;
        path.moveTo (points.get(0).x,points.get(0).y); // Move to first point
        for ( int i = 1; i < points.size(); ++ i ) {
            Point2D p = points.get(i);
            path.lineTo ( p.x, p.y ) ;
        }

        Paint paint = new Paint ();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAlpha(100);
        paint.setAntiAlias(true);
        c.drawPath( path, paint );
    }
}