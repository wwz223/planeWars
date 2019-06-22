package fjdz.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * �����︸��
 * �п��ߣ����꣬�Լ�step��������
 * @author dell
 *
 */
public class FlyingObject {
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	
	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	public int state = LIFE;
	
	/*����ͼƬ�ķ��� fileName��ͼƬ·��*/
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	/*�����������ȡĳһ��ͼƬ����Ҫ������д*/
	public BufferedImage getImage() {return null;};
	
	/*��ͼƬ*/
	public void paint(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
	
	/*�ж϶���״̬*/
	public boolean isLife() {
		return state == LIFE;
	}
	public boolean isDead() {
		return state == DEAD;
	}
	public boolean isRemove() {
		return state == REMOVE;
	}
	
	public void goDead() {
		state = DEAD; 
	}
	
	/*�ƶ�*/
	public void step() {}

	/*�ж�Խ��,Ĭ���ǲ�Խ��*/
	public boolean outOfBounds() {
		return false;
	}
	
	/*��ײ*/
	public boolean hit(FlyingObject other) {
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y - other.height;
		int y2 = this.y + this.height;
		int otherX = other.x;
		int otherY = other.y;

		return otherX>=x1 && otherX<=x2 && otherY>=y1 && otherY<=y2;
	}
	public synchronized final int getWidth() {
		return width;
	}
	public synchronized final void setWidth(int width) {
		this.width = width;
	}
	public synchronized final int getHeight() {
		return height;
	}
	public synchronized final void setHeight(int height) {
		this.height = height;
	}
	public synchronized final int getX() {
		return x;
	}
	public synchronized final void setX(int x) {
		this.x = x;
	}
	public synchronized final int getY() {
		return y;
	}
	public synchronized final void setY(int y) {
		this.y = y;
	}
	public synchronized final int getState() {
		return state;
	}
	public synchronized final void setState(int state) {
		this.state = state;
	}
	
	
	
	














}
