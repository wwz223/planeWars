package fjdz.entity;

import java.awt.image.BufferedImage;

public class Bee extends FlyingObject{
	
	private int xStep;
	private int yStep;
	private int awardType;
	private int deadIndex;
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("bee" + i + ".png");
		}
	}
	/*构造方法*/
	public Bee() {
		width = 60;
		height = 50;
		x = (int)(Math.random()*(World.WIDTH - this.width));
		y = -this.height;
		xStep = (int)(Math.random()*4) - 2;
		yStep = 2;
		awardType = (int)(Math.random()*2);
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
		x += xStep;
		y += yStep;
		//当越界以后，x反方向飘
		if(x<0 || x>World.WIDTH-this.width) {
			xStep *= -1;
		}
	}
	
	/*重写父类越界方法*/
	public boolean outOfBounds() {
		if(this.y > World.HEIGHT) {
			return true;
		}
		return false;
	}
	

	
}
