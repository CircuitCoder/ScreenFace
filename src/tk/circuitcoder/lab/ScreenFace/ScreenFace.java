package tk.circuitcoder.lab.ScreenFace;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class ScreenFace {
	private class DragListener extends MouseAdapter implements MouseMotionListener {
		Point ori;
		Point oriWin;
		
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
	
	private JFrame f;
	boolean keep=true;
	
	public static void main(String[] args) {
		ScreenFace sf=new ScreenFace();
		sf.start();
	}
	
	public void start() {
		f=new JFrame("Screen Face");
		f.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(f,false);
		
		
		TimePanel tp=new TimePanel();
		tp.setForeground(new Color(1,1,1,0.2F));
		tp.setBackground(new Color(0,0,0,0.6F));
		tp.setBounds(0,0,400,200);
		f.add(tp);
		
		DragListener dl=new DragListener();
		f.addMouseMotionListener(dl);
		f.addMouseListener(dl);
		f.setSize(400,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setAlwaysOnTop(true);
		f.setVisible(true);
		
		Thread looper=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(ScreenFace.this.keep) {
						f.repaint();
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(-2);
				}
			}
		});
		try {
			looper.start();
			looper.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
