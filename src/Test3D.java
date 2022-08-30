

import bagel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class Test3D extends AbstractGame {
    private Point3D cameraPos;
    private Camera camera;

    private Object3D cube;

    private List<Object3D> objects = new ArrayList<Object3D>();

    private Object3D xAxis;
    private Object3D yAxis;
    private Object3D zAxis;


    private final double STANDARD_FPS = 60;
    private final long SECOND_TO_NANO = 1000000000;
    private long frames;
    private long startTime;
    private double fps = STANDARD_FPS;



    private Font defaultFont;



    public Test3D() {
        super(1280, 720, "Bagel3D");

        cameraPos = new Point3D(500, 400, 300);
        camera = new Camera(cameraPos, 0, 90);

        // for testing purposes only
        cube = new Cuboid(new Point3D(300, 400 ,300), 600, 60, 600).createObject();
        objects.add(cube);
        objects.add(new Cuboid(new Point3D(100, 50 ,50), 30, 70, 30).createObject());
        objects.add(new Cuboid(new Point3D(300, 700 ,300), 120, 100, 100).createObject());
        objects.add(new Cuboid(new Point3D(900, -50 ,-50), 100, 100, 100).createObject());
        xAxis = new Cuboid(new Point3D(-5000, 0 ,0), 5000 + camera.getCameraPos().x - 1,0, 0).createObject();
        yAxis = new Cuboid(new Point3D(0, -5000 ,0), 0, 10000, 0).createObject();
        zAxis = new Cuboid(new Point3D(0, 0 ,-5000), 0, 0, 10000).createObject();

        startTime = System.nanoTime();
        defaultFont = new Font("res/conformable.otf", 99 );

    }

    public static void main(String[] args) {
        // GameObject a = new GameObject("R", new Point(400, 300), 6, 8);
        // System.out.println(a.getRadius(new Vector2(4, 3)));
        Test3D game = new Test3D();
        game.run();
    }

    @Override
    public void update(Input input) {

        // draw axis first so they do not obstruct other objects
        xAxis = new Cuboid(new Point3D(-5000, 0 ,0), 4999 + camera.getCameraPos().x,0, 0).createObject();
        xAxis.getPoints(camera, Window.getWidth(), Window.getHeight());
        xAxis.drawObject();
        yAxis.getPoints(camera, Window.getWidth(), Window.getHeight());
        yAxis.drawObject();
        zAxis.getPoints(camera, Window.getWidth(), Window.getHeight());
        zAxis.drawObject();


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

        cameraPos = new Point3D(cameraPos.toVector().add(move.rotateAroundZ(-1 * camera.gethAngle())));
        camera.setCameraPos(cameraPos);

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }


        // draw objects
        for (Object3D obj: objects){
            obj.getPoints(camera, Window.getWidth(), Window.getHeight() );
            obj.drawObject();
        }

        cube.rotateAroundZ(0.1 );
        cube.getPoints(camera, Window.getWidth(), Window.getHeight());
        cube.drawObject();

        Rectangle3D rect = new Rectangle3D((cube.getPos()), 60, 60, cameraPos.toVector());
        rect.draw(camera, Window.getWidth(), Window.getHeight());

        int length = 100;
        int bigLength = 1000;
        for(int i = 0; i < bigLength; i+=length){
            for(int j = 0; j < bigLength; j+=length) {
                rect = new Rectangle3D(new Point3D(j, i, 0), length, length, cameraPos.toVector());
                rect.draw(camera, Window.getWidth(), Window.getHeight());
            }

        }

        frames++;
        long elapsedTime = System.nanoTime() - startTime;
        if ( elapsedTime > 0.5 * SECOND_TO_NANO){
            fps =  frames * 2;
            frames = 0;
            startTime = System.nanoTime();

        }

        defaultFont.drawString("FPS: " + fps, 20, 50);
    }


}