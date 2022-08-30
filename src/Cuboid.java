public class Cuboid extends Object3D{
    private double xLen;
    private double yLen;
    private double zLen;

    public Cuboid(Point3D pos, double xLen, double yLen, double zLen) {
        super(pos);
        this.xLen = xLen;
        this.yLen = yLen;
        this.zLen = zLen;
    }

    public Object3D createObject(){

        Object3D cuboid = new Object3D(pos);
        Object3D v1 = new Object3D(new Point3D(pos.x + xLen, pos.y          , pos.z));
        Object3D v2 = new Object3D(new Point3D(pos.x + xLen, pos.y + yLen, pos.z));
        Object3D v3 = new Object3D(new Point3D(pos.x + xLen, pos.y          , pos.z + zLen));
        Object3D v4 = new Object3D(new Point3D(pos.x + xLen, pos.y + yLen, pos.z + zLen));
        Object3D v5 = new Object3D(new Point3D(pos.x          , pos.y + yLen, pos.z));
        Object3D v6 = new Object3D(new Point3D(pos.x          , pos.y + yLen, pos.z + zLen));
        Object3D v7 = new Object3D(new Point3D(pos.x          , pos.y          , pos.z + zLen));

        cuboid.addObject(v1);
        cuboid.addObject(v2);
        cuboid.addObject(v3);
        cuboid.addObject(v4);
        cuboid.addObject(v5);
        cuboid.addObject(v6);
        cuboid.addObject(v7);

        return cuboid;

    }
}
