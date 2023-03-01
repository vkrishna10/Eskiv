import java.awt.*;
public class SHMBall extends MovingBall{      
        
        private double velY;
        private double t = 0;

        public SHMBall(double x, double size, Color c) {
            super(x,0,size,c, 0 , 0);
            velY = 0;
        }

        public void move(){
            
            velY = updateVel(velY);
            t += 0.01;
            double w = 1;
            setFrame(getX(), ( ((BallGameTemplate.frameHeight/2) - 10) * Math.sin( w * t)) + (BallGameTemplate.frameHeight/2) - 20);//Yi + Vydt
        }

        public double updateVel(double Vi){
           double k = 1;
           double H = BallGameTemplate.frameHeight;
           double Y = this.getY() - BallGameTemplate.frameHeight/2;
           double acc = (k * ( H - Y))/Math.sqrt((0.75 * H * H) - (Y * Y) + (2 * Y * H));//Acceleration function

           return Vi + acc * (0.01); //Vi + adt
        }
}
