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
	
	/*��ȡͼƬ����*/
	@Override
	public BufferedImage getImage() {
		// TODO �Զ����ɵķ������
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
	
	/*��д����Խ�緽��*/
	public boolean outOfBounds() {
		if(this.y > World.HEIGHT) {
			return true;
		}	
		return false;
	}
	

	@Override
	public int getScore() {
		// TODO �Զ����ɵķ������
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
