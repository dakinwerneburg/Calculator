import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * Project 1
 * CMSC 330
 * Section 6830
 * FileName LexicalAnalyzer.java
 * 
 * Lexical Analyzer object that returns a list of tokens 
 * from specified file.
 * 
 * @author Dakin T. Werneburg
 *
 */
public class LexicalAnalyzer {
	
	private ArrayList<String> tokenList = new ArrayList<String>();
	private Stack<Character> stack = new Stack<Character>();
	private BufferedReader bufferRdr;
	private String filename ="";
	
	/**
	 * Constructor.
	 * 
	 * @param filename    Name of file to be analyzed.
	 */
	public LexicalAnalyzer(String filename){
		this.filename = filename;
		int position = 0;
	    String spChar = "";
	    String token = "";
	    
	    /**
	     * Reads each character and puts it on a stack until it reaches a whitespace
	     * character.  If it comes across a special character, it will pop off the stack and 
	     * add the special character to the ArrayList.  Once it reaches whitespace it will 
	     * iterate through the stack and generate a String token that will be
	     * add to the ArrayList 
	     */
	   
	    try {
	    	bufferRdr = new BufferedReader(new FileReader(filename));
	    	while((position = bufferRdr.read()) != -1) {
			    char ch = (char) position ; 
			   
			    if(!Character.isWhitespace(ch) ){
			    	
			    	
			    			stack.push(ch);
			    			
  	
			    	if(ch == '(' || ch == ')' || ch == ';' || ch == ',' || ch == '.' || ch == ':' ){        /*Special Character terminals*/
			    		stack.pop();
			    		spChar = Character.toString(ch);
			    	}
			    	 
			    }         
			    if(Character.isWhitespace(ch) || spChar.length() > 0){
			    	
			    	if(stack.size() > 0){
			   
			    		for(Character c : stack){ 
			    			
			    				token += c;		
			    		}
			    		tokenList.add(token);
			    		stack.clear();
			    		token = "";
			    	}
			    	if(spChar != ""){
			    		tokenList.add(spChar);
			    		spChar = "";
			    	}
			    }
			    
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error! No file not found" );
			System.exit(0);
		}
	}
	
	
	
	/**
	 * Getter method that provide public access to the list of tokens.
	 * @return   ArrayList of tokens
	 */
	public ArrayList<String> getTokens() {
		return tokenList;
	}
		
}
	
	
	
	
