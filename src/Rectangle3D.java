

import bagel.Drawing;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Rectangle3D extends Object3D{
    private double width;
    private double height;
    private Vector3 rotation;
    private Point3D[] vertices = new Point3D[4];

    private final int lineWidth = 10;  // the width of the lines used to fill in the rectangles,
                                        // wider = better performance, but less visual quality

    // debug only
    private final Colour DEBUG = new Colour(0.1, 0.5, 0, 1);

    public Rectangle3D(Point3D pos, double width, double height, Vector3 rotation) {
        super(pos);
        this.width = width;
        this.height = height;
        this.rotation = rotation;

        Vector3[] directions = {new Vector3(0,0,0), new Vector3(width, 0 ,0), new Vector3(width, height, 0), new Vector3(0,height,0 )};

        for (int i = 0; i<4; i++){
            vertices[i] = new Point3D(pos.toVector().add(directions[i]));
        }


    }

    public void draw(Camera camera, double screenWidth, double screenHeight){
        double screenDist = (screenWidth /2 )/ Math.tan(camera.getFov()/2);
        double bufferPixels = 200;

        Point[] cast_vertices = new Point[4];

        for (int i =0; i<4; i++){

            Point3D point = vertices[i].copy();
            Vector3 ray = point.toVector().subtract(camera.getCameraPos().toVector()); // cast a ray from camera to the point
            ray = ray.rotateAroundZ(camera.gethAngle());

            if(ray.x > 0){ // point is behind player
                return;
            }

            // coordinates of the ray projected on a screen
            double ver = ray.z/ray.x * screenDist + screenHeight/2;
            double hor = ray.y/ray.x * screenDist + screenWidth/2;


            // do not draw if the projected coordinate is outside the screen
            // however, the current implementation would make objects near the edges disappear, if the object is large enough.
            if(ver < 0 - bufferPixels || ver > screenHeight + bufferPixels || hor < 0 - bufferPixels || hor > screenWidth + bufferPixels){
                return;
            }

            cast_vertices[i] = new Point(hor, ver);

        }

        // draw lines from edges to one vertex (not very efficient)

        Vector2 cast_top_dir = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());
        // Vector2 cast_bot_dir = cast_vertices[2].asVector().sub(cast_vertices[3].asVector());
        Vector2 cast_rgt_dir = cast_vertices[3].asVector().sub(cast_vertices[0].asVector());
        // Vector2 cast_lft_dir = cast_vertices[2].asVector().sub(cast_vertices[1].asVector());
        double topLen = cast_top_dir.length();
        double rgtLen = cast_rgt_dir.length();

        cast_top_dir = cast_top_dir.normalised();
        cast_rgt_dir = cast_rgt_dir.normalised();



        for (int s = 0; s < topLen; s+=lineWidth){
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_top_dir.mul(s)).asPoint(),cast_vertices[2],lineWidth, DEBUG);
            // rectangle.drawLine(cast_vertices[2].asVector().add(cast_y_dir.normalised().mul(s)).asPoint(),cast_vertices[0],2, DEBUG);
            // rectangle.drawLine(cast_vertices[1].asVector().add(cast_x_dir.normalised().mul(s)).asPoint(),cast_vertices[2],2, DEBUG);
        }

        for (int j = 0; j < rgtLen; j+=lineWidth){
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_rgt_dir.mul(j)).asPoint(),cast_vertices[2],lineWidth, DEBUG);
        }



        // draw quadrilaterals to fill (somehow even less efficient)
        /*
        Vector2 downLeftDir = cast_vertices[2].asVector().sub(cast_vertices[0].asVector());
        Vector2 downRghtDir = cast_vertices[3].asVector().sub(cast_vertices[1].asVector());

        double downRghtLen = downRghtDir.length();
        double downLeftLen = downLeftDir.length();



        double maxLen = Math.max(downRghtDir.length(), downLeftDir.length());
        double minLen = Math.min(downRghtDir.length(), downLeftDir.length());
        downRghtDir = downRghtDir.normalised();
        downLeftDir = downLeftDir.normalised();

        Point topRight = cast_vertices[0].asVector().add(downLeftDir.mul(lineWidth/2)).asPoint();
        Point topLeft = cast_vertices[1].asVector().add(downRghtDir.mul(lineWidth/2)).asPoint();
        Point botLeft = cast_vertices[2].asVector().add(downLeftDir.mul(-1 * lineWidth/2)).asPoint();
        Point botRight = cast_vertices[3].asVector().add(downRghtDir.mul(-1 * lineWidth/2)).asPoint();

        for (int s = 0; s < minLen / 2 + lineWidth; s+=lineWidth){

            Drawing.drawLine(topLeft, topRight, lineWidth , DEBUG);
            Drawing.drawLine(topRight, botRight, lineWidth , DEBUG);
            Drawing.drawLine(botRight, botLeft, lineWidth , DEBUG);
            Drawing.drawLine(botLeft, topLeft, lineWidth , DEBUG);


            topRight = cast_vertices[0].asVector().add(downLeftDir.mul(s)).asPoint();
            topLeft = cast_vertices[1].asVector().add(downRghtDir.mul(s)).asPoint();
            botLeft = cast_vertices[2].asVector().add(downLeftDir.mul(-1 * s)).asPoint();
            botRight = cast_vertices[3].asVector().add(downRghtDir.mul(-1 * s)).asPoint();

        }

        // for debug
        Drawing.drawLine(topLeft, botRight , 1, new Colour(0,0,0));
        Drawing.drawLine(topRight, botLeft, 1, new Colour(0,0,0));

         */






        // draw an outline of the rectangle
        for(int i = 0; i < 3; i++){
            Drawing.drawLine(cast_vertices[i],cast_vertices[i+1],2, new Colour(0,0,0));
        }

        Drawing.drawLine(cast_vertices[3],cast_vertices[0],2, new Colour(0,0,0));




    }






}
