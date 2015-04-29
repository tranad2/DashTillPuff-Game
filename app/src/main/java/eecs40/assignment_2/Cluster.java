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
    float x, y;
    Bitmap bitmap;
    int screenWidth, screenHeight, clusWidth, clusHeight;
    Rect dst;
    private float dx=-30f;  //x-velocity of screen


    public Cluster(Bitmap bmp, int xPos, int yPos, DashTillPuffSurfaceView view){
        bitmap = bmp;
        clusWidth = bmp.getWidth();
        clusHeight = bmp.getHeight();
        x = xPos;
        y = yPos;
        dst = new Rect( xPos, yPos, xPos+clusWidth, yPos+clusHeight);
    }

    public void setLocation(int xPos, int yPos){
        dst.set(xPos, yPos, xPos+clusWidth, yPos+clusHeight);
    }

    public void tick(Canvas c){
        //TODO
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

    public String toString(){
        return "("+x+","+y+")";
    }
}

