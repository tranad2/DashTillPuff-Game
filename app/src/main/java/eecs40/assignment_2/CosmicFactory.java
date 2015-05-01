package eecs40.assignment_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.Random;

public class CosmicFactory implements TimeConscious {

    private static final String TAG = "CosmicFactory";
    private int screenWidth, screenHeight, mScaledWidth, mScaledHeight;
    private int OFFSET = 1; //Bitmap height (same for all bitmaps)
    private ArrayList<Bitmap> bitmapList;
    private ArrayList<ArrayList<Cluster>> clusterList;
    private Trajectory traj;
    private DashTillPuffSurfaceView sv;

    public CosmicFactory(Trajectory trajectory, DashTillPuffSurfaceView view) {
        //Load cosmic bitmaps
        Bitmap blackhole, blueplanet, cloud, earth, glossyplanet, goldenstar, neutronstar, shinystar, star1, star2;
        bitmapList = new ArrayList<>();
        clusterList = new ArrayList<>();
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

        mScaledHeight = blackhole.getHeight()/3;    //Reuse scaled width/height with same bitmaps
        mScaledWidth = blackhole.getWidth()/3;

        blackhole = Bitmap.createScaledBitmap(blackhole, mScaledWidth, mScaledHeight, true);
        blueplanet = Bitmap.createScaledBitmap(blueplanet, mScaledWidth, mScaledHeight, true);
        cloud = Bitmap.createScaledBitmap(cloud, mScaledWidth, mScaledHeight, true);
        earth = Bitmap.createScaledBitmap(earth, mScaledWidth, mScaledHeight, true);
        glossyplanet = Bitmap.createScaledBitmap(glossyplanet, mScaledWidth, mScaledHeight, true);
        goldenstar = Bitmap.createScaledBitmap(goldenstar, mScaledWidth, mScaledHeight, true);
        neutronstar = Bitmap.createScaledBitmap(neutronstar, mScaledWidth, mScaledHeight, true);
        shinystar = Bitmap.createScaledBitmap(shinystar, mScaledWidth, mScaledHeight, true);
        star1 = Bitmap.createScaledBitmap(star1, mScaledWidth, mScaledHeight, true);
        star2 = Bitmap.createScaledBitmap(star2, mScaledWidth, mScaledHeight, true);

        bitmapList.add(blackhole);
        bitmapList.add(blueplanet);
        bitmapList.add(cloud);
        bitmapList.add(earth);
        bitmapList.add(glossyplanet);
        bitmapList.add(goldenstar);
        bitmapList.add(neutronstar);
        bitmapList.add(shinystar);
        bitmapList.add(star1);
        bitmapList.add(star2);

        screenWidth = view.getWidth();
        screenHeight = view.getHeight();

        traj = trajectory;
        sv = view;
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

        if(!(traj.getPoints().isEmpty()) && traj.getPoints().size()>1 && (5*screenWidth/3)-traj.getPoints().get(traj.getPoints().size()-1).x>=screenWidth/3) {
            Log.v(TAG,"CONDITION");
            ArrayList<Cluster> newClus = new ArrayList<>();
            Bitmap type = getRanClusType();
            Random ran = new Random();
            Point2D p1 = traj.getPoints().get(traj.getPoints().size()-2);
            Point2D p2 = traj.getPoints().get(traj.getPoints().size()-1);

            //yPos=yLine+-(ranOffset) -- ranOffset must be bigger than ship dimension (128x128)
            //Log.v(TAG,"X: "+xPos+" Y: "+yPos);
            for (int i = 0; i < 10; i++) {
                float xPos = ran.nextInt((int) p2.x - (int) p1.x) + p1.x; //Get xPos between first point and next point; ranInt(p2.x-p1.x)+p1.x
                float yLine = (p2.y - p1.y) / (p2.x - p1.x) * (xPos - p1.x) + p1.y;
                int ranY = 0;
                Log.v(TAG, "Iteration " + i);
                if ((p1.y + mScaledHeight > screenHeight - mScaledHeight && p2.y + mScaledHeight > screenHeight - mScaledHeight && OFFSET>0) || (p1.y-mScaledHeight<mScaledHeight && p2.y-mScaledHeight<mScaledHeight && OFFSET<0)){
                    break;
                }
                else{
                    if (OFFSET > 0) {   //Below traj path
                        ranY = ran.nextInt(screenHeight - (int) yLine);
                        if (yLine + ranY + mScaledHeight > screenHeight - mScaledHeight) {
                            i--;
                            continue;
                        }
                    } else if (OFFSET < 0) {    //Above traj path
                        ranY = ran.nextInt((int) yLine);
                        if (yLine - ranY - mScaledHeight < mScaledHeight) {
                            i--;
                            continue;
                        }
                    }
                }
                float yPos = yLine+Math.signum(OFFSET)*(ranY+1.5f*mScaledHeight); //Slope formula; yLine = (p2.y-p1.y)/(p2.x-p1.x)*(xPos-p1.x)+p1.y
                Log.v(TAG, new Cluster(type, (int) xPos, (int) yPos, sv).toString());
                newClus.add(new Cluster(type, (int) xPos, (int) yPos, sv));
            }
            Log.v(TAG,"isEmpty"+newClus.isEmpty());
            clusterList.add(newClus);
            Log.v(TAG,"newClus"+newClus);
            OFFSET *= -1;   //Alternate between top and bottom of line

        }


        // Remove cosmic objects ( stars , planets , etc .) that moved out
        // of the scene .
        //...

        for(int j = 0; j<clusterList.size(); j++){
            for(int k = 0; k<clusterList.get(j).size(); k++){
                if(clusterList.get(j).get(k).getX()<0-clusterList.get(j).get(k).getWidth()){
                    clusterList.get(j).remove(k);
                    k--;
                }
            }
            if(clusterList.get(j).isEmpty()){
                clusterList.remove(j);
                j--;
            }
        }
        for(ArrayList<Cluster> list : clusterList){
            for(Cluster clu : list){
                //Log.v(TAG, "DRAW");
                clu.tick(canvas);
            }
        }
    }

    public ArrayList<ArrayList<Cluster>> getClusters(){
        return clusterList;
    }

    protected void draw ( Canvas c ) {
        //Call Clusters draw()
        for(ArrayList<Cluster> list : clusterList){
            for(Cluster clu : list){
                clu.draw(c);
            }
        }
    }

    private Bitmap getRanClusType(){
        Random ran = new Random();
        int type = ran.nextInt(bitmapList.size());
        return bitmapList.get(type);
    }
}