package fjdz.entity;

import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Enemy{
	private int step;
	private int deadIndex;
	private int score = 8;
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("airplane" + i + ".png");
		}
	}
	public Airplane() {
		width = 49;
		height = 36;
		x = (int)(Math.random()*(World.WIDTH - this.width));
		y = -this.height;
		step = 1; 
		deadIndex = 0;
	}
	
	public BufferedImage getImage() {
		if(isLife()){
			return images[0];
		}else if(isDead()) {
			BufferedImage img = images[deadIndex++];
			if(deadIndex == images.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	/*重写父类移动方法*/
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
	
}
