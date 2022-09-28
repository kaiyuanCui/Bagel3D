

import bagel.Drawing;
import bagel.Font;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public class Rectangle3D extends SimpleObject{
    private double width;
    private double height;

    private final int lineWidth = 7;  // the width of the lines used to fill in the rectangles,
                                        // wider = better performance, but less visual quality

    private static final Font defaultFont = new Font("res/conformable.otf", 20 );; // used to debug
    private static final int VERTICES = 4;


    // debug only
    private final Colour DEBUG = new Colour(0.8, 0.8, 0.8, 1);


    public Rectangle3D(Point3D pos, double width, double height, Vector3 rotation) {
        super(pos, VERTICES);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.colour = DEBUG;
        vertices = new Point3D[4];
        Vector3[] directions = {new Vector3(0,0,0), new Vector3(width, 0 ,0),
                new Vector3(width, height, 0), new Vector3(0,height,0 )};


        for (int i = 0; i<4; i++){
            vertices[i] = new Point3D(pos.toVector().add(directions[i].rotate(rotation)));
        }


    }



    public Rectangle3D(Point3D v0, Point3D v1, Point3D v2,Point3D v3) {

        super(v0.toVector().add(v0.vectorTo(v2).divide(2)).toPoint(), VERTICES); // middle
        this.width = v0.distanceTo(v1);
        this.height = v0.distanceTo(v3);
        this.colour = DEBUG;
        vertices = new Point3D[4];

        vertices[0] = v0;
        vertices[1] = v1;
        vertices[2] = v2;
        vertices[3] = v3;
    }


    public void setColour(Colour colour) {
        this.colour = colour;
    }


    /**
     * Draws the Rectangle with a given light source direction
     * @param camera
     * @param screenWidth
     * @param screenHeight
     * @param lightSourceDirection
     */

    @Override
    public void draw(Camera camera, double screenWidth, double screenHeight, Vector3 lightSourceDirection) {
        // everything here is constant, compute beforehand?
        double screenDist = (screenWidth / 2) / Math.tan(camera.getFov() / 2);


        Point[] cast_vertices = castVertices(camera, screenWidth, screenHeight);
        if (cast_vertices[3] == null) { // return early if the rectangle should not be drawn
            return;
        }


        // how much the normal is projected in the direction of the light source (might be better using angles instead)
        double light = vertices[1].toVector().subtract(vertices[0].toVector()).
                cross(vertices[3].toVector().subtract(vertices[0].toVector())).unitVector().
                projectionLength(lightSourceDirection);

        double environmentalLight = 0.2; // change to a constant later
        Colour drawColour = new Colour(colour.r * (environmentalLight + (1 - environmentalLight) * light),
                colour.g * (environmentalLight + (1 - environmentalLight) * light),
                colour.b * (environmentalLight + (1 - environmentalLight) * light),
                2.5 - camera.getCameraPos().distanceTo(pos) / 500); // distance fog, only looks good when on ground


        // draw lines from edges to one vertex (not very efficient)

        Vector2 cast_top_dir = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());
        Vector2 cast_rgt_dir = cast_vertices[3].asVector().sub(cast_vertices[0].asVector());
        // Vector2 cast_lft_dir = cast_vertices[2].asVector().sub(cast_vertices[1].asVector());
        // Vector2 cast_bot_dir = cast_vertices[2].asVector().sub(cast_vertices[3].asVector());
        double topLen = cast_top_dir.length();
        double rgtLen = cast_rgt_dir.length();
        cast_top_dir = cast_top_dir.normalised();
        cast_rgt_dir = cast_rgt_dir.normalised();


        for (int s = 0; s < topLen; s += lineWidth) {
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_top_dir.mul(s)).asPoint(), cast_vertices[2], lineWidth, drawColour);
            // rectangle.drawLine(cast_vertices[2].asVector().add(cast_y_dir.normalised().mul(s)).asPoint(),cast_vertices[0],2, DEBUG);
            // rectangle.drawLine(cast_vertices[1].asVector().add(cast_x_dir.normalised().mul(s)).asPoint(),cast_vertices[2],2, DEBUG);
        }

        for (int j = 0; j < rgtLen; j += lineWidth) {
            Drawing.drawLine(cast_vertices[0].asVector().add(cast_rgt_dir.mul(j)).asPoint(), cast_vertices[2], lineWidth, drawColour);
        }


        // draw an outline of the shape
        for(int i = 0; i < 3; i++){
            Drawing.drawLine(cast_vertices[i],cast_vertices[i+1],2, new Colour(0,0,0, drawColour.a));
        }

        Drawing.drawLine(cast_vertices[3],cast_vertices[0],2, new Colour(0,0,0, drawColour.a));


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






