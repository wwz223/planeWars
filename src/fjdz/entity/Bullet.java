package fjdz.entity;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
	private int step;
	private static BufferedImage image;
	static {
			image = loadImage("bullet.png");
	}
	
	//传入英雄机飞机头的坐标
	public Bullet(int x,int y){
		width = 8;
		height = 14;
		this.x = x-this.width/2;
		this.y = y;
		step = 3;
	}
	
	/*获取图片方法*/
	@Override
	public BufferedImage getImage() {
		// TODO 自动生成的方法存根
		if(isLife()) {
			return image;
		}else if(isDead()) {
			state = REMOVE;
		}
		return null;
	}
	
	public void step() {
		// TODO 自动生成的方法存根
		y -= step;
	}
	
	public boolean outOfBounds() {
		return this.y <= -this.height ; 
	}
}
