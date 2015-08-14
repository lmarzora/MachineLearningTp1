package learning;

import java.io.*;


public class Saver {

	private static String s = "./bin/pesos/";
	private static String s2 = "./pesos/";
	

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
				
				for (double d : weights) {
					System.out.print(d + " ");
				}
			  System.out.println("lei");
			  
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
