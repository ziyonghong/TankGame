import java.awt.*;

/**
 * שǽ�ࣨ�ӵ��ɴ򴩣�
 */
public class BrickWall implements Config{
//	public static final int width = 20; // ����ǽ�Ĺ̶�����
//	public static final int length = 20;
	int x, y;

	GameFrame tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { // ����commonWall��ͼƬ
				tk.getImage(BrickWall.class.getResource("Images/commonWall.gif")), };
	}

	public BrickWall(int x, int y, GameFrame tc) { // ���캯��
		this.x = x;
		this.y = y;
		this.tc = tc; // ��ý������
	}

	public void draw(Graphics g) {// ��commonWall
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() { // ����ָ�������ĳ�����ʵ��
		return new Rectangle(x, y, BrickWallwidth, BrickWalllength);
	}
}
