

import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class Test3D extends AbstractGame {
    private static Point3D cameraPos = new Point3D(0, 0, 700);
    private Camera camera;

    private static Point mousePos = new Point(0, 0);

    private Object3D cube;

    private List<Object3D> objects = new ArrayList<Object3D>();

    private static ComplexObject world = new ComplexObject(new Point3D(0, 0, 0)) {
    };



    private static final double STANDARD_FPS = 60;
    private static double fps = STANDARD_FPS;

    private int frameCounter = 0;



    private Font defaultFont;



    public Test3D() {
        super(1280, 720, "Bagel3D");


        camera = new Camera(cameraPos, 135, -45,  90);



        // world generation
        int length = 100;
        int bigLength = 10000;
        for(int i = 0; i < bigLength; i+=length){
            for(int j = 0; j <= bigLength; j+=length) {

                world.addObject(new Cuboid(new Point3D(j, i, 0), length, length, length));
            }

        }
        world.addObject(new Image3D(new Point3D(0, 0, 200), "res/sinkhole.png"));
        world.addObject(new Rectangle3D(new Point3D(0, 0, 500), 100, 100, new Vector3(0,0,0)));
        world.addObject(new Rectangle3D(new Point3D(200, 0, 500), 100, 100, new Vector3(1,0,0)));
        world.addObject(new Rectangle3D(new Point3D(0, 200, 500), 100, 100, new Vector3(0,3.14159256/4,0)));
        world.addObject(new Rectangle3D(new Point3D(200, 200, 500), 100, 100, new Vector3(0,0,1)));

        //counter = new Frames(System.nanoTime());
        defaultFont = new Font("res/conformable.otf", 40 );




    }

    public static void main(String[] args) {
        // GameObject a = new GameObject("R", new Point(400, 300), 6, 8);
        // System.out.println(a.getRadius(new Vector2(4, 3)));
        Test3D game = new Test3D();
        game.run();
    }

    @Override
    public void update(Input input) {

        fps = Frames.fps();


        // camera movement
        double speed = 5 * STANDARD_FPS/fps;
        double turnSpeed = 0.5 * STANDARD_FPS/fps;

        Vector3 move = new Vector3(0,0,0);

        if(input.isDown(Keys.LEFT_SHIFT)){
            move.z -= speed;
        }

        if(input.isDown(Keys.SPACE)){
            move.z += speed;
        }

        if (input.isDown(Keys.A)) {
            move.y += speed;
        }
        if (input.isDown(Keys.D)) {
            move.y -= speed;
        }
        if (input.isDown(Keys.W)) {
            move.x -= speed;
        }
        if (input.isDown(Keys.S)) {
            move.x += speed;
        }
        if (input.isDown(Keys.LEFT)){
            camera.hTurn(turnSpeed);
        }
        if (input.isDown(Keys.RIGHT)){
            camera.hTurn(-1 * turnSpeed);
        }
        if (input.isDown(Keys.UP)){
            camera.vTurn(-1 * turnSpeed);
        }
        if (input.isDown(Keys.DOWN)){
            camera.vTurn(turnSpeed);
        }

        // check mouse movement compared to last frame
        camera.hTurn(-1 * (input.getMouseX() - mousePos.x));
        camera.vTurn((input.getMouseY() - mousePos.y));
        mousePos = new Point(input.getMouseX(), input.getMouseY());

        

        cameraPos = new Point3D(cameraPos.toVector().add(move.rotateAroundZ(-1 * camera.gethAngle())));
        camera.setCameraPos(cameraPos);

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }




        /*
        cube.rotateAroundZ(0.1 );
        cube.getPoints(camera, Window.getWidth(), Window.getHeight());
        cube.drawObject();

         */



        // draw objects
       world.draw(camera, Window.getWidth(), Window.getHeight(), cameraPos);
        /*
        for(Object3D object: objects){
            object.draw(camera, Window.getWidth(), Window.getHeight());
        }

         */



        defaultFont.drawString("FPS: " + fps, 20, 50);
        defaultFont.drawString("POS: " + cameraPos, 20, 100 );
        defaultFont.drawString("Facing: h:" + camera.gethAngle() + ", v: " + camera.getvAngle(), 20, 150);


        // crosshair
        Drawing.drawLine(new Point(Window.getWidth()/2 -15, Window.getHeight()/2),
                new Point(Window.getWidth()/2 +15, Window.getHeight()/2), 1, new Colour(0,0,0));
        Drawing.drawLine(new Point(Window.getWidth()/2 , Window.getHeight()/2 - 15),
                new Point(Window.getWidth()/2 , Window.getHeight()/2 + 15), 1, new Colour(0,0,0));
    }

    public static Point3D getCameraPos() {
        return cameraPos;
    }
}