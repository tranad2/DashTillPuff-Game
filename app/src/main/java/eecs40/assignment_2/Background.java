package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Background implements TimeConscious {

    private final DashTillPuffSurfaceView   view;
    Bitmap                                  bitmap1, bitmap2;
    public  int                             x1, X1;
    public  int                             x2, X2;
    public  int                             y1, Y1;
    public  int                             y2, Y2;
    public  int                             width, height;

    public Background ( DashTillPuffSurfaceView view ) {
        this.view = view;
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bitmap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffwallpaper, options);
        this.bitmap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffwallpaper, options);
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
    }

    public void tick( Canvas canvas ) {
        this.x1 -= 10;
        this.x2 -= 10;
        this.X1 -= 10;
        this.X2 -= 10;
        if (this.x2 <= 0) {
            this.x1 = this.X2;
            this.x2 = this.x1 + width;
        }
        if (this.X2 <= 0) {
            this.X1 = this.x2;
            this.X2 = this.X1 + width;
        }
        draw (canvas);
    }

    protected void draw ( Canvas c ) {

        Paint paint = new Paint();
        Rect dst1 = new Rect( x1, y1, x2, y2 ); // Where to draw .
        Rect dst2 = new Rect( X1, Y1, X2, Y2 );
        c.drawBitmap ( bitmap1, null, dst1, paint );
        c.drawBitmap ( bitmap2, null, dst2, paint );

    }
}