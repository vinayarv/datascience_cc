

import java.util.ArrayList;
import java.util.List;

public class TweetPair {
	
	private List<String> text;
    private String time;
    
	public TweetPair(){
		text = new ArrayList<String>();
		time ="";
	}
	public TweetPair(List<String> text, String time) {
		super();
		this.text = text;
		this.time = time;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(String text) {
		this.text.add(text);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public void print ()
	{
		System.out.print(this.time);
		for (String t : text)
		    System.out.print(" " + t);
		System.out.println();
	}
    
}
