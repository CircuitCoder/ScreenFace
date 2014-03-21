package tk.circuitcoder.lab.ScreenFace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimePanel extends Panel{
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat df,tf;
	
	public TimePanel() {
		this("YY-MM-dd E", "kk:mm:ss");
	}
	
	public TimePanel(String dateF,String timeF) {
		this(dateF,timeF,50,10);
	}
	
	public TimePanel(String dateF,String timeF,int c,int w) {
		super(c,w);
		df=new SimpleDateFormat(dateF);
		tf=new SimpleDateFormat(timeF);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Date d=new Date();
		g.setColor(new Color(180,180,180));
		g.setFont(new Font(null,Font.BOLD|Font.ITALIC,18));
		g.drawString(df.format(d),ow+20,ow+100);
		
		g.setFont(new Font(null,Font.BOLD,48));
		g.setColor(new Color(100,100,100));
		g.drawString(tf.format(d),ow+20,ow+150);
		g.setColor(new Color(255,255,255));
		g.drawString(tf.format(d),ow+22,ow+152);
	}
}
