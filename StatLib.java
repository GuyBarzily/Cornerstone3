package test;



public class StatLib {

	// simple average
	public static float avg(float[] x){
		int len = x.length;
		float avg = 0;
		for(int i =0; i<len; i++){
			avg+=x[i];
		}
		return avg/len;
	}
	// returns the variance of X and Y
	public static float var(float[] x){
		float avg = avg(x);
		float sum = 0;
		for (int i = 0; i < x.length; i++) {
			sum+= Math.pow((x[i] - avg),2);
		}
		float var =(sum/x.length);
		return var;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){

		float avgX = avg(x);
		float avgY = avg(y);
		float sum =0;
		for (int i = 0; i < x.length;i++){
			sum+= (x[i] - avgX) * (y[i] - avgY);
		}
		float cov = sum/x.length;
		return cov;
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		double devX = Math.sqrt(var(x));
		double devY = Math.sqrt(var(y));
		float cov = cov(x,y);
		double pearson = (cov/(devX*devY));
		return (float)pearson ;

	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		int len = points.length;
		float sumX[] = new float[len];
		float sumY[] = new float[len];
		for (int i = 0; i < len; i++) {
			sumX[i] = points[i].x;
			sumY[i] = points[i].y;
		}
		float xAvg = avg(sumX);
		float yAvg = avg(sumY);
		float sumXY =0;
		float sumLowerX =0;
		for (int i=0; i<len;i++){
			sumXY+=(sumX[i] - xAvg)*(sumY[i] - yAvg);
			sumLowerX+= Math.pow((sumX[i] - xAvg),2);
		}
		float m = sumXY/sumLowerX;
		float b = yAvg - (m*xAvg);
		Line l = new Line(m,b);
		return l;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){

		Line l =linear_reg(points);
		return dev(p,l);
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){

		return Math.abs(l.f((p.x)) - p.y);


	}

}
