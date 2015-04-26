package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Background implements TimeConscious {

    Bitmap                  bitmap1, bitmap2;
    public  int             x1, X1;
    public  int             x2, X2;
    public  int             y1, Y1;
    public  int             y2, Y2;
    public  int             width, height;
    Rect                    dst1, dst2;

    public Background ( DashTillPuffSurfaceView view ) {
        //Load background bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bitmap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffwallpaper, options);
        this.bitmap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffwallpaper, options);

        //Initialize background position
        this.width = view.getWidth();
        this.height = view.getHeight();
        this.x1 = 0;
        this.x2 = width;
        this.y1 = 0;
        this.y2 = height;
        this.X1 = this.x2;
        this.X2 = this.X1 + width;
        this.Y1 = 0;
        this.Y2 = height;
        dst1 = new Rect( x1, y1, x2, y2 ); // Where to draw .
        dst2 = new Rect( X1, Y1, X2, Y2 );
    }

    public void setLocation( int x1, int y1, int x2, int y2, int X1, int Y1, int X2, int Y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        dst1.set(x1, y1, x2, y2);

        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        dst2.set(X1, Y1, X2, Y2);
    }

    public void tick( Canvas canvas ) {
        this.x1 -= 30;
        this.x2 -= 30;
        this.X1 -= 30;
        this.X2 -= 30;
        if (this.x2 <= 0) {
            this.x1 = this.X2;
            this.x2 = this.x1 + width;
        }
        else if (this.X2 <= 0) {
            this.X1 = this.x2;
            this.X2 = this.X1 + width;
        }
        setLocation( x1, y1, x2, y2, X1, Y1, X2, Y2);
        draw (canvas);
    }

    protected void draw ( Canvas c ) {
        Paint paint = new Paint();
        c.drawBitmap ( bitmap1, null, dst1, paint );
        c.drawBitmap ( bitmap2, null, dst2, paint );
    }
}