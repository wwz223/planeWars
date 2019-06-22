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
	/*��д�����ƶ�����*/
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
	
}
