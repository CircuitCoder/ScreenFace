package tk.circuitcoder.lab.ScreenFace;

import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static int rc;
	protected static int ow;
	
	@Override
	public void paint(Graphics g) {
		int w=this.getWidth();
		int h=this.getHeight();
		int x=this.getX();
		int y=this.getY();
		
		g.setColor(this.getBackground());
		g.fillRoundRect(x,y,w,h,rc,rc);
		
		g.setColor(this.getForeground());
		g.fillRoundRect(x+ow,y+ow,w-2*ow,h-2*ow,rc-ow,rc-ow);
	}
	
	public static void setRC(int c) {
		rc=c;
	}
	
	public static void setOW(int w) {
		ow=w;
	}
}
