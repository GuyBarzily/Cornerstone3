package test;

import java.util.*;

import static test.StatLib.linear_reg;
import static test.StatLib.pearson;


public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	private static float threshold = (float)0.9;
	public List<CorrelatedFeatures> correlation = new ArrayList<>();


	@Override
	public void learnNormal(TimeSeries ts) {
		float tmpCorr,maxCorr;
		int c;

        for(int i=0; i< ts.ht1.size();i++){
			tmpCorr =0;
			maxCorr = 0;
            c = -1;
			for(int j= i+1; j<ts.ht1.size();j++){
				tmpCorr = pearson(ts.getFarray(i),ts.getFarray(j));
                if(Math.abs(tmpCorr)> maxCorr){
                    maxCorr = Math.abs(tmpCorr);
                    c = j;
                }
			}
            if ((c != -1) && (maxCorr > this.getThreshold())){
				Point[] points = this.getPointArr(ts.getFarray(i), ts.getFarray(c));
				Line l = linear_reg(points);
				float thres = this.getThresholeDis(points,l);
				CorrelatedFeatures tmp = new CorrelatedFeatures(ts.getColName(i), ts.getColName(c), maxCorr, l,(float)(thres*1.1));
				this.correlation.add(tmp);
            }
        }
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<AnomalyReport> detect = new ArrayList<>();
		for (CorrelatedFeatures c : this.correlation){
			Point[] points = this.getPointArr(ts.getFarray(c.feature1), ts.getFarray(c.feature2));
			Line l = c.lin_reg;
			float maxDis =0;
			float tmpDis =0;
			for(int i= 0; i<points.length;i++){
				tmpDis = (float) Math.abs((l.a*points[i].x - points[i].y+l.b)/(Math.sqrt(Math.pow(l.a, 2)+1.0)));
				if(tmpDis > maxDis)
					maxDis = tmpDis;
				if(maxDis > c.threshold){
					String s = c.feature1 + "-" +c.feature2;
					AnomalyReport tmp = new AnomalyReport(s,i+1);
					detect.add(tmp);
					break;
				}
			}


		}
		return detect;

	}

	public List<CorrelatedFeatures> getNormalModel(){
		return this.correlation;
	}
	public float getThreshold(){
		return threshold;
	}
	public Point[] getPointArr(float []arr1,float []arr2){
		Point [] points = new Point[arr1.length];
		for (int i=0; i< arr1.length;i++){
			points[i] = new Point(arr1[i],arr2[i]);
		}
		return points;
	}
	public float getThresholeDis(Point[] points, Line l){
		float maxDis =0;
		float tmpDis =0;
		for(int i= 0; i<points.length;i++){
			tmpDis = (float) Math.abs((l.a*points[i].x - points[i].y+l.b)/(Math.sqrt(Math.pow(l.a, 2)+1.0)));
			if(tmpDis > maxDis)
				maxDis = tmpDis;
		}
		return maxDis;
	}
}
