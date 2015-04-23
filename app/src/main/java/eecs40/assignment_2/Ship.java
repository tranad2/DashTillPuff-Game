package eecs40.assignment_2;

/**
 * Created by Alex on 4/23/2015.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ship implements TimeConscious{

    Bitmap bitmapShip;
    float x, y, width, height;
    float dy;
    float gravity = 10f;
    Rect rec;

    public Ship(int x, int y, DashTillPuffSurfaceView view){
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmapShip = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffspaceship, options);
        width = bitmapShip.getWidth();
        height = bitmapShip.getHeight();
        bitmapShip = Bitmap.createScaledBitmap(bitmapShip, (int)width/2, (int)height/2, true);
        width/=2;
        height/=2;
        this.x = x;
        this.y = y;
        rec = new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
    }

    public void setLocation(float xPos, float yPos){
        this.x = xPos;
        this.y = yPos;
        rec.set((int)x,(int)y,(int)(x+width),(int)(y+height));
    }

    public void setDy(float yVel){
        dy = yVel;
    }

    public void tick(Canvas c){
        y+=(dy+gravity);
        setLocation(x,y);
        draw(c);
    }


    public void draw(Canvas c){
        Paint paint = new Paint();
        c.drawBitmap ( bitmapShip, null, rec, paint );
    }
}
