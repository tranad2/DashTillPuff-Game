package eecs40.assignment_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DashTillPuffSurfaceView extends SurfaceView implements SurfaceHolder.Callback, TimeConscious {
    private DashTillPuffRenderThread    renderThread;
    Background bg;
    Trajectory traj;
    Ship ship;
    CosmicFactory cos;
    boolean startFlag = false;
    int score;

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
        cos = new CosmicFactory(traj, this);
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
                startFlag = true;
                ship.setTouchFlag(true);
                break;
            case MotionEvent.ACTION_UP: // Let space ship fall freely .
                ship.setTouchFlag(false);
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        // Draw everything ( restricted to the displayed rectangle ) .

        bg.draw(c);
        if ( startFlag ) {
            traj.draw(c);
            cos.draw(c);
            ship.draw(c);
        }
    }

    @Override
    public void tick( Canvas c ) {
        // Tick background , space ship , cosmic factory , and trajectory .
        // Draw everything ( restricted to the displayed rectangle ) .

        // Lag debug
        /*Paint paint = new Paint () ;
        paint.setStyle ( Paint. Style . FILL ) ;
        paint.setColor ( Color. WHITE ) ;
        paint.setAntiAlias ( true ) ;
        c.drawPaint ( paint ) ;*/

        bg.tick(c);
        if (!startFlag) {
            Paint paint = new Paint();
            paint.setTextSize(getWidth()/12);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Tap anywhere to start",getWidth()/2, getHeight()/2, paint);
        }
        else {
            if(ship.checkCollision(cos.getClusters())){
                //TODO
                //Do something with ship visibility
                //Clear arrays in CosmicFactory and Trajectory
                //Change gamestate
                //When back to startFlag, reinitialize everything
                ship.setVisible(false);
                if(!ship.isVisible()){
                    //Go to endscreen
                }
                traj=null;
                cos=null;
                ship=null;
            }
            traj.tick(c);
            cos.tick(c);
            ship.tick(c);
            drawScore(c);
            score++;
        }
    }

    protected void drawScore( Canvas c ) {
        Paint paint = new Paint() ;
        paint.setTextSize(getWidth()/12);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText(Integer.toString(score/10),getWidth(), getHeight()/8, paint);
    }

}