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
 * 飞机大战
 * 步骤：
 * 	1、创建6个对象，创建world类测试
 * 	2、给6个对象类添加构造方法，并测试
 * 	3、构造数组对象，构造FlyingObject类，重构对象类，测试
 *  4、给成员属性私有化，方法公有化，重写父类的方法
 *  5、在static中加载图片
 *  	1）
 * 	6、画对象
 * @author dell
 *
 */
/*业务类*/
public class World extends JPanel{
	/*加载游戏状态的图片*/
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
	
	/*写游戏的状态常量，不同于飞行物的状态*/
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int OVER = 3;
	public static final int SUCESS = 4;
	//初始状态为开始
	public int state;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	
	private int intervel = 5; //设置定时器的周期
	private int index;   //页面刷新次数
	public int score; //游戏分数
	public int sucessScore = 500; //成功需要的分数
	public int heroLifeNum;  //英雄机生命个数
	
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {};  //敌人数组
	private Bullet[] bullets = {};		 //子弹数组
	
	Random ran = new Random();
	
	/*重新开始游戏*/
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
	/* 掉一条命之后重新开始*/
	public void again () {
		index = 0;
		score = 0;
		heroLifeNum--;
	}
	
	/*生成敌机*/
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
		
	/*生成敌机并入场，此方法，限制了敌机生成评率*/
	//这段代码被定时器调用，定时器运行40次，才会调用一架敌机
	
	/*隔一段时间往画板中添加一个飞机*/
	public void enterAction() {
		if(index % 100 == 0) {
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1] = nextOne();
		}
	}
	
	/*发射单排子弹*/ 
	public void shootType1() {
		if(index % 50 ==0) {
			bullets = Arrays.copyOf(bullets, bullets.length + 1);
			bullets[bullets.length-1] = hero.shoot();
		}
	}
	/*发射双排子弹*/
	public void shootType2() {
		if(index % 50 ==0) {
			Bullet[] bullets2  = new Bullet[2];
			bullets2 = hero.doubleShoot();
			bullets = Arrays.copyOf(bullets, bullets.length + 1);
			//左右炮来回发子弹
			if((index) % 100 == 0) {
				bullets[bullets.length-1] = bullets2[0];
			}else {
				bullets[bullets.length-1] = bullets2[1];
			}
		}
	}
	
	/*发射三排子弹*/
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
	/*发射四排子弹*/
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
	/*选择发射子弹种类*/
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
	
	
	//自定义一个移动逻辑封装，使天空，子弹，敌机都飞起来
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
	 * 检测所有飞行物是否越界的方法
	 * 敌机和子弹一旦越界就从数组里删除
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
		
		enemies = livesEnemies;  //重新赋值
		bullets = livesBullets;
	}
	
	/*添加飞行物碰撞方法**/
	public void hitEnemyAction() {
		//检测子弹和飞行物碰撞逻辑
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
	
	/*英雄机碰撞方法*/
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
		//添加鼠标监听事件
		init();
		MouseAdapter mouse = new MouseAdapter() {
			/*鼠标移动事件*/
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x,y);
				}
			}
			/*鼠标单击事件*/
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
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
			/*鼠标移出事件*/
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
			/*鼠标移出之后再移入事件*/
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
		/*定时器*/
		intervel -= index/1000;
		Timer timer = new Timer(); 
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(state == RUNNING) {
					//检查状态
					checkState();
					//扩容数组并向里面传入一个数据
					enterAction();
					//子弹射击
					shootAction(hero.getBulletType());
					index++;
					//敌机和子弹移动
					stepAction();
					//碰撞方法
					hitEnemyAction();
					hitHeroAction();
					//删除越界和碰撞的飞机和子弹
					outOfBounds();
				}
				repaint();
			}
		},0,intervel);	
	}
		
	@Override
	/*重写paint方法*/
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
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
		
		/*根据状态画图片*/
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
		frame.setSize(WIDTH,HEIGHT); //设置大小
		frame.setResizable(false);//不能重置大小
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		world.action();
		frame.add(world);
	
	}

	
	
	
}
