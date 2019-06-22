package fjdz.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * �ɻ���ս
 * ���裺
 * 	1������6�����󣬴���world�����
 * 	2����6����������ӹ��췽����������
 * 	3������������󣬹���FlyingObject�࣬�ع������࣬����
 *  4������Ա����˽�л����������л�����д����ķ���
 *  5����static�м���ͼƬ
 *  	1��
 * 	6��������
 * @author dell
 *
 */
/*ҵ����*/
public class World extends JPanel{
	/*������Ϸ״̬��ͼƬ*/
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage sucess;
	
	static {
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
		sucess = FlyingObject.loadImage("sucess.png");
	}
	
	/*д��Ϸ��״̬��������ͬ�ڷ������״̬*/
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int OVER = 3;
	public static final int SUCESS = 4;
	//��ʼ״̬Ϊ��ʼ
	public int state;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	
	private int intervel = 5; //���ö�ʱ��������
	private int index;   //ҳ��ˢ�´���
	public int score; //��Ϸ����
	public int sucessScore = 500; //�ɹ���Ҫ�ķ���
	public int heroLifeNum;  //Ӣ�ۻ���������
	
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {};  //��������
	private Bullet[] bullets = {};		 //�ӵ�����
	
	Random ran = new Random();
	
	/*���¿�ʼ��Ϸ*/
	public void init() {
		state = START;
		index = 0;
		score = 0;
		heroLifeNum = 3;
		sky = new Sky();
		hero = new Hero();
		enemies = new FlyingObject[] {};
		bullets = new Bullet[] {};
	}
	/* ��һ����֮�����¿�ʼ*/
	public void again () {
		index = 0;
		score = 0;
		heroLifeNum--;
	}
	
	/*���ɵл�*/
	public FlyingObject nextOne() {
		int type = ran.nextInt(20);
		//System.out.println(type);
		if(type < 2) {
			return new Bee(); 
		}else if(type<10) {
			return new Airplane();
		}else{
			return new BigAirplane();
		}
	}
		
	/*���ɵл����볡���˷����������˵л���������*/
	//��δ��뱻��ʱ�����ã���ʱ������40�Σ��Ż����һ�ܵл�
	
	/*��һ��ʱ�������������һ���ɻ�*/
	public void enterAction() {
		if(index % 100 == 0) {
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1] = nextOne();
		}
	}
	
	/*���䵥���ӵ�*/ 
	public void shootType1() {
		if(index % 50 ==0) {
			bullets = Arrays.copyOf(bullets, bullets.length + 1);
			bullets[bullets.length-1] = hero.shoot();
		}
	}
	/*����˫���ӵ�*/
	public void shootType2() {
		if(index % 50 ==0) {
			Bullet[] bullets2  = new Bullet[2];
			bullets2 = hero.doubleShoot();
			bullets = Arrays.copyOf(bullets, bullets.length + 1);
			//���������ط��ӵ�
			if((index) % 100 == 0) {
				bullets[bullets.length-1] = bullets2[0];
			}else {
				bullets[bullets.length-1] = bullets2[1];
			}
		}
	}
	
	/*���������ӵ�*/
	public void shootType3() {
		if(index % 50 ==0) {
			Bullet[] bullets3 = new Bullet[3];
			bullets3 = hero.thirdShoot();
			bullets = Arrays.copyOf(bullets, bullets.length + 3);
			bullets[bullets.length-3] = bullets3[0];
			bullets[bullets.length-2] = bullets3[1];
			bullets[bullets.length-1] = bullets3[2];
		}
	}
	/*���������ӵ�*/
	public void shootType4() {
		if(index % 50 ==0) {
			Bullet[] bullets4 = new Bullet[4];
			bullets4 = hero.fourShoot();
			bullets = Arrays.copyOf(bullets, bullets.length + 4);
			bullets[bullets.length-4] = bullets4[0];
			bullets[bullets.length-3] = bullets4[1];
			bullets[bullets.length-2] = bullets4[2];
			bullets[bullets.length-1] = bullets4[3];
		}
	}
	/*ѡ�����ӵ�����*/
	public void shootAction(int bulletType) {
		switch(bulletType) {
			case 1:
				shootType1();
				break;
			case 2:
				shootType2();
			case 3:
				shootType3();
				break;
			case 4:
				shootType4();
			default:
				break;
		}
		
	}
	
	
	//�Զ���һ���ƶ��߼���װ��ʹ��գ��ӵ����л���������
	public void stepAction() {
		sky.step();
		for(int i = 0;i<enemies.length;i++) {
			enemies[i].step();
		}
		for(int i = 0;i<bullets.length;i++) {
			bullets[i].step();
		}
	}
	
	/**
	 * ������з������Ƿ�Խ��ķ���
	 * �л����ӵ�һ��Խ��ʹ�������ɾ��
	 */
	public void outOfBounds() {
		FlyingObject[] livesEnemies = {};
		Bullet[] livesBullets = {};
		for(int i = 0;i<enemies.length;i++) {
			if(!enemies[i].outOfBounds() && !enemies[i].isRemove()) {
				livesEnemies = Arrays.copyOf(livesEnemies, livesEnemies.length + 1);
				livesEnemies[livesEnemies.length - 1] = enemies[i];
			}
		}
		for(int i = 0;i<bullets.length;i++) {
			if(!bullets[i].outOfBounds() && !bullets[i].isRemove()) {
				livesBullets = Arrays.copyOf(livesBullets, livesBullets.length + 1);
				livesBullets[livesBullets.length - 1] = bullets[i]; 
			}
		}
		
		enemies = livesEnemies;  //���¸�ֵ
		bullets = livesBullets;
	}
	
	/*��ӷ�������ײ����**/
	public void hitEnemyAction() {
		//����ӵ��ͷ�������ײ�߼�
		for(int i = 0;i<bullets.length;i++) {
			Bullet b = bullets[i];
			for(int j = 0;j<enemies.length;j++) {
				FlyingObject f = enemies[j];
				if(f.isLife() && b.isLife() && f.hit(b)) {
					if(f instanceof Bee) {
						if(hero.getBulletType() < 4) {
							int type = ran.nextInt(2);
							if(type<1) {
								hero.upBulletType();
							}else {
								heroLifeNum++;
								//hero.addLife();
							}
						}else {
							heroLifeNum++;
							//hero.addLife();
						}
						
					}else {
						Enemy e = (Enemy)f;
						score += e.getScore();
						//f = (FlyingObject) e;
					}
					f.goDead();
					b.goDead();
				}
			}
		}
	}	
	
	/*Ӣ�ۻ���ײ����*/
	public void hitHeroAction() {
		for(int i = 0;i<enemies.length;i++) {
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.hit(f)) {
				f.goDead();
				//hero.substractLife();
				again();
				hero = new Hero();
			}
		}
	}
	
	public void checkState() {
		if(heroLifeNum<=0) {
			state = OVER;
		}
		if(score>sucessScore) {
			state = SUCESS;
		}
	}
	public void action() {
		//����������¼�
		init();
		MouseAdapter mouse = new MouseAdapter() {
			/*����ƶ��¼�*/
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x,y);
				}
			}
			/*��굥���¼�*/
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				if(state == START) {
					state = RUNNING;
				}
				if(state == OVER) {
					init();
				}
				if(state == SUCESS) {
					init();
				}
				//super.mouseClicked(e);
			}
			/*����Ƴ��¼�*/
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
			/*����Ƴ�֮���������¼�*/
			@Override
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		//this.addKeyListener(mp);
		/*��ʱ��*/
		intervel -= index/1000;
		Timer timer = new Timer(); 
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(state == RUNNING) {
					//���״̬
					checkState();
					//�������鲢�����洫��һ������
					enterAction();
					//�ӵ����
					shootAction(hero.getBulletType());
					index++;
					//�л����ӵ��ƶ�
					stepAction();
					//��ײ����
					hitEnemyAction();
					hitHeroAction();
					//ɾ��Խ�����ײ�ķɻ����ӵ�
					outOfBounds();
				}
				repaint();
			}
		},0,intervel);	
	}
		
	@Override
	/*��дpaint����*/
	public void paint(Graphics g) {
		// TODO �Զ����ɵķ������
		super.paint(g);
		sky.paint(g);
		hero.paint(g);
		//System.out.println(Arrays.toString(enemies));
		for(int i = 0;i<enemies.length;i++) {
			enemies[i].paint(g);
		}
		for(int i = 0;i<bullets.length;i++) {
			bullets[i].paint(g);
		}
		
		g.drawString("Score:"+ score, 20, 20);
		g.drawString("Life:" + heroLifeNum, 20, 50);
		g.drawString("index:"+index, 20, 70);
		
		/*����״̬��ͼƬ*/
		switch(state) {
			case START:
				g.drawImage(start, 0,0,null);
				break;
			case PAUSE:
				g.drawImage(pause, 0,0,null);
				break;
			case OVER:
				g.drawImage(gameover,0,0, null);
				break;
			case SUCESS:
				g.drawImage(sucess,0,0, null);
				break;
			default:break;
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT); //���ô�С
		frame.setResizable(false);//�������ô�С
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		world.action();
		frame.add(world);
	
	}

	
	
	
}
