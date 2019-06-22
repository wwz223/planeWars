package fjdz.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject{
	private int step;
	private int y1;//天空移动
	private static BufferedImage image;
	static {
			image = loadImage("background.png");
	}
	
	public Sky(){
		width = World.WIDTH;
		height = World.HEIGHT;
		x = 0;
		y = 0;
		step = 1;
		y1 = -this.height;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(getImage(),this.x,this.y,null);
		g.drawImage(getImage(),this.x,this.y1,null);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void step() {	
		if(y > this.height) {
			y = -this.height;
		}
		if(y1 > this.height){
			y1 = -this.height;
		}
		y += step;
		y1 += step;
	}
	
}
