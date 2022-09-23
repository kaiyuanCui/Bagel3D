

import bagel.Drawing;
import bagel.Font;
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
    private Colour colour;
    private static final Font defaultFont = new Font("res/conformable.otf", 20 );; // used to debug


    // debug only
    private final Colour DEBUG = new Colour(0.1, 0.5, 0, 1);
    private static final Vector3 DEFAULT_LIGHT_SOURCE = new Vector3(1,2,5);

    public Rectangle3D(Point3D pos, double width, double height, Vector3 rotation) {
        super(pos);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.colour = DEBUG;
        Vector3[] directions = {new Vector3(0,0,0), new Vector3(width, 0 ,0), new Vector3(width, height, 0), new Vector3(0,height,0 )};


        for (int i = 0; i<4; i++){
            vertices[i] = new Point3D(pos.toVector().add(directions[i].rotate(rotation)));
        }


    }



    public Rectangle3D(Point3D v0, Point3D v1, Point3D v2,Point3D v3) {

        super(v0.toVector().add(v0.vectorTo(v2).divide(2)).toPoint()); // middle
        this.width = v0.distanceTo(v1);
        this.height = v0.distanceTo(v3);
        this.colour = DEBUG;

        vertices[0] = v0;
        vertices[1] = v1;
        vertices[2] = v2;
        vertices[3] = v3;
    }


    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Point[] castVertices(Camera camera, double screenWidth, double screenHeight){
        double screenDist = (screenWidth /2 )/ Math.tan(camera.getFov()/2);

        // points outside the screen
        int out = 0;

        Point[] cast_vertices = new Point[4];

        for (int i =0; i<4; i++){
            // cast a ray from camera to the point
            Vector3 ray = vertices[i].toVector().subtract(camera.getCameraPos().toVector());
            ray = ray.rotateAroundZ(camera.gethAngle());
            ray = ray.rotateAroundY(camera.getvAngle());

            // point is behind player
            if(ray.x > 0){
                return cast_vertices;
            }

            // coordinates of the ray projected on a screen
            double ver = ray.z/ray.x * screenDist + screenHeight/2;
            double hor = ray.y/ray.x * screenDist + screenWidth/2;


            // do not draw if the projected coordinates are all outside the screen
            // however, the current implementation would make objects near the edges disappear, if the object is large enough.
            if(ver < 0|| ver > screenHeight || hor < 0  || hor > screenWidth){
                out++;
                if(out == 4){
                    return cast_vertices;
                }
            }

            cast_vertices[i] = new Point(hor, ver);

        }
        return cast_vertices;

    }


    @Override
    public void draw(Camera camera, double screenWidth, double screenHeight){
        draw(camera, screenWidth, screenHeight, DEFAULT_LIGHT_SOURCE);
    }

    public void draw(Camera camera, double screenWidth, double screenHeight, Vector3 lightSourceDirection){
        // everything here is constant, compute beforehand?
        double screenDist = (screenWidth /2 )/ Math.tan(camera.getFov()/2);


        Point[] cast_vertices = castVertices(camera, screenWidth, screenHeight);
        if (cast_vertices[3] == null){ // return early if the rectangle should not be drawn
            return;
        }



        // how much the normal is projected in the direction of the light source (might be better using angles instead)
        double light = vertices[1].toVector().subtract(vertices[0].toVector()).
                cross(vertices[3].toVector().subtract(vertices[0].toVector())).unitVector().
                projectionLength(lightSourceDirection) ;
        System.out.println(vertices[1].toVector().subtract(vertices[0].toVector()).
                cross(vertices[3].toVector().subtract(vertices[0].toVector())).unitVector());
        double environmentalLight = 0.5; // change to a constant later
        Colour drawColour = new Colour(colour.r * (environmentalLight  + (1 - environmentalLight) * light) ,
                colour.g * (environmentalLight  + (1 - environmentalLight) * light),
                colour.b * (environmentalLight  + (1 - environmentalLight) * light));




        /*
        this method does not work, why?

        // check if the points are within the screen
        for(Point p: cast_vertices){
            if((p.x >= 0  || p.x <= screenHeight) && (p.y >= 0  || p.y <= screenWidth)){
                break;
            }
            return;
        }
         */

        // draw lines from edges to one vertex (not very efficient)

        Vector2 cast_top_dir = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());
        Vector2 cast_rgt_dir = cast_vertices[3].asVector().sub(cast_vertices[0].asVector());
        // Vector2 cast_lft_dir = cast_vertices[2].asVector().sub(cast_vertices[1].asVector());
        // Vector2 cast_bot_dir = cast_vertices[2].asVector().sub(cast_vertices[3].asVector());
        double topLen = cast_top_dir.length();
        double rgtLen = cast_rgt_dir.length();
        cast_top_dir = cast_top_dir.normalised();
        cast_rgt_dir = cast_rgt_dir.normalised();


        for (int s = 0; s < topLen; s+=lineWidth){
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_top_dir.mul(s)).asPoint(),cast_vertices[2],lineWidth, drawColour);
            // rectangle.drawLine(cast_vertices[2].asVector().add(cast_y_dir.normalised().mul(s)).asPoint(),cast_vertices[0],2, DEBUG);
            // rectangle.drawLine(cast_vertices[1].asVector().add(cast_x_dir.normalised().mul(s)).asPoint(),cast_vertices[2],2, DEBUG);
        }

        for (int j = 0; j < rgtLen; j+=lineWidth){
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_rgt_dir.mul(j)).asPoint(),cast_vertices[2],lineWidth, drawColour);
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

        Vector3 ray = pos.toVector().subtract(camera.getCameraPos().toVector());
        ray = ray.rotateAroundZ(camera.gethAngle());
        ray = ray.rotateAroundY(camera.getvAngle());

        // point is behind player
        if(ray.x > 0){
            return;
        }

        // coordinates of the ray projected on a screen
        double ver = ray.z/ray.x * screenDist + screenHeight/2;
        double hor = ray.y/ray.x * screenDist + screenWidth/2;
        // Drawing.drawCircle(new Point(hor, ver),2, new Colour(1, 0,0)); // draws the pos as a dot
        // defaultFont.drawString(String.valueOf(light),hor, ver); // displays the amount of light reflected



    }






}
