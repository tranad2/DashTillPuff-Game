package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Background implements TimeConscious {

    private final DashTillPuffSurfaceView   view;
    public  int                             x1;
    public  int                             x2;
    public  int                             y1;
    public  int                             y2;

    public Background ( DashTillPuffSurfaceView view ) {
        this.view = view;
        this.x1 = 0;
        this.x2 = view.getWidth();
        this.y1 = 0;
        this.y2 = view.getHeight();
    }

    public void tick( Canvas canvas ) {
        draw (canvas);
    }

    protected void draw ( Canvas c ) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffwallpaper, options);

        Paint paint = new Paint() ;
        paint.setAlpha(255); // Control transparency
        Rect dst = new Rect( x1 , y1 , x2 , y2 ) ; // Where to draw .
        c.drawBitmap ( bitmap, null, dst, paint );
    }
}