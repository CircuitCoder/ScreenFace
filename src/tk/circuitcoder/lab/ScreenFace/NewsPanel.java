package tk.circuitcoder.lab.ScreenFace;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JLabel;

public class NewsPanel extends Panel{
	private static class FadeLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		float alpha;
		int b;
		public FadeLabel(int blh) {
			alpha=1;
			b=blh;
		}
		
		public void setAlpha(float newA) {
			alpha=newA;
		}
		
		
		@Override
		public void paint(Graphics g) {
			if(g instanceof Graphics2D) {
				Graphics2D g2d=(Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
				super.paint(g2d);
				g.setColor(new Color(1,1,1,0.5F));
				g.fillRect(this.getX(),this.getY()+this.getHeight()-b,this.getWidth(),b);
			}
			else {
				super.paint(g);
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private static Font tfont = new Font(null,Font.BOLD,36);
	
	private Font titleFont;
	private Font bodyFont;
	private double delta;
	private double rs;
	ArrayList<FadeLabel> rows=new ArrayList<FadeLabel>();
	
	public NewsPanel(double rollSpeed) {
		this.setLayout(null);
		titleFont=new Font(null,Font.BOLD,24);
		bodyFont=new Font(null,Font.PLAIN,18);
		delta=-1;
		rs=rollSpeed;
	}
	
	public void addMessage(String title,String body) {
		FadeLabel t=new FadeLabel(1);
		t.setSize(this.getWidth()-2*ow-40,0);
		t.setFont(titleFont);
		t.setForeground(Color.WHITE);
		format(t,title,0);
		
		FadeLabel b=new FadeLabel(0);
		b.setFont(bodyFont);
		b.setForeground(Color.WHITE);
		b.setSize(this.getWidth()-2*ow-40,0);
		format(b,body,15);
		
		System.out.println(t.getSize().toString());
		System.out.println(t.getLocation().toString());
		System.out.println(b.getSize().toString());
		System.out.println(b.getLocation().toString());
		
		delta+=b.getHeight();
		delta+=t.getHeight();
		
		rows.add(b);
		rows.add(t);
	}
	
	private void format(JLabel l,String s,int bottom_margin) {
	    StringBuilder builder = new StringBuilder("<html>");
	    char[] chars = s.toCharArray();
	    FontMetrics fontMetrics = l.getFontMetrics(l.getFont());
	    int lineNumber=0;
	    int b=0,len=1;
	    while(true) {
	    	while(b+len<=chars.length&&fontMetrics.charsWidth(chars,b,len)<l.getWidth()) len++;
	    	if(b+len<chars.length) {
	    		builder.append(chars,b,len-1).append("<br/>");
	    		++lineNumber;
	    		b+=len-1;
	    		len=1;
	    	} else {
	    		builder.append(chars,b,len-1);
	    		++lineNumber;
	    		break;
	    	}
	    }
	    
	    builder.append("</html>");
	    l.setText(builder.toString());
	    l.setVerticalAlignment(JLabel.TOP);
	    l.setSize(l.getWidth(),fontMetrics.getHeight()*lineNumber+bottom_margin);
	}
	
	@Override
	public void paint(Graphics g) {
		if(delta>0) delta-=(Math.log10(delta)*1.5-0.5)*rs;
		if(delta<0) delta=0;
		
		super.paint(g);
		
		g.setColor(new Color(100,100,100));
		g.setFont(tfont);
		g.drawString("News",ow+18,ow+38);
		g.setColor(Color.WHITE);
		g.drawString("News",ow+20,ow+40);
		g.fillRect(ow+20, ow+55,250,5);
		
		if(rows.size()==0) return;
		int x=(int) (ow+60-delta);
		ListIterator<FadeLabel> i=rows.listIterator(rows.size());
		while(x<=this.getHeight()-2*ow-120&&i.hasPrevious()) {
			FadeLabel l=i.previous();
			if(x+l.getHeight()<ow+60) {
				l.setAlpha(0);
			}
			else if(x<ow+60) {
				l.setAlpha(((float)x+l.getHeight()-ow-60)/l.getHeight());
			} else {
				l.setAlpha(1);
			}
			l.paint(g.create(ow+20,x,l.getWidth(),l.getHeight()));
			x+=l.getHeight();
		}
	}
}
