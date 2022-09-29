/**
 *  Base class of all 3D objects.
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
     * @param camera
     * @param screenWidth
     * @param screenHeight
     */

    public void draw(Camera camera, double screenWidth, double screenHeight){
        draw(camera, screenWidth, screenHeight, DEFAULT_LIGHT_SOURCE);
    }


    /**
     * Draws the object with a given light source
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSource
     */
    public void draw(Camera camera, double screenWidth, double screenHeight, Point3D lightSource){
        // do nothing
    }

    /**
     * Draws the object with a given light source direction
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSourceDirection
     */

    public void draw(Camera camera, double screenWidth, double screenHeight, Vector3 lightSourceDirection){
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
        Point3D player = Test3D.getCameraPos();

        // difference of their distances, this further away -> negative
        // return (int) (o.distanceTo(player) - this.distanceTo(player));

        if (this.distanceTo(player) > o.distanceTo(player)){
            return -1;
        }

        return 1;
    }


}
