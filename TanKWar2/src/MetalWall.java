import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * ����ǽ�ࣨ�ְ�,�ӵ����ɴ򴩣�
 */
public class MetalWall implements Config{
//	public static final int width = 30; // ���ý���ǽ�ĳ���̬ȫ�ֲ���
//	public static final int length = 30;
	private int x, y;
	GameFrame tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(BrickWall.class.getResource("Images/metalWall.gif")), };
	}

	public MetalWall(int x, int y, GameFrame tc) {// ���캯��������Ҫ����ĳ�����ֵ
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { // ������ǽ
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() { // ����ָ�������ĳ�����ʵ��
		return new Rectangle(x, y, MetalWallwidth, MetalWalllength);
	}
}
