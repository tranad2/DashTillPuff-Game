package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Ship implements TimeConscious{

    private Bitmap bitmapShip;
    private int x1, y1, x2, y2, shipWidth, shipHeight, screenWidth, screenHeight;
    private float dy;
    private float gravity = 5;
    private Rect dst;
    private boolean visible, touchFlag;

    public Ship( DashTillPuffSurfaceView view ){
        //Load ship bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmapShip = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffspaceship, options);

        //Scale ship bitmap
        shipWidth   = bitmapShip.getWidth() / 4;
        shipHeight  = bitmapShip.getHeight() / 4;
        bitmapShip  = Bitmap.createScaledBitmap(bitmapShip, shipWidth, shipHeight, true);

        //Initialize ship position
        this.x1 = view.getWidth()/4;
        this.y1 = view.getHeight()/2;
        this.x2 = this.x1 + shipWidth;
        this.y2 = this.y1 + shipHeight;
        dst = new Rect( x1, y1, x2, y2 );

        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
        touchFlag = false;
        visible = true;
    }

    public void setLocation( int xPos, int yPos ) {
        this.x1 = xPos;
        this.y1 = yPos;
        this.x2 = this.x1 + shipWidth;
        this.y2 = this.y1 + shipHeight;
        dst.set(x1, y1, x2, y2);
    }

    @Override
    public void tick( Canvas c ){
        //Thrusters
        if ( touchFlag ) {
            y1 += dy * 0.35;
            dy -= gravity;
        }
        //Gravity
        else {
            y1 += dy * 0.35;
            dy += gravity;
        }

        //Keep ship on screen
        if ( y1 > screenHeight - shipHeight ) {     //Bottom bound
            y1 = screenHeight - shipHeight;
            dy = 0;
        }
        else if ( y1 < 0 ) {                        //Top bound
            y1 = 0;
            dy = 0;
        }
        setLocation(x1, y1);
        draw( c );
    }

    protected void draw( Canvas c ){
        if(visible) {
            Paint paint = new Paint();
            c.drawBitmap(bitmapShip, null, dst, paint);
        }
    }

    public Rect getRect(){
        return dst;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setTouchFlag(boolean val){
        touchFlag = val;
    }

    public void setVisible(boolean val){
        visible = val;
    }

    public boolean checkCollision(ArrayList<ArrayList<Cluster>> clusterList){
        boolean collision = false;
        for(ArrayList<Cluster> subList: clusterList){
            for(Cluster c : subList){
                if(dst.intersect(c.getRect())){
                    collision = true;
                }
            }

        }
        return collision;
    }
}
