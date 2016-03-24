

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class tweets_cleaned {
	
	public static void writeOutputToFile(File file, String content)
	{	
		try {
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
     * Java Method to read JSON From File
     */
    public static void readJson(String file, File oFile) {
    	
		JSONParser parser = new JSONParser();
		JSONObject jObj = new JSONObject();
    	BufferedReader br = null;
    	
    	String content= "";
    	int count=0;
    	int output =0;
    	
    	Pattern pat = Pattern.compile("[^\\x20-\\x7f]+");
 		Matcher mat = null;
 		
		try {
			String line;
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				try{
				jObj = (JSONObject)parser.parse(line);
				}catch(ParseException p){
				 p.getStackTrace();
				}
				String time = (String) jObj.get("created_at");
				String text = (String) jObj.get("text");
				//Pattern pat = Pattern.compile("[^\\x20-\\x7f]+");
				if(text !=null)
					mat = pat.matcher(text);
				int ucount=0;
				while(mat.find()){
					ucount++;   
				}
				if(ucount>0)
				{
					count++;
					output = count;
				}
				if(text !=null)
				{
					text = text.replaceAll("\\P{Print}", "");
					text = text.replaceAll("\\s+", " ");	
				}
				
				content += text + " (timestamp: " +time + ")" + "\n";
		
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		content += "\n" + count + " tweets contained unicode.";
		writeOutputToFile(oFile, content);
		
    }						
	
    public static void main(String[] args) {
		System.out.println("Tweets cleaned programs");
		
		String current = System.getProperty("user.dir");
		System.out.println(current);
		
		for (int i = 0; i < args.length; i++)
		{
            System.out.println(args[i]);
    	}
		
		String filePath = current + "\\" + args[0];		
		String outputFile = current + "\\" + args[1];
		
		File oFile = new File(outputFile);
		// if file doesnt exists, then create it
		if (!oFile.exists()) {
			try {
				oFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		readJson(filePath, oFile);
	}

}
