/**
 *  Class to represent cuboids
 */
public class Cuboid extends ComplexObject{
    private double xLen;
    private double yLen;
    private double zLen;

    private Point3D[] v = new Point3D[8];
    private static final int[][] SIDES = {{4, 6, 7, 5}, {6, 2, 3, 7}, {2, 0, 1, 3}, {0, 4, 5, 1}, {5, 7, 3, 1}, {0, 2, 6, 4}};


    public Cuboid(Point3D pos, double xLen, double yLen, double zLen) {
        super(pos);
        this.xLen = xLen;
        this.yLen = yLen;
        this.zLen = zLen;

        int i = 0;

        /*
        x y z
        0 0 0 v0
        0 0 1 v1
        0 1 0 v2
        0 1 1 v3
        1 0 0 v4
        1 0 1 v5
        1 1 0 v6
        1 1 1 v7

         */
        for (int x = 0; x < 2; x++){
            for (int y = 0; y < 2; y++){
                for (int z = 0; z < 2; z++){
                    v[i] = new Point3D(pos.x + x * xLen, pos.y + y*yLen, pos.z + z*zLen);
                    i++;

                }
            }
        }

        for (int[] arr: SIDES){
            addObject(new Rectangle3D(v[arr[0]], v[arr[1]], v[arr[2]], v[arr[3]]));
        }



    }





     /*

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

    }*/
}



