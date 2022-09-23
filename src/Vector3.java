

public class Vector3 {
    public double x,y,z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 subtract(Vector3 another){
        return new Vector3(x-another.x, y- another.y, z- another.z);
    }

    public Vector3 add(Vector3 another){
        return new Vector3(x+another.x, y+ another.y, z+ another.z);
    }
    public Vector3 multiply(double scalar){

        return new Vector3(x*scalar, y*scalar, z*scalar);
    }

    public Vector3 divide(double scalar){

        return new Vector3(x/scalar, y/scalar, z/scalar);
    }

    /**
     *
     * @param another
     * @return The cross product (this x another)
     */
    public Vector3 cross(Vector3 another){
        return new Vector3(y* another.z - z* another.y,
                            z*another.x - x* another.z,
                                x*another.y - y*another.x);
        // u x v = (u2v3 - u3v2) i + (u3v1 - u1v3) j + (u1v2 - u2v1)k

    }

    /**
     *
     * @param another
     * @return The dot product of the two vectors
     */
    public double dot(Vector3 another){
        return x*another.x + y*another.y + z*another.z;
    }

    /**
     *
     * @param another
     * @return The length of this vector when projected onto another vector
     */
    public double projectionLength(Vector3 another){
        return another.unitVector().dot(this);
    }

    /**
     *
     * @param another
     * @return The projection of this vector onto another vector
     */
    public Vector3 projectionOnto(Vector3 another){
        Vector3 unit = another.unitVector();
        return unit.multiply(unit.dot(this));
    }




    public double len(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 unitVector(){
        return this.divide(this.len());
    }

    public Vector3 toLenOf(double scalar){
        Vector3 newV = this.unitVector();
        newV.multiply(scalar);
        return newV;
    }



    public Vector3 rotateAroundX(double angle){
        return new Vector3(x, y*Math.cos(angle) - z*Math.sin(angle),
                z*Math.cos(angle) + y*Math.sin(angle));
    }

    public Vector3 rotateAroundY(double angle){
        return new Vector3(x*Math.cos(angle) + z*Math.sin(angle), y,
                z*Math.cos(angle) - x*Math.sin(angle));
    }

    public Vector3 rotateAroundZ(double angle){
        return new Vector3(x*Math.cos(angle) - y*Math.sin(angle),
                y*Math.cos(angle) + x*Math.sin(angle), z);
    }

    public Vector3 rotate(Vector3 angles){
        Vector3 result = this.rotateAroundX(angles.x);
        result = result.rotateAroundY(angles.y);
        return result.rotateAroundZ(angles.z);

    }

    public Point3D toPoint(){
        return new Point3D(this.x, this.y, this.z);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }



}
