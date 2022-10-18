package Bagel3D;

import Bagel3D.util.Point3D;
import Bagel3D.util.Vector3;

/**
 *  Base class of all 3D objects.
 *
 * @author kaiyuanCui
 */

public abstract class Object3D implements Comparable<Object3D>{
    protected Point3D pos;

    public Object3D(Point3D pos) {
        this.pos = pos;
    }

    public Point3D getPos() {
        return pos;
    }



    public void setPos(Point3D pos) {
        this.pos = pos;
    }

    protected static final Vector3 DEFAULT_LIGHT_SOURCE = new Vector3(1,2,5);


    /**
     * Draws the object with the Default light source direction
     */

    public void draw(){
        draw(DEFAULT_LIGHT_SOURCE);
    }


    /**
     * Draws the object with a given light source

     * @param lightSource
     */
    public void draw( Point3D lightSource){
        draw(pos.vectorTo(lightSource));
    }

    /**
     * Draws the object with a given light source direction

     * @param lightSourceDirection
     */

    public void draw(Vector3 lightSourceDirection){
        // do nothing
    }

    /**
     *
     * @param point
     * @return the distance of the object to the point
     */
    public double distanceTo(Point3D point){
        return pos.distanceTo(point);
    }

    @Override
    public int compareTo(Object3D o) {
        // need a more elegant solution for this?
        Point3D player = Camera.getInstance().getCameraPos();

        // difference of their distances, this further away -> negative
        // return (int) (o.distanceTo(player) - this.distanceTo(player));

        if (this.distanceTo(player) > o.distanceTo(player)){
            return -1;
        }

        return 1;
    }


}
