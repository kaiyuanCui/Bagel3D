import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public class Image3D extends Object3D{
    private Image image;
    private Rectangle3D rect;
    private double width;
    private double height;
    private Vector3 rotation;

    public Image3D(Point3D pos, String imgPath) {
        super(pos);
        this.image = new Image(imgPath);
        this.width = image.getWidth();
        this.height = image.getHeight();
        //width = 1000;
        //height = 1000;
        this.rotation = new Vector3(0, 0,0);
        this.rect = getRect();
        rect.setColour(new Colour(1,0,0));

    }

    public Rectangle3D getRect(){
        return new Rectangle3D(pos, width, height, rotation);
    }

    @Override
    public void draw(Camera camera, double screenWidth, double screenHeight) {
        Point[] cast_vertices = rect.castVertices(camera, screenWidth, screenHeight);
        if (cast_vertices[3] == null){
            return;
        }
        Point centre = cast_vertices[0].asVector().add(cast_vertices[2].asVector()).div(2).asPoint();
        Vector2 horizontal = cast_vertices[1].asVector().sub(cast_vertices[0].asVector());
        // rect.draw(camera, screenWidth, screenHeight);
        image.draw(centre.x, centre.y, new DrawOptions().setRotation(Math.atan2(horizontal.y, horizontal.x))
                        .setScale(cast_vertices[1].distanceTo(cast_vertices[0])/width,
                                                    cast_vertices[3].distanceTo(cast_vertices[0])/height));
    }
}
