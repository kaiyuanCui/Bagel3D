

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



    public double len(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 unitVector(){
        double len = this.len();
        return new Vector3(x/len, y/len, z/len);
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



}
