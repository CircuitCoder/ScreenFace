package tk.circuitcoder.lab.ScreenFace;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JLabel;

public class NewsPanel extends Panel{
	private static final Font tfont = new Font(null,Font.BOLD,36);
	private static FontMetrics tfontM;

	private static final long serialVersionUID = 1L;
	
	private Font titleFont;
	private Font bodyFont;
	private double delta;
	private double rs;
	ArrayList<JLabel> rows=new ArrayList<JLabel>();
	
	public NewsPanel(double rollSpeed) {
		this.setLayout(null);
		titleFont=new Font(null,Font.BOLD,24);
		bodyFont=new Font(null,Font.PLAIN,12);
		delta=-1;
		rs=rollSpeed;
		if(tfontM==null) tfontM=this.getFontMetrics(tfont);
	}
	
	public void addMessage(String title,String body) {
		JLabel t=new JLabel();
		t.setSize(this.getWidth()-2*ow-40,0);
		t.setFont(titleFont);
		format(t,title,5);
		
		JLabel b=new JLabel();
		b.setFont(bodyFont);
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
	    l.setSize(l.getWidth(),fontMetrics.getHeight()*lineNumber+bottom_margin);
	}
	
	@Override
	public void paint(Graphics g) {
		if(delta>0) delta-=rs;
		if(delta<0) delta=0;
		
		super.paint(g);
		
		g.setColor(new Color(100,100,100));
		g.setFont(tfont);
		g.drawString("News",ow+18,ow+38);
		g.setColor(Color.WHITE);
		g.drawString("News",ow+20,ow+40);
		
		if(rows.size()==0) return;
		int x=(int) (ow+60-delta);
		ListIterator<JLabel> i=rows.listIterator(rows.size());
		while(x<=this.getHeight()-2*ow-120&&i.hasPrevious()) {
			JLabel l=i.previous();
			if(x+l.getHeight()<ow+60) {
				l.setForeground(new Color(1,1,1,0));
				System.out.println("Hid");
			}
			else if(x<ow+60) {
				l.setForeground(new Color(1,1,1,((float)x+l.getHeight()-ow-60)/l.getHeight()));
				System.out.println("Trans");
			} else {
				l.setForeground(new Color(1,1,1,0F));
				System.out.println("Shown");
			}
			l.paint(g.create(ow+20,x,l.getWidth(),l.getHeight()));
			x+=l.getHeight();
		}
	}
}
