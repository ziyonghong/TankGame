import java.awt.*;
import java.util.Random;

/**
 * Ѫ���ࣨҽ���䣬�ɼ�Ѫ��
 */

public class Blood  implements Config{

//	public static final int width = 36;
//	public static final int length = 36;

	private int x, y;
	GameFrame tc;
	private static Random r = new Random();

	int step = 0;
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private Image[] bloodImags = new Image[] { tk.getImage(BrickWall.class.getResource("Images/hp.png")), };

	private int[][] poition = { { 155, 196 }, { 500, 58 }, { 80, 340 }, { 99, 199 }, { 345, 456 }, { 123, 321 },
			{ 258, 413 } };

	public void draw(Graphics g) {
		if (r.nextInt(100) > 98) {
			this.live = true;
			move();
		}
		if (!live)
			return;
		g.drawImage(bloodImags[0], x, y, null);

	}

	private void move() {
		step++;
		if (step == poition.length) {
			step = 0;
		}
		x = poition[step][0];
		y = poition[step][1];

	}

	public Rectangle getRect() {
		return new Rectangle(x, y, Bloodwidth, Bloodlength);
	}

	public boolean isLive() {// �ж��Ƿ񻹻���
		return live;
	}

	public void setLive(boolean live) { // ��������
		this.live = live;
	}
}