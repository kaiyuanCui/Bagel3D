
import java.lang.Math;
public class Camera {
    private Point3D cameraPos;
    private double hAngle;

    private double vAngle;
    private double fov;

    public Camera(Point3D cameraPos, float angle, float fov) {
        this.cameraPos = cameraPos;
        this.hAngle = Math.toRadians(angle);
        this.vAngle = Math.toRadians(angle);
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
        vAngle += Math.toRadians(angle);
    }

    public double getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
