import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Write {
	
	public static void writeRes(String name,ArrayList<Double> data){
		try {
			File file = new File(name);

			// if file doesnt exists, then create it
			if ( ! file.exists() ) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(double ss: data)
				bw.write(String.valueOf(ss)+"\n");
			bw.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
