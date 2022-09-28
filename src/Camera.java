
import java.lang.Math;


public class Camera {
    private Point3D cameraPos;
    private double hAngle;

    private double vAngle;
    private double fov;

    public Camera(Point3D cameraPos, float hAngle, float vAngle, float fov) {
        this.cameraPos = cameraPos;
        this.hAngle = Math.toRadians(hAngle);
        this.vAngle = Math.toRadians(vAngle);
        this.fov = Math.toRadians(fov);
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
}
