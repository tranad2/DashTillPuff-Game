package eecs40.assignment_2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DashTillPuffRenderThread extends Thread {
    DashTillPuffSurfaceView view;
    private static final int FRAME_PERIOD = 2; // In ms

    public DashTillPuffRenderThread ( DashTillPuffSurfaceView view ) {
        this.view = view;
    }

    public void run () {
        SurfaceHolder sh = view.getHolder();

        // Main game loop .
        while ( !Thread.interrupted() ) {
            Canvas c = sh.lockCanvas( null );
            try {
                synchronized ( sh ) {
                    view.tick ( c );
                }
            } catch ( Exception e ) {
                e.getStackTrace();
            } finally {
                if ( c != null ) {
                    sh.unlockCanvasAndPost( c );
                }
            }
            try {
                Thread.sleep( FRAME_PERIOD );
            } catch ( InterruptedException e ) {
                return;
            }
        }
    }
}