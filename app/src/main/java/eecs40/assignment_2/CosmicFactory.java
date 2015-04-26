package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CosmicFactory implements TimeConscious {
    Bitmap                  blackhole, blueplanet, cloud, earth, glossyplanet, goldenstar, neutronstar, shinystar, star1, star2;
    int                     x1, y1, x2, y2, screenWidth, screenHeight;
    Rect                    dst;

    public CosmicFactory( DashTillPuffSurfaceView view) {
        //Load cosmic bitmaps
        BitmapFactory.Options options = new BitmapFactory.Options();
        blackhole = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffblackhole, options);
        blueplanet = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffblueplanet, options);
        cloud = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffcloud, options);
        earth = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffearth, options);
        glossyplanet = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffglossyplanet, options);
        goldenstar = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffgoldenstar, options);
        neutronstar = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffneutronstar, options);
        shinystar = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffshinystar, options);
        star1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffstar1, options);
        star2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dashtillpuffstar2, options);

        dst = new Rect( x1, y1, x2, y2 );

        screenWidth = view.getWidth();
        screenHeight = view.getHeight();
    }


    @Override
    public void tick ( Canvas canvas ) {
        // Create new ‘‘ clusters ’’ of cosmic objects . Alternately place
        // clusters of cosmic objects above and below the guiding
        // trajectory .
        //...

        // Randomly select the type of cluster objects from a list
        // ( e . g . , stars , clouds , planets , etc .) . So all objects in
        // a cluster are of the same type .
        //...

        // Remove cosmic objects ( stars , planets , etc .) that moved out
        // of the scene .
        //...
    }

    protected void draw ( Canvas c ) {
        Paint paint = new Paint();
        c.drawBitmap ( blackhole, null, dst, paint );
    }
}