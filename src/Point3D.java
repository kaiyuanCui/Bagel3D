
import org.lwjgl.system.CallbackI;

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

}
