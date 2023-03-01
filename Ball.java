import java.awt.geom.*;
import java.awt.*;
public class Ball extends Ellipse2D.Double {

  private Color color;
  private double size;

  public Ball(double x,double y, double size, Color c){
		super(x,y,size, size);
   	 	this.size = size;
		color = c;
  }

  public void move(){
    setFrame(getX(),getY());
  }

  public Color getColor() { return color; }
  public void setX(){}

  public double getCenterX(){
    return getX() + size/2;
  }

  public double getCenterY(){
    return getY() + size/2;
  }

  public double getSize() {return size;}

  public void setFrame(double x, double y){
    setFrame(x,y,size,size);
  }

}