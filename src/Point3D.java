/**
 *  Class to represent a Point in 3D space.
 */

public class Point3D {
    public double x,y,z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Vector3 vector3){
        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;

    }

    public Vector3 toVector(){
        return new Vector3(x,y,z);
    }

    public Point3D copy(){
        return new Point3D(x,y,z);
    }


    /**
     *
     * @param another Another point
     * @return A Vector3 from this point to another
     */
    public Vector3 vectorTo(Point3D another){
        return another.toVector().subtract(this.toVector());
    }

    /**
     * 
     * @param another Another point
     * @return The distance from this point to another point.
     */
    public double distanceTo(Point3D another){
        return vectorTo(another).len();
        
    }

    public Point3D midPoint(Point3D another){
        Vector3 v = vectorTo(another);
        return  this.toVector().add(v.divide(2)).toPoint();
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z +")";
    }

}
