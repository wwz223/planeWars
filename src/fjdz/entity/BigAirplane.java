package fjdz.entity;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements Enemy{
	
	private int step;
	private int deadIndex;
	private int score = 5;
	private int life = 3;
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("bigplane" + i + ".png");
		}
	}
	
	public BigAirplane() {
		width = 66;
		height = 99;
		x = (int)(Math.random()*(World.WIDTH - this.width));
		y = -this.height;
		step = 1; 
		deadIndex = 0;
	}
	
	/*获取图片方法*/
	@Override
	public BufferedImage getImage() {
		// TODO 自动生成的方法存根
		if(isLife()) {
			return images[0];
		}else if(isDead()) {		
			BufferedImage img =  images[deadIndex++];
			if(deadIndex == images.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}

	public void step() {
		y += step;
	}
	
	/*重写父类越界方法*/
	public boolean outOfBounds() {
		if(this.y > World.HEIGHT) {
			return true;
		}	
		return false;
	}
	

	@Override
	public int getScore() {
		// TODO 自动生成的方法存根
		return this.score;
	}
	
	public void goDead() {
		if(life > 0) {
			life --;
		}else {
			state = DEAD;
		}
	}
}
