package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Alex on 4/27/2015.
 */
public class Cluster {

    private static final String TAG = "Cluster";
    private float x, y;
    private Bitmap bitmap;
    private int clusWidth, clusHeight;
    private Rect dst;
    private float dx=-30f;  //x-velocity of screen


    public Cluster(Bitmap bmp, int xPos, int yPos, DashTillPuffSurfaceView view){
        bitmap = bmp;
        clusWidth = bmp.getWidth();
        clusHeight = bmp.getHeight();
        x = xPos;
        y = yPos;
        dst = new Rect( xPos-clusWidth/2, yPos-clusHeight/2, xPos+clusWidth/2, yPos+clusHeight/2);
    }

    public void setLocation(int xPos, int yPos){
        dst.set(xPos-clusWidth/2, yPos-clusHeight/2, xPos+clusWidth/2, yPos+clusHeight/2);
    }

    public void tick(Canvas c){
        //Update Cluster location
        x+=dx;
        setLocation((int)x, (int)y);
        draw(c);
    }

    protected void draw(Canvas c){
        Paint paint = new Paint();
        c.drawBitmap ( bitmap, null, dst, paint );
    }

    public Rect getRect(){
        return dst;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return clusWidth;
    }

    public float getHeight(){
        return clusHeight;
    }

    public String toString(){
        return "("+x+","+y+")";
    }

}

