package tk.circuitcoder.lab.ScreenFace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ScreenFace {
	private class DragListener extends MouseAdapter implements MouseMotionListener {
		public DragListener(JFrame f,ScreenFace p) {
			this.f=f;
			parent=p;
		}
		
		JFrame f;
		ScreenFace parent;
		Point ori;
		Point oriWin;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()!=MouseEvent.BUTTON3) return;
			parent.toggleOpt();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton()!=MouseEvent.BUTTON1) return;
			ori=e.getLocationOnScreen();
			oriWin=f.getLocation();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()!=MouseEvent.BUTTON1) return;
			ori=null;
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(ori==null) return;
			Point c=e.getLocationOnScreen();
			int newX=(int) (c.getX()-ori.getX()+oriWin.getX());
			int newY=(int) (c.getY()-ori.getY()+oriWin.getY());
			f.setLocation(newX,newY);
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}
	}
	
	private JFrame tf;
	private JFrame nf;
	private JFrame opt;
	boolean keep=true;
	
	public static void main(String[] args) {
		ScreenFace sf=new ScreenFace();
		sf.start();
	}
	
	public void start() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		Panel.setOW(10);
		Panel.setRC(20);
		opt=new JFrame("Screen Face");
		opt.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JButton stopBtn=new JButton("Exit");
		stopBtn.setSize(10,10);
		stopBtn.setPreferredSize(new Dimension(40,30));
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		opt.add(stopBtn);
		
		tf=new JFrame("Time Frame");
		tf.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(tf,false);
		
		TimePanel tp=new TimePanel();
		tp.setForeground(new Color(1,1,1,0.2F));
		tp.setBackground(new Color(0,0,0,0.6F));
		tp.setBounds(0,0,400,200);
		tf.add(tp);
		
		DragListener dl=new DragListener(tf,this);
		tf.addMouseMotionListener(dl);
		tf.addMouseListener(dl);
		tf.setSize(400,200);
		tf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		tf.setAlwaysOnTop(true);
		tf.setVisible(true);
		
		nf=new JFrame("News Panel");
		nf.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(nf,false);
		
		NewsPanel np=new NewsPanel(1.5);
		np.setForeground(new Color(1,1,1,0.2F));
		np.setBackground(new Color(0,0,0,0.6F));
		np.setBounds(0,500,400,600);
		nf.add(np);
		
		DragListener ndl=new DragListener(nf,this);
		nf.addMouseMotionListener(ndl);
		nf.addMouseListener(ndl);
		nf.setSize(400,600);
		nf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		nf.setAlwaysOnTop(true);
		nf.setVisible(true);
		
		Thread looper=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(ScreenFace.this.keep) {
						tf.repaint();
						nf.repaint();
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(-2);
				}
			}
		});
		looper.start();
		
		Scanner sc=new Scanner(System.in);
		while(true) {
			String title=sc.next();
			String body=sc.next();
			np.addMessage(title.replaceAll("-"," "),body.replaceAll("-"," "));
		}
	}
	public void toggleOpt() {
		opt.pack();
		opt.setVisible(!opt.isVisible());
	}
}
