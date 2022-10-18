package Bagel3D;

import Bagel3D.util.Point3D;
import Bagel3D.util.Vector3;
import bagel.util.Colour;
import bagel.util.Point;

/**
 *  Class to represent a simple 3D object, which is a 2D shape in 3D space.
 */
public class SimpleObject extends Object3D {
    protected Point3D[] vertices;
    protected Vector3 rotation;
    protected Colour colour;
    protected final int NUM_VERTICES;

    public SimpleObject(Point3D pos, int numVertices) {
        super(pos);
        NUM_VERTICES = numVertices;
        vertices = new Point3D[NUM_VERTICES];
    }



    public Point[] castVertices(){
        Camera camera = Camera.getInstance();
        double screenWidth = camera.getWidth();
        double screenHeight = camera.getHeight();

        double screenDist = (screenWidth /2 )/ Math.tan(camera.getFov()/2);

        // points outside the screen
        int out = 0;

        Point[] cast_vertices = new Point[NUM_VERTICES];

        for (int i =0; i<NUM_VERTICES; i++){
            // cast a ray from camera to the point
            Vector3 ray = vertices[i].toVector().subtract(camera.getCameraPos().toVector());
            ray = ray.rotateAroundZ(camera.gethAngle());
            ray = ray.rotateAroundY(camera.getvAngle());

            // point is behind player
            // （ We sometimes still need to draw the object when one of its vertices is behind, but at this point,
            //   I am not sure how to do that)
            if(ray.x > 0){
                return cast_vertices;
            }




            // coordinates of the ray projected on a screen
            double ver = ray.z/ray.x * screenDist + screenHeight/2;
            double hor = ray.y/ray.x * screenDist + screenWidth/2;


            // do not draw if the projected coordinates are all outside the screen

            if(ver < 0|| ver > screenHeight || hor < 0  || hor > screenWidth ){
                out++;
                if(out == NUM_VERTICES){
                    return cast_vertices;
                }
            }

            cast_vertices[i] = new Point(hor, ver);

        }
        return cast_vertices;

    }

    /**
     * Overrides the method in Bagel3D.Object3D
     * @param point
     * @return the distance of its closet vertex to the point
     */
    @Override
    public double distanceTo(Point3D point) {
        //double min = Double.MAX_VALUE;
        double sum = 0;
        for (Point3D v: vertices){
            double currDis = v.distanceTo(point);
            sum += currDis;
            /*
            if ( currDis < min){
                min = currDis;
            }

             */
        }
        return sum/NUM_VERTICES;
    }


    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

}
