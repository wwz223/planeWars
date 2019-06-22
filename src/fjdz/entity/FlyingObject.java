package fjdz.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 飞行物父类
 * 有宽，高，坐标，以及step（）方法
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
	
	/*加载图片的方法 fileName是图片路劲*/
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	/*从数组里面获取某一张图片，需要子类重写*/
	public BufferedImage getImage() {return null;};
	
	/*画图片*/
	public void paint(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
	
	/*判断对象状态*/
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
	
	/*移动*/
	public void step() {}

	/*判断越界,默认是不越界*/
	public boolean outOfBounds() {
		return false;
	}
	
	/*碰撞*/
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
