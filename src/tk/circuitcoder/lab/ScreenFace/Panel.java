package tk.circuitcoder.lab.ScreenFace;

import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	int rc;
	int ow;
	
	public Panel(int c,int w) {
		this.rc=c;
		this.ow=w;
	}
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
}
