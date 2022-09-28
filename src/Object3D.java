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

    }

    /**
     * Draws the object with a given light source direction
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSourceDirection
     */

    public void draw(Camera camera, double screenWidth, double screenHeight, Vector3 lightSourceDirection){

    }


    @Override
    public int compareTo(Object3D o) {
        /*
        if (subObjects.size() > 0){
            if(o.subObjects.size() > 0){
                return Collections.max(subObjects).compareTo(Collections.max(o.subObjects));
            }
        }

         */

        // I need a better algorithm for this
        Point3D player = Test3D.getCameraPos();
        // the return values are reversed: < if further away

        if (Math.abs(this.pos.distanceTo(player)) > Math.abs(o.getPos().distanceTo(player))){
            return -1;
        }

        return 1;
    }


}
