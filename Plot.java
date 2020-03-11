package sci.lihang;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class Plot{
	private Frame fr;
	private Canvas cv;
	public int[] size;
	private LinkedList<Inte> ar=new LinkedList<>();
	public static class Inte{
		public int[][] array;
		public boolean bo=false;
		public Color color=new Color(0,0,0);
	}
	public Plot(){this(500);}
	public Plot(int size){
		this.size=new int[]{size,size/2};
		fr=new Frame();
		fr.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		cv=new Canvas(){
			private static final long serialVersionUID = 6492308672188943643L;		
			public void paint(Graphics g){
				plot(g);
			}
		};
		cv.setPreferredSize(new Dimension(this.size[0],this.size[1]));
		
		fr.add(cv);
	}
	public void plot(Graphics g){
		if(ar.size()>0){
			for(Inte x:ar)
			{
				g.setColor(x.color);
				if(x.bo)for(int i=0;i<x.array[0].length;i++)
					g.fillRect(x.array[0][i],x.array[1][i],5,5);
				else
					g.drawPolyline(x.array[0],x.array[1],x.array[0].length);
			}
		}
	}
	public void clear(){ar.clear();}
	public void plot(double[]x,double[]y,boolean bo,Color c){
		double delyx=(x[x.length-1]-x[0])/size[1];
		Inte p=new Inte();
		p.array=new int[2][x.length];
		for(int j=0;j<x.length;j++){
			p.array[0][j]=(int)(2*x[j]/delyx);
			p.array[1][j]=(int)(size[1]-y[j]/delyx);
		}
		p.bo=bo;
		p.color=c;
		ar.add(p);
	}
	public void repaint(){cv.repaint();}
	public void show(){
		fr.pack();
		fr.setVisible(true);	
	}
	
	public static void main(String[] _) {
		Plot p=new Plot();
		linmse.Gussian gus=new linmse.Gussian();
		
		p.plot(linmse.linspace(0, 1, 10),linmse.linspace(0,1, 10),true,new Color(255,0,0));
		p.cv.repaint();
		p.show();	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.clear();
		double[] x=linmse.linspace(0, 1, 100);
		double[]y=x.clone();
		for(int i=0;i<y.length;i++)y[i]=0.5*Math.sin(20*x[i])+0.5f+0.3*Math.random();
		p.plot(x,y,true,new Color(0,255,0));
		p.repaint();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<y.length;i++)y[i]=0.5*Math.sin(20*x[i])+0.5f+0.1*gus.run();
		p.clear();
		p.plot(x,y,true,new Color(0,255,0));
		p.repaint();		
	}
}