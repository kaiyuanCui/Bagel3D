package Bagel3D;

import Bagel3D.util.Point3D;

import java.lang.Math;

/**
 * Class to represent the point of view of the player
 *
 * @author kaiyuanCui
 */


public class Camera {
    private static Camera _camera = null;
    private Point3D cameraPos;
    private double hAngle;

    private double vAngle;
    private double fov;

    private int height;
    private int width;

    private double screenDist ;

    private Camera() {
        // default values
        this.cameraPos = new Point3D(0, 0, 0);
        this.hAngle = 0;
        this.vAngle = 0;
        this.fov = 90;
        this.width = 1280;
        this.height = 720;
        this.screenDist = (width / 2) / Math.tan(fov / 2);
    }

    public static Camera getInstance(){
        if(_camera == null){
            _camera = new Camera();
        }
        return _camera;
    }

    public Point3D getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(Point3D cameraPos) {
        this.cameraPos = cameraPos;
    }

    public double gethAngle() {
        return hAngle;
    }
    public double getvAngle() { return vAngle; }

    public void hTurn(double angle){
        hAngle += Math.toRadians(angle);
    }
    public void vTurn(double angle){
        double target = vAngle + Math.toRadians(angle);
        if (target > Math.PI/2){
            vAngle = Math.PI/2;
        }
        else if (target < - Math.PI/2){
            vAngle = - Math.PI/2;
        }
        else
        {
            vAngle = target;
        }
    }

    public double getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }


    public void sethAngle(double hAngle) {
        this.hAngle = hAngle;
    }

    public void setvAngle(double vAngle) {
        this.vAngle = vAngle;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getScreenDist() {
        return screenDist;
    }

    public void setScreenDist(double screenDist) {
        this.screenDist = screenDist;
    }
}
