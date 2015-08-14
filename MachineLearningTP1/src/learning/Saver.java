package learning;

import java.io.*;
import java.util.Iterator;
import java.util.List;


public class Saver {

	private static String s = "./bin/pesos/";
	private static String s2 = "./bin/curves/";
	

	public static void learningCurve(List<Integer> history,String fileName) {
		FileWriter writer;
		try {
			writer = new FileWriter(s2+fileName+".csv");
		
			 Iterator<Integer> it = history.iterator();
			 while(it.hasNext()) {
				writer.append(it.next().toString());
				if(it.hasNext())
					writer.append(",");
			 }	
		  
				
		    //generate whatever data you want
				
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static boolean fileExists(String name) {
		File f = new File(s+name+".ser");
		return f.exists();
	}
	public static void check() {
		File f = new File(".");
		System.out.println(f.getAbsolutePath());
	}
	
	public static double[] readWeights(String fileName , double[] weights) {
		
	
		try (
			InputStream file = new FileInputStream(s+fileName+".ser");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);			
		){
			try {

				weights = (double[]) input.readObject();
			/*	
				for (double d : weights) {
					System.out.print(d + " ");
				}
			  System.out.println("lei");
			*/  
			return weights;
			}
			finally{
				input.close();
			}
		    
		} 
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		catch(Exception ex){
			ex.printStackTrace();
	    }
	

		return null;
	}
	

	
	public static boolean saveWeights(double[] weights, String fileName) {
		try (
				OutputStream file = new FileOutputStream(s+fileName+".ser");
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);
			    ){
			
				try {
			      output.writeObject(weights);
			      return true;
				}
				    finally{
				    	output.close();
					}
			  	}  
			    catch(IOException ex){
			    	
			      ex.printStackTrace();
			    }
		return false;
	}
	
}
