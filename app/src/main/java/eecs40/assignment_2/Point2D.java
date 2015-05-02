package eecs40.assignment_2;

public class Point2D {

    //X,Y Coordinate for trajectory & cosmos
    public float x;
    public float y;

    public Point2D(float x, float y){
        this.x=x;
        this.y=y;
    }
    
    public String toString(){
        return "("+x+","+y+")";
    }
}