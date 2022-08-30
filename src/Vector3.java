

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
    public void multiply(double scalar){
        x = x * scalar;
        y = y * scalar;
        z = z * scalar;
    }

    public void divide(double scalar){
        x = x / scalar;
        y = y / scalar;
        z = z / scalar;
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

    public Vector3 rotateAroundZ(double angle){
        return new Vector3(x*Math.cos(angle) - y*Math.sin(angle),
                y*Math.cos(angle) + x*Math.sin(angle),
                z);
    }



}
