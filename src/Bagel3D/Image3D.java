package Bagel3D;

import Bagel3D.util.Point3D;
import Bagel3D.util.Vector3;
import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 *  Class to draw an image in 3D space
 */
public class Image3D extends Object3D{
    private Image image;
    private static Rectangle3D rect;
    private double width;
    private double height;
    private Vector3 rotation;

    public Image3D(Point3D pos, String imgPath) {
        super(pos);
        this.image = new Image(imgPath);
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.rotation = new Vector3(0, 0,0);
        this.rect = getRect();
        rect.setColour(new Colour(1,0,0));

    }

    public Rectangle3D getRect(){
        return new Rectangle3D(pos, width, height, rotation);
    }


    @Override
    public double distanceTo(Point3D point) {
        return rect.distanceTo(point);
    }

    @Override
    public void draw(Vector3 lightSourceDirection) {

        Camera camera = Camera.getInstance();
        Point[] cast_vertices = rect.castVertices(camera, camera.getWidth(), camera.getHeight());
        if (cast_vertices[3] == null){
            return;
        }

        Point centre = cast_vertices[0].asVector().add(cast_vertices[2].asVector()).div(2).asPoint();
        Vector2 horizontal = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());

        rect.draw();    // draws the bounding rectangle

        image.draw(centre.x, centre.y,
                // rotates the image based on the rectangle's rotation on screen
                new DrawOptions().setRotation(Math.atan2(horizontal.y, horizontal.x))
                        // set the size of the image based on the width and height of the rectangle on screen
                        .setScale(cast_vertices[1].distanceTo(cast_vertices[0])/width,
                                                    cast_vertices[3].distanceTo(cast_vertices[0])/height));
    }


}
