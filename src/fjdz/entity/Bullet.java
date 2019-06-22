package fjdz.entity;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
	private int step;
	private static BufferedImage image;
	static {
			image = loadImage("bullet.png");
	}
	
	//����Ӣ�ۻ��ɻ�ͷ������
	public Bullet(int x,int y){
		width = 8;
		height = 14;
		this.x = x-this.width/2;
		this.y = y;
		step = 3;
	}
	
	/*��ȡͼƬ����*/
	@Override
	public BufferedImage getImage() {
		// TODO �Զ����ɵķ������
		if(isLife()) {
			return image;
		}else if(isDead()) {
			state = REMOVE;
		}
		return null;
	}
	
	public void step() {
		// TODO �Զ����ɵķ������
		y -= step;
	}
	
	public boolean outOfBounds() {
		return this.y <= -this.height ; 
	}
}
