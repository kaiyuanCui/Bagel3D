public class Frames {
    private static final double STANDARD_FPS = 60;
    private static final long SECOND_TO_NANO = 1000000000;
    private static int frames;
    private static long startTime = System.nanoTime();
    private static double fps = STANDARD_FPS;



    public static double fps(){ // this should run one time and one time only every frame
        frames++;
        long elapsedTime = System.nanoTime() - startTime;
        if ( elapsedTime > 0.5 * SECOND_TO_NANO){
            fps =  frames * 2;
            frames = 0;
            startTime = System.nanoTime();

        }
        return fps;
    }

    public static int getCurrentFrame(){
        return frames;
    }
}
