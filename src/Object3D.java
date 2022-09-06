
import bagel.Drawing;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Object3D {
    protected Point3D pos;
    private List<Object3D> subObjects = new ArrayList<Object3D>();

    public Object3D(Point3D pos) {
        this.pos = pos;
    }

    public Point3D getPos() {
        return pos;
    }

    public void addObject(Object3D newObj){
        subObjects.add(newObj);
    }

    public void setPos(Point3D pos) {
        this.pos = pos;
    }

    /*

    public List<Point> getPoints(Camera camera, double screenWidth, double screenHeight){
        double screenDist = (screenWidth /2 )/ Math.tan(camera.getFov()/2);
        List<Point> points = new ArrayList<Point>();
         // Object3D temp = this.rotateAroundZ(camera.gethAngle()); // rotates everything about the z axis (not about the camera)

        Vector3 ray = pos.toVector().subtract(camera.getCameraPos().toVector()); // cast a ray from camera to the point

        ray = ray.rotateAroundZ(camera.gethAngle());


        // coordinates projected on a screen
        double ver = ray.z/ray.x * screenDist + screenHeight/2;
        double hor = ray.y/ray.x * screenDist + screenWidth/2;

        /*
        // camera tilt
        ver = ver*Math.cos(camera.gethAngle()) + hor*Math.sin(camera.gethAngle());
        hor = hor*Math.cos(camera.gethAngle()) - ver*Math.sin(camera.gethAngle());




        if (ray.x <= 0){
            points.add(new Point(hor,ver));
        }





        for (Object3D obj: subObjects){
            points.addAll(obj.getPoints(camera, screenWidth, screenHeight));
        }


        return points;
    }

     */




    public void draw(Camera camera, double screenWidth, double screenHeight){
        for(Object3D object: subObjects){
            object.draw(camera, screenWidth, screenHeight);
        }

    }

    /*
    public Object3D rotateAroundZ(double angle){  //clockwise

        // create a copy of the object to rotate to avoid continuous transformation to it, which deforms it.
        Object3D rotated = this.clone();
        rotated.setPos(new Point3D(pos.x*Math.cos(angle) - pos.y*Math.sin(angle),
                                    pos.y*Math.cos(angle) + pos.x*Math.sin(angle),
                                        pos.z));


        for (Object3D obj: subObjects){
            rotated.addObject(obj.rotateAroundZ(angle));
        }
        return rotated;
    }

     */


}
