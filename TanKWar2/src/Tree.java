import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * �������֣���
 */

public class Tree {
//	public static final int width = 30;
//	public static final int length = 30;
	int x, y;
	GameFrame tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[] { tk.getImage(BrickWall.class.getResource("Images/tree.gif")), };
	}

	public Tree(int x, int y, GameFrame tc) { // Tree�Ĺ��췽��������x��y��tc����
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { // ������
		g.drawImage(treeImags[0], x, y, null);
	}

}
