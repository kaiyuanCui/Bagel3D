package Bagel3D;

import Bagel3D.util.Point3D;
import Bagel3D.util.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent Complex 3D objects, which are made up of a group of simple objects
 */
public class ComplexObject extends Object3D{

    protected List<Object3D> subObjects = new ArrayList<Object3D>(); // change this to simple
    private int renderSize = 256;

    public ComplexObject(Point3D pos) {
        super(pos);
    }

    public void addObject(Object3D newObj){
        subObjects.add(newObj);
    }

    public void mergeObject(ComplexObject newObj){
        subObjects.addAll(newObj.subObjects);
    }

    public void sort(){
        Collections.sort(subObjects);
    }

    @Override
    /**
     * Draws the object with a given light source
     * @param lightSource
     */
    public void draw(Point3D lightSource){
        sort();
        for(Object3D object: subObjects.subList(Math.max(0, subObjects.size() - renderSize), subObjects.size())){

            object.draw(object.pos.vectorTo(lightSource));
        }
    }

    @Override
    /**
     * Draws the object with a given light source direction
     * @param lightSourceDirection
     */

    public void draw(Vector3 lightSourceDirection){
        sort();
        for(Object3D object: subObjects.subList(Math.max(0, subObjects.size() - renderSize), subObjects.size())){
            object.draw(lightSourceDirection);
        }

    }

    public void setRenderSize(int renderSize) {
        this.renderSize = renderSize;
    }

    /**
     * Overrides the method in Bagel3D.Object3D
     * @param point
     * @return the distance of its closet subobject to the point
     */
    @Override
    public double distanceTo(Point3D point) {
        double min = Double.MAX_VALUE;
        for (Object3D o: subObjects){
            double currDis = o.distanceTo(point);
            if ( currDis < min){
                min = currDis;
            }
        }
        return min;
    }
}
