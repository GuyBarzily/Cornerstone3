package test;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class TimeSeries {
	Hashtable<String, Vector<Float>> ht1 =new Hashtable<>();

	public TimeSeries(String csvFileName)  {
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFileName));
			String line = br.readLine();
			String[] row = line.split(",");
			Vector <String> ColumnName = new Vector<>();
			for(int i=0; i<row.length; i++){//create as many vectors as column in the csv file
				Vector <Float> tmp = new Vector<>();
				ColumnName.add(row[i]);
				ht1.put(row[i],tmp);
			}
			while ((line = br.readLine()) != null){//reading the csv file and adding values to each vector
				row = line.split(",");
				for(int i =0; i< row.length;i++){
					ht1.get(ColumnName.get(i)).add(Float.valueOf(row[i]));//changing the string value to a Float
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public Float getValueByIndex(String st,Integer x){
		return this.ht1.get(st).get(x);
	}
	public Float getValueByInteger(int x, int y){
		int tmp = x+65;
		char ch = (char)tmp;
		String s = new String(String.valueOf(ch));
		return this.getValueByIndex(s, y);
	}
	public float[] getFarray(int x){
		int tmp = x+65;
		char ch = (char)tmp;
		String s = new String(String.valueOf(ch));
		float[] tmpF = new float[this.ht1.get(s).size()];
		for(int i = 0; i< tmpF.length; i++){
			tmpF[i] = this.getValueByInteger(x, i);
		}
		return tmpF;
	}
	public float[] getFarray(String s){
		float[] tmpF = new float[this.ht1.get(s).size()];
		for(int i = 0; i< tmpF.length; i++){
			tmpF[i] = this.getValueByIndex(s, i);
		}
		return tmpF;
	}
	public String getColName(int x){
		int tmp = x+65;
		char ch = (char)tmp;
		String s = new String(String.valueOf(ch));
		return s;
	}




	
}
