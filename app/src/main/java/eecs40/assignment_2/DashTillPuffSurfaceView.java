package eecs40.assignment_2;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DashTillPuffSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private DashTillPuffRenderThread    renderThread;
    Background bg;
    Trajectory traj;
    Ship ship;

    public DashTillPuffSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new DashTillPuffRenderThread( this );
        renderThread.start();
        //...
        // Create the sliding background , cosmic factory , trajectory
        // and the space ship
        bg = new Background(this);
        traj = new Trajectory(this);
        ship = new Ship(this);
        //...
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Respond to surface changes, e.g., aspect ratio changes.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The cleanest way to stop a thread is by interrupting it.
        // The thread must regularly check its interrupt flag.
        renderThread.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: // Thrust the space ship up .
                ship.touchFlag = true;
                break;
            case MotionEvent.ACTION_UP: // Let space ship fall freely .
                ship.touchFlag = false;
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        // Draw everything ( restricted to the displayed rectangle ) .
        bg.draw(c);
        traj.draw(c);
        ship.draw(c);
    }

    @Override
    public void tick(Canvas c) {
        // Tick background , space ship , cosmic factory , and trajectory .
        // Draw everything ( restricted to the displayed rectangle ) .

        // Lag debug
        /*Paint paint = new Paint () ;
        paint.setStyle ( Paint. Style . FILL ) ;
        paint.setColor ( Color. WHITE ) ;
        paint.setAntiAlias ( true ) ;
        c.drawPaint ( paint ) ;*/

        bg.tick(c);
        traj.tick(c);
        ship.tick(c);
    }

}