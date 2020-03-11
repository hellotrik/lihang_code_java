package sci.lihang;

public class linmse {
	public static double[] real_func(double[] x){
		double[] y=x.clone();
		for(int i=0;i<x.length;i++){
			y[i]=Math.sin(2*Math.PI*x[i]);
		}
		return y;
	}
	public static class poly1d{
		private double[] p;
		public poly1d(double[] p){
			this.p=p.clone();
		}
		private double mul(double x,int m){
			double y=1.0d;
			for(int i=0;i<m;i++){
				y*=x;
			}
			return y;
		}
		public double run(double x){
			double y=0;
			for(int i=0;i<p.length;i++){
				y+=mul(x,p.length-i-1)*p[i];
			}
			return y;
		}
	}
	
	public static double[] fit_func(double[] p,double[] x){
		double[] y=x.clone();
		for(int i=0;i<y.length;i++){
			y[i]=new poly1d(p).run(x[i]);
		}
		return y;
		
	}
	
	public static double[] residuals_func(double[]p,
			double[]x,double[]y){
		double[] y1=fit_func(p,x);
		int n=Math.min(y.length,y1.length);
		double ret[]=new double[n];
		for(int i=0;i<n;i++){
			ret[i]=y1[i]-y[i];
		}
		return ret;		
	}
	
	public static double[] linspace(double s,double e,int num){
		if(num<2)return new double[]{s};
		if(num==2)return new double[]{s,e};
		
		double dely=(e-s)/(num-1);
		double y[]=new double[num];
		y[0]=s;
		y[num-1]=e;
		for(int i=1;i<num-1;i++){
			y[i]=y[i-1]+dely;
		}
		return y;
	}
	public static class Gussian{
		private double m_saved;
		private boolean M_save=false;
		public double run2(){
			if(M_save){
				M_save=false;
				return m_saved;
			}else{
				double x,y;
				x=2.0d*Math.PI*Math.random();
				y=Math.sqrt(-2.0d*Math.log(Math.random()));
				m_saved=y*Math.cos(x);
				M_save=true;
				return y*Math.sin(x);
			}
		}
		
		public double run(){
			if(M_save){
				M_save=false;
				return m_saved;
			}else{
				double x,y,r2;
				do{
					x=2.0d*Math.random()-1.0d;
					y=2.0d*Math.random()-1.0d;
					r2=x*x+y*y;
				}
				while(r2>1.0d || r2==0.0d);
				final double mult=Math.sqrt(-2.0d*Math.log(r2)/r2);
				m_saved=x*mult;
				M_save=true;
				return y*mult;
			}
		}
		
		
	}
	
	public static void printar(double[] x){
		String s="[";
		for(int i=0;i<x.length;i++){
			s+=x[i]+(i==x.length-1?"":i%5==4?"\n":" ");
		}
		s+="]";
		System.out.println(s);
	}
	

	
	public static void main(String[] args){
		double[] x=linmse.linspace(0,1,10);
		double[] x_p=linmse.linspace(0, 1,1000);
		double[] y_=linmse.real_func(x);
		double[] y=y_.clone();
		linmse.Gussian rd=new linmse.Gussian();
		for(int i=0;i<y.length;i++){
			y[i]+=rd.run2();
		}
		
		
	}
}
