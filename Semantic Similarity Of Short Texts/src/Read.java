import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Read {
	private String fileName; 
	private ArrayList<String> lines = new ArrayList<String>();
	
	public Read(String fileName) {
		this.fileName = fileName;
		ReadFile();
	}
	
	public void ReadFile(){
		try (BufferedReader br = new BufferedReader(new FileReader(this.fileName)))
		{
			
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine.toLowerCase());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	
	public static void writeRes(String name,ArrayList<String> data){
		try {
			File file = new File(name);

			// if file doesnt exists, then create it
			if ( ! file.exists() ) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(String ss: data)
				bw.write(ss+"\n");
			bw.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
