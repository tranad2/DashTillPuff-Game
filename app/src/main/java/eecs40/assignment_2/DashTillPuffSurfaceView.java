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
    Background background;
    Trajectory trajectory;
    Ship ship;
    CosmicFactory cosmos;
    int gameState = 0;  //0 = start screen, 1 = game screen, 2 = game over screen
    int score;
    int hiScore = 0;

    public DashTillPuffSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new DashTillPuffRenderThread( this );
        renderThread.start();
        background = new Background(this);
        trajectory = new Trajectory(this);
        ship = new Ship(this);
        cosmos = new CosmicFactory(trajectory, this);
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
            case MotionEvent.ACTION_DOWN: // Thrust the space ship up
                if (gameState == 2){    //If game over, restart game on touch
                    gameState = 1;
                    ship.resetVelocity();
                    ship.setVisible(true);
                    score = 0;
                }
                gameState = 1;
                ship.setTouchFlag(true);
                break;

            case MotionEvent.ACTION_UP: // Let space ship fall freely
                ship.setTouchFlag(false);
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        background.draw(c);
        if ( gameState == 1 ) {     //If not in main game state, don't draw
            trajectory.draw(c);
            cosmos.draw(c);
            ship.draw(c);
        }
    }

    @Override
    public void tick( Canvas c ) {
        background.tick(c);

        //Start screen
        if ( gameState == 0) {
            Paint paint = new Paint();
            paint.setTextSize(getWidth()/12);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Tap anywhere to start", getWidth()/2, getHeight()/2, paint);
        }

        //Main game screen
        if ( gameState == 1 ) {
            trajectory.tick(c);
            cosmos.tick(c);
            ship.tick(c);
            drawScore(c);
            score++;
            if (score > hiScore) {
                hiScore = score;
            }
        }

        //Check collisions
        if (ship.checkCollision(cosmos.getClusters())) {
            gameState = 2;
        }

        //Game over screen
        if ( gameState == 2 ) {
            drawScore(c);
            Paint paint = new Paint();
            paint.setTextSize(getWidth() / 12);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            c.drawText("Game Over", getWidth() / 2, 2 * getHeight() / 5, paint);
            c.drawText("Tap anywhere to restart", getWidth() / 2, 3 * getHeight() / 5, paint);

            //Clear game data
            ship.setVisible(false);
            trajectory.getPoints().clear();
            cosmos.getClusters().clear();
            ship.setLocation(this.getWidth()/4, this.getHeight()/2);
        }
    }

    protected void drawScore( Canvas c ) {
        Paint paint1 = new Paint() ;
        paint1.setTextSize(getWidth()/24);
        paint1.setColor(Color.WHITE);
        paint1.setTextAlign(Paint.Align.LEFT);
        paint1.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText("Score: " + Integer.toString(score/10), 0, getHeight()/14, paint1);    //10 ticks = 1 point
        Paint paint2 = new Paint() ;
        paint2.setTextSize(getWidth() / 24);
        paint2.setColor(Color.WHITE);
        paint2.setTextAlign(Paint.Align.RIGHT);
        paint2.setTypeface(Typeface.DEFAULT_BOLD);
        c.drawText("Hi Score: " + Integer.toString(hiScore/10), getWidth(), getHeight()/14, paint2);
    }

}