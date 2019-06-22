package fjdz.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	private int doubleFire;
	private int index;
	private int deadIndex;
	private int bulletType = 1;
	private static BufferedImage[] images;
	//加载图片
	static {
		images = new BufferedImage[2];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("hero" + i + ".png");
		}
	}
	/*构造方法*/
	public Hero(){
		width = 97;
		height = 124;
		x = 140;
		y = 500;
		//y = World.HEIGHT;
		//life = 3;
		doubleFire = 0;
		int index = 0;
		int deadIndex = 2;
		bulletType = 1;
	}
	
	/*根据状态获取相应的图片*/
	public BufferedImage getImage() {
		if(isLife()){
			return images[index++%2];
		}else if(isDead()) {
			BufferedImage img = images[deadIndex++];
			if(deadIndex == images.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	

	/*移动英雄机方法*/
	public void moveTo(int x,int y) {
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	/*生成子弹*/
	public Bullet shoot() {
		return new Bullet(x+width/2,y-10);
	}
	/*生成两列子弹*/
	public Bullet[] doubleShoot() {
		Bullet[] bullets = new Bullet[2];
		bullets[0] = new Bullet(x+15,y-10);
		bullets[1] = new Bullet(x+width-15,y-10);
		
		return bullets;
	}
	
	/*三排子弹*/
	public Bullet[] thirdShoot() {
		Bullet[] bullets = new Bullet[3];
		bullets[0] = new Bullet(x+15,y-10);
		bullets[1] = new Bullet(x+width/2,y-10);
		bullets[2] = new Bullet(x+width-15,y-10);
		
		return bullets;
	}
	/*四排子弹*/
	public Bullet[] fourShoot() {
		Bullet[] bullets = new Bullet[4];
		bullets[0] = new Bullet(x+width/2-16,y-10);
		bullets[1] = new Bullet(x+width/2-8,y-10);
		bullets[2] = new Bullet(x+width/2,y-10);
		bullets[3] = new Bullet(x+width/2+8,y-10);
		
		return bullets;
	}
	
	/*升级武器*/
	public void upBulletType() {
		this.bulletType ++;
	}
	
	public int getBulletType() {
		return bulletType;
	}

	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}
	

	
	
	
}
