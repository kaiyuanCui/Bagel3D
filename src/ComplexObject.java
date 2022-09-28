import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent Complex 3D objects, which are made up of a group of simple objects
 */
public class ComplexObject extends Object3D{

    protected List<Object3D> subObjects = new ArrayList<Object3D>(); // change this to simple
    private static int renderSize = 256;

    public ComplexObject(Point3D pos) {
        super(pos);
    }

    public void addObject(Object3D newObj){
        subObjects.add(newObj);
    }

    public void sort(){
        Collections.sort(subObjects);
    }


    /**
     * Draws the object with a given light source
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSource
     */
    public void draw(Camera camera, double screenWidth, double screenHeight, Point3D lightSource){
        sort();
        for(Object3D object: subObjects.subList(Math.max(0, subObjects.size() - renderSize), subObjects.size())){
            object.draw(camera, screenWidth, screenHeight, object.pos.vectorTo(lightSource));
        }
    }


    /**
     * Draws the object with a given light source direction
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSourceDirection
     */

    public void draw(Camera camera, double screenWidth, double screenHeight, Vector3 lightSourceDirection){
        sort();
        for(Object3D object: subObjects.subList(Math.max(0, subObjects.size() - renderSize), subObjects.size())){
            object.draw(camera, screenWidth, screenHeight, lightSourceDirection);
        }

    }
}
