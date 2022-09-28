import bagel.Drawing;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

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



    public Point[] castVertices(Camera camera, double screenWidth, double screenHeight){
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
            if(ray.x > 0){
                return cast_vertices;
            }

            // coordinates of the ray projected on a screen
            double ver = ray.z/ray.x * screenDist + screenHeight/2;
            double hor = ray.y/ray.x * screenDist + screenWidth/2;


            // do not draw if the projected coordinates are all outside the screen
            // however, the current implementation would make objects near the edges disappear, if the object is large enough.
            if(ver < 0|| ver > screenHeight || hor < 0  || hor > screenWidth){
                out++;
                if(out == 4){
                    return cast_vertices;
                }
            }

            cast_vertices[i] = new Point(hor, ver);

        }
        return cast_vertices;

    }


}
