import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * ̹���ࣨ���õз�̹�˺����̹�ˣ�
 */

public class Tank implements Config{
	public static int speedX = 6, speedY = 6; // ��̬ȫ�ֱ����ٶ�
	public static int count = 0;
	public static final int width = 35, length = 35; // ̹�˵�ȫ�ִ�С�����в��ɸı���
	private Direction direction = Direction.STOP; // ��ʼ��״̬Ϊ��ֹ
	private Direction Kdirection = Direction.U; // ��ʼ������Ϊ����
	GameFrame tc;

	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; // ��ʼ��Ϊ����
	private int life = 200; // ��ʼ����ֵ

	private static Random r = new Random();
	private int step = r.nextInt(10) + 5; // ����һ�������,���ģ��̹�˵��ƶ�·��

	private boolean bL = false, bU = false, bR = false, bD = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();// �������
	private static Image[] tankImgs = null; // �洢ȫ�־�̬
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImgs = new Image[] { 
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")), };
		imgs.put("L", tankImgs[0]);
		imgs.put("LU", tankImgs[1]);
		imgs.put("U", tankImgs[2]);
		imgs.put("RU", tankImgs[3]);
		imgs.put("R", tankImgs[4]);
		imgs.put("RD", tankImgs[5]);
		imgs.put("D", tankImgs[6]);
		imgs.put("LD", tankImgs[7]);
	}

	public Tank(int x, int y, boolean good) {// Tank�Ĺ��캯��1
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, GameFrame tc) {// Tank�Ĺ��캯��2
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); // ɾ����Ч��
			}
			return;
		}

		if (good)
			new DrawBloodbBar().draw(g); // ���̹�˵�Ѫ����

		switch (Kdirection) {
		// ���ݷ���ѡ��̹�˵�ͼƬ
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		
		}

		move(); // ����move����
	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch (direction) { // ѡ���ƶ�����
		case L:
			x -= speedX;
			break;
		case LU:
			x -= speedX;
			y -= speedY;
			break;
		case U:
			y -= speedY;
			break;
		case RU:
			x += speedX;
			y -= speedY;
		case R:
			x += speedX;
			break;
		case RD:
			x += speedX;
			y += speedY;
		case D:
			y += speedY;
			break;
		case LD:
			x -= speedX;
			y += speedY;
		case STOP:
			break;
		}

		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}

		if (x < 0)
			x = 0;
		if (y < 40) // ��ֹ�߳��涨����
			y = 40;
		if (x + Tank.width > GameFrame.Fram_width) // ����������ָ����߽�
			x = GameFrame.Fram_width - Tank.width;
		if (y + Tank.length > GameFrame.Fram_length)
			y = GameFrame.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3; // �������·��
				int rn = r.nextInt(directons.length);
				direction = directons[rn]; // �����������
			}
			step--;

			if (r.nextInt(40) > 38)// ��������������Ƶ��˿���
				this.fire();
		}
	}

	private void changToOldDir() {
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) { // ���ܼ����¼�
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R: // ������Rʱ�����¿�ʼ��Ϸ
			tc.tanks.clear(); // ����
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			if (tc.tanks.size() == 0) { // ����������û��̹��ʱ���ͳ���̹��
				for (int i = 0; i < 20; i++) {
					if (i < 9) // ����̹�˳��ֵ�λ��
						tc.tanks.add(new Tank(150 + 70 * i, 40, false, Direction.R, tc));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D, tc));
					else
						tc.tanks.add(new Tank(10, 50 * (i - 12), false, Direction.L, tc));
				}
			}

			tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc);// �����Լ����ֵ�λ��

			if (!tc.home.isLive()) // ��home��������
				tc.home.setLive(true);
			new GameFrame(); // ���´������
			break;
		case KeyEvent.VK_RIGHT: // �������Ҽ�
			bR = true;
			break;

		case KeyEvent.VK_LEFT:// ���������
			bL = true;
			break;

		case KeyEvent.VK_UP: // �������ϼ�
			bU = true;
			break;

		case KeyEvent.VK_DOWN:// �������¼�
			bD = true;
			break;
		}
		decideDirection();// ���ú���ȷ���ƶ�����
	}

	void decideDirection() {
		if (!bL && !bU && bR && !bD) // �����ƶ�
			direction = Direction.R;
		else if(bL && bU && !bR && !bD) 
			direction= Direction.LU;
		else if (bL && !bU && !bR && !bD) // ������
			direction = Direction.L;
		else if(!bL && bU && bR && !bD) 
			direction = Direction.RU;
		else if (!bL && bU && !bR && !bD) // �����ƶ�
			direction = Direction.U;
		else if(!bL && !bU && bR && bD)
			direction = Direction.RD;
		else if (!bL && !bU && !bR && bD) // �����ƶ�
			direction = Direction.D;
		else if(bL && !bU && !bR && bD) 
			direction = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP; // û�а������ͱ��ֲ���
	
	}

	public void keyReleased(KeyEvent e) { // �����ͷż���
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_F:
			fire();
			break;

		case KeyEvent.VK_RIGHT:
			bR = false;
			break;

		case KeyEvent.VK_LEFT:
			bL = false;
			break;

		case KeyEvent.VK_UP:
			bU = false;
			break;

		case KeyEvent.VK_DOWN:
			bD = false;
			break;
			
			//��A����ǿ��
		case KeyEvent.VK_A:
			superFire();
			break;

		}
		decideDirection(); // �ͷż��̺�ȷ���ƶ�����
	}

	public Bullets fire() { // ���𷽷�
		if (!live)
			return null;
		int x = this.x + Tank.width / 2 - Bulletswidth / 2; // ����λ��
		int y = this.y + Tank.length / 2 - Bulletslength / 2;
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc); // û�и�������ʱ����ԭ���ķ��򷢻�
		tc.bullets.add(m);
		return m;
	}
	
	public Bullets fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.width / 2 - Bulletswidth / 2; // ����λ��
		int y = this.y + Tank.length / 2 - Bulletslength / 2;
		Bullets m = new Bullets(x, y+2, good,dir, this.tc);
		tc.bullets.add(m);
		return m;
	}
	
	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(BrickWall w) { // ��ײ����ͨǽʱ
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir(); // ת����ԭ���ķ�����ȥ
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) { // ײ������ǽ
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) { // ײ��������ʱ��
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) { // ײ���ҵ�ʱ��
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {// ײ��̹��ʱ
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);// ��ʾ���̹�˵�Ѫ����
			int w = width * life / 200;
			g.fillRect(375, 585, w, 10);// ��ʾ���̹�˵�Ѫ����
			g.setColor(c);
		}
	}

	public boolean eat(Blood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if (this.life <= 100)
				this.life = this.life + 100; // ÿ��һ��������100������
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}