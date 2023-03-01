import java.awt.*;
public class MovingBall extends Ball{

    private double dx, dy;

    public MovingBall(double x,double y, double size, Color c, double dx, double dy){
        super(x, y, size, c);
        this.dx = dx;
        this.dy = dy;
    }

    public void setDy(double y){ dy = y;}

    public double getDy(){return dy;}

    public void move(){
        setFrame(getX() + dx,getY() + dy);
        if ((this.getCenterX() > BallGameTemplate.frameWidth || this.getCenterX() < (this.getSize()/2))){
                dx *= -1;
        } else if ((this.getCenterY() > BallGameTemplate.frameHeight || this.getCenterY() < (this.getSize()/2))){
                dy *= -1;
        }
    }
}
