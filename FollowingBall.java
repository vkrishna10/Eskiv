import java.awt.*;
public class FollowingBall extends Ball {

    private double deltaX;
    private double deltaY;

    private double unitX;
    private double unitY;

    private double speed = 1;

    public FollowingBall(double x,double y, double size, Color c) {
        super(x, y, size, c);
      
    }

    public void move(){
        deltaX = (this.getCenterX() - BallGameTemplate.ball.getCenterX()) * -1;
        deltaY = (this.getCenterY() - BallGameTemplate.ball.getCenterY()) * -1;

        double dist = Math.sqrt((deltaY * deltaY) + (deltaX * deltaX));

        unitX = deltaX/dist;// Components of the Unit Vector
        unitY = deltaY/dist;// Components of the Unit Vector

        double xM = speed * unitX;
        double yM = speed * unitY;
        
        setFrame(getX() + xM ,getY() + yM);
       
    }

    // public static double pythag(double x, double y){
    //     double xSqr = x * x;
    //     double ySqr = y * y;

    //     return Math.sqrt((xSqr + ySqr));
    // }
    
}
