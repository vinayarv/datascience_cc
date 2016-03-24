
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DebugGraphics;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class average_degree {
	
	private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");
	
    public static void readJson(String file, List<TweetPair> textTagPair) {
    	
		JSONParser parser = new JSONParser();
		JSONObject jObj = new JSONObject();
    	BufferedReader br = null;    	
 		Matcher mat = null;
 		//String content = "";
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
		       
				if(text !=null)
				{
					text = text.replaceAll("\\P{Print}", "");
					text = text.replaceAll("\\s+", " ");
					mat = HASHTAG_PATTERN.matcher(text);
				}
//				String eachTweetHashtag = "";
				TweetPair textTag = new TweetPair();
				
		        while (mat.find()) {
//		        	eachTweetHashtag += mat.group() + " ";
		        	textTag.setText(mat.group());
		        }
		        if(!textTag.getText().isEmpty())
		        {
		        	textTag.setTime(time);
		        	textTagPair.add(textTag); 	
		        }
		        
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
			
    }	
    
    public static double buildGraph(Graph graph, TweetPair tweet)
    {
    	List<String> hashtags = new ArrayList<String>();
    	String time;
    	double avg_degree= 0;
    	
    	hashtags = tweet.getText();
    	
		int count = 0; //  Variable to hold the count of pairs
	    for (int i=0; i<hashtags.size(); i++){
	    	for(int j=0; j<hashtags.size(); j++){
	    		if (j>i){
	    			graph.addEdge(hashtags.get(i), hashtags.get(j));
	    			count++;
	    		}
	    	}  		
	    }
	    float vgraph=0;
	    float degree =0;
	    for (String v : graph.vertices()) {
			degree += graph.degree(v);
			vgraph++;
		}
	    avg_degree = degree/vgraph;
	    avg_degree = Math.round(avg_degree * 100.0) / 100.0;
	    //System.out.println("V:" +vgraph + "avg_degree" + avg_degree);
		return avg_degree;	
		
    }
    
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
		
	   List<TweetPair> tagTimepair = new ArrayList<TweetPair>();
	   readJson(filePath, tagTimepair);
//	   for(TweetPair p : tagTimepair){
//       		p.print();
//       }
	   
	   double avg_degree =0;
	   String degree = "";
	   Graph tweetGraph = new Graph();
	   for (TweetPair tweetPair : tagTimepair) {
		   if(tweetPair.getText().size()>1){
			   avg_degree = buildGraph(tweetGraph, tweetPair);
			   degree += avg_degree + "\n";
		   }
	   }
	   //System.out.println(degree);	
	   writeOutputToFile(oFile, degree);
	   
     // print out graph again by iterating over vertices and edges
     
	}

}
