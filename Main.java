/**
 * Project 2
 * CMSC 330
 * Secton 6830
 * FileName Main.java
 *
 * Creates two objects and passes one into the other
 *
 * @author Dakin T. Werneburg
 */

import java.io.IOException;


public class Main {


	public static void main(String[] args) throws IOException {
		LexicalAnalyzer lexical = new LexicalAnalyzer("Input.txt");
		GuiBuilder g = new GuiBuilder(lexical.getTokens());
	}

}
