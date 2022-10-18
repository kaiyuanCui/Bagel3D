package Bagel3D;

import Bagel3D.util.Point3D;
import Bagel3D.util.Vector3;
import bagel.DrawOptions;
import bagel.Drawing;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 *  Class to draw an image in 3D space
 */
public class Image3D extends Object3D{
    private Image image;
    private Rectangle3D rect;
    private double width;
    private double height;
    private Vector3 rotation;
    private boolean rotateWithCamera = true;
    private SimpleObject centre;

    private static final double IMG_PRIORITY = 50;

    public Image3D(Point3D pos, String imgPath, Vector3 rotation) {
        super(pos);
        this.image = new Image(imgPath);
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.rotation = rotation;
        this.rect = getRect();
        rect.setColour(new Colour(1,0,0));
        centre = new SimpleObject(pos, 1);
        centre.getVertices()[0] = pos;
    }

    public Rectangle3D getRect(){
        return new Rectangle3D(pos, width, height, rotation);
    }


    @Override
    public double distanceTo(Point3D point) {
        // this is not an ideal fix to larger objects covering up images, but it will have to do for now.
        return Math.min(centre.distanceTo(point), rect.distanceTo(point)) - IMG_PRIORITY;
    }


    /**
     * Draws the image in 3D space. This is far from perfect, but things do look OK near the centre of the screen.
     * @param lightSourceDirection
     */
    @Override
    public void draw(Vector3 lightSourceDirection) {

       // rect.draw();// draws the bounding rectangle
        Camera camera = Camera.getInstance();
        DrawOptions options = new DrawOptions();


        if(rotateWithCamera){   // rotates the bounding rectangle
            double rotationZ = Math.atan2(pos.vectorTo(camera.getCameraPos()).y, pos.vectorTo(camera.getCameraPos()).x) + Math.PI/2;
            rotation = new Vector3(rotation.x, rotation.y, rotationZ);
            rect = getRect();
        }

        // cast the bounding rectangle on the screen

        Point[] cast_vertices = rect.castVertices();
        if (cast_vertices[3] == null){
            return;
        }

        Point centrePoint = centre.castVertices()[0];
        if (centrePoint == null){
            return;
        }

        Vector2 horizontal = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());
        // rotates the image based on the rectangle's rotation on screen
        if (rotateWithCamera){
            options.setRotation(Math.atan2(horizontal.y, horizontal.x) + Math.PI);
        }
        else {
            options.setRotation(Math.atan2(horizontal.y, horizontal.x));
        }



        // set the size of the image based on the width and height of the rectangle on screen
        options.setScale(cast_vertices[1].distanceTo(cast_vertices[0])/width,
                cast_vertices[3].distanceTo(cast_vertices[0])/height);

        image.draw(centrePoint.x, centrePoint.y, options);
        // Drawing.drawCircle(centrePoint,5 * camera.getScreenDist()/ this.distanceTo(camera.getCameraPos()), new Colour(1,0,0));

    }

    public void setRotateWithCamera(boolean rotateWithCamera) {
        this.rotateWithCamera = rotateWithCamera;
    }
}
