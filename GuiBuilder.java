import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * This is a GUI builder object that creates a gui calculator
 * by the provided by the passes token ArrayList.
 *  
 * @author dakinwerneburg
 *
 */

public class GuiBuilder  implements ActionListener{
	private ArrayList<String> tokens;
	private int index = 0;
	private JFrame window;
	private JButton button;
	private ButtonGroup group;
	private JRadioButton radio;
	private JLabel label;
	private JPanel panel;
	private JTextField display;
	private double result;;
	private boolean add = false;
	private boolean sub = false;
	private boolean mult = false;
	private boolean div = false;
	private String concatenatedNum ="";
	
	
	/**
	 * Constructor 
	 * @param tokens ArrayList of tokens from LexicalAnalzer
	 */
	public GuiBuilder(ArrayList<String> tokens){
		this.tokens = tokens;
		gui();
	}
	

	public void gui(){
		window = new JFrame();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		int width = 0;
		int height = 0;	


		//Window terminal validation//
		if (tokens.get(index).equals("Window")){						
			index++;

		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: Window" );
			System.exit(0);
		}

		//STRING terminal validation//
		if (tokens.get(index).startsWith("\"") && tokens.get(index).endsWith("\"")){            
			window.setTitle(tokens.get(index).replace("\"", ""));
			index++;	
		}	
		else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a STRING surrounded with \"\" ");
			System.exit(0);
		}

		// '(' terminal validation//
		if(tokens.get(index).equals("(")){
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: '(" );
			System.exit(0);
		}

		// NUMBER terminal validation//
		if(tokens.get(index).matches("\\d+")){
			width = Integer.parseInt(tokens.get(index));      
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a NUMBER" );
			System.exit(0);
		}

		//',' terminal validation//
		if(tokens.get(index).equals(",")){
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ','" );
			System.exit(0);
		}

		// NUMBER terminal validation//
		if(tokens.get(index).matches("\\d+")){
			height = Integer.parseInt(tokens.get(index));
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a NUMBER" );
			System.exit(0);
		}

		// ')' terminal validation//
		if(tokens.get(index).equals(")")){
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ')'" );
			System.exit(0);
		}


		window.setSize(width, height);	    /*sets Frame size from two NUMBER terminals*/
		layout(window);		                /*Calls Non-Terminal layout*/
		widgets(window);                    /*Calls Non-Terminal widgets*/


		// End terminal validation//
		if(tokens.get(index).equals("End"))
			index++;
		else{ 
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: End" );
			System.exit(0);
		}

		// '.' terminal validation//
		if(tokens.get(index).equals("."))
			index++;
		else {
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: '.'" );
			System.exit(0);
		}
	}

	/**
	 * Method defines the following procedure:
	 * layout ::=   Layout layout_type ':'
	 * 
	 * @param c  Container that the layout will be applied to
	 */
	public void layout(Container c){

		//Layout terminal validation//
		if(tokens.get(index).equals("Layout")){
			index ++;
			layout_type(c);
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: Layout" );
			System.exit(0);
		}

		//':' terminal validation//
		if(tokens.get(index).equals(":")){
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ':'" );
			System.exit(0);
		}
	}


	/**
	 * Method defines the following procedure:
	 * layout_type ::=  Flow | Grid '(' NUMBER ',' NUMBER [',' NUMBER ',' NUMBER] ')' 
	 * 
	 * @param c  Container that the layout will be applied to
	 */
	public void layout_type(Container c){

		//Flow or Grid terminal validation//
		if(tokens.get(index).equals("Flow")){
			c.setLayout(new FlowLayout());
			index++;				
		}else if (tokens.get(index).equals("Grid")){
			int start = index;
			int rows = 0;
			int cols = 0;
			int hgap = 0;
			int vgap = 0;
			String pattern1 = tokens.get(start) + tokens.get(start+1) +  tokens.get(start+2) + tokens.get(start+3) + " " + 
					tokens.get(start+4) + tokens.get(start+5) + " " + 
					tokens.get(start+6) + tokens.get(start+7) + " " +
					tokens.get(start+8) + tokens.get(start+9);						
			start = index;
			String pattern2 = tokens.get(start) + tokens.get(start+1) + tokens.get(start+2) + tokens.get(start+3) + " " +
					tokens.get(start+4) + tokens.get(start+5);
			if(pattern1.matches("Grid\\(\\d+, \\d+, \\d+, \\d+\\)")){					
				index += 2;
				rows =  Integer.parseInt(tokens.get(index));
				index += 2;
				cols = Integer.parseInt(tokens.get(index));
				index += 2;
				hgap = Integer.parseInt(tokens.get(index));
				index += 2;
				vgap = Integer.parseInt(tokens.get(index));
				index += 2;
				c.setLayout((new GridLayout(rows, cols, hgap, vgap)));
			}else if (pattern2.matches("Grid\\(\\d+, \\d+\\)")){
				index += 2;
				rows =  Integer.parseInt(tokens.get(index));
				index += 2;
				cols = Integer.parseInt(tokens.get(index));
				index += 2;
				c.setLayout(new GridLayout(rows, cols));
			}else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: (NUMBER,NUMBER) or (NUMBER,NUMBER,NUMBER,NUMBER)" );
				System.exit(0);
			}

		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: Flow or Grid" );
			System.exit(0);
		}
	}



	/**
	 * Method defines the following the following procedure through recursion:
	 * widgets ::= widget widgets | widget
	 * 
	 * @param c  Container that the components will be added to
	 */
	public void widgets(Container c){			
		widget(c);
		if(!tokens.get(index).equals("End")){			
			widgets(c);	
		}

	}


	/**
	 * Method that defines the following procedure:
	 * widget ::=  Button STRING ';' | 
	 * 			   Group radio_buttons End ';' |
	 *             Label STRING ';' |
	 *             Panel layout widgets End ';' |
	 *             Textfield NUMBER ';'
	 *             
	 * @param c Container that the components will be added to
	 */
	public void widget(Container c){
		switch(tokens.get(index)){
		case "Button":
			button = new JButton();
			c.add(button);
			button.addActionListener(this);
			index ++;

			//STRING terminal validation//
			if (tokens.get(index).startsWith("\"") && tokens.get(index).endsWith("\"")){
				String buttonText = tokens.get(index).replace("\"", "");
				button.setText(buttonText);
				index++;	
			}	
			else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a STRING surrounded with \"\" ");
				System.exit(0);
			}

			//';' terminal validation
			if(tokens.get(index).equals(";")){
				index++;
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'");
				System.exit(0);
			}
			break;
		case "Group":
			group = new ButtonGroup();
			index++;
			radioButtons(c, group);
			//End; terminal validation
			if(tokens.get(index).equals("End")){
				index++;
				if(tokens.get(index).equals(";")){
					index++;
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
							"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'" );
					System.exit(0);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: End" );
				System.exit(0);
			}
			break;

		case "Label":
			label = new JLabel();
			c.add(label);
			index++;

			//STRING terminal validation
			if (tokens.get(index).startsWith("\"") && tokens.get(index).endsWith("\"")){
				String labelText = tokens.get(index).replace("\"", "");
				label.setText(labelText);
				index++;	
			}	
			else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a STRING surrounded with \"\" ");
				System.exit(0);
			}

			//';' terminal validation
			if(tokens.get(index).equals(";")){
				index++;
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'");
				System.exit(0);
			}
			break;
		case "Panel":
			panel = new JPanel();
			index++;
			c.add(panel);
			layout(panel);
			widgets(panel);

			//End; terminal validation
			if(tokens.get(index).equals("End")){
				index++;
				if(tokens.get(index).equals(";")){
					index++;
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
							"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'" );
					System.exit(0);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: End" );
				System.exit(0);
			}
			break;
		case "Textfield":
			display = new JTextField();
			display.setEditable(false);
			display.setHorizontalAlignment(JTextField.RIGHT);
			index ++;
			c.add(display);

			//NUMBER terminal validation
			if (tokens.get(index).matches("\\d+")){
				int cols = Integer.parseInt(tokens.get(index));
				display.setColumns(cols);
				index++;	
			}else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a NUMBER");
				System.exit(0);
			}

			//';' terminal validation
			if(tokens.get(index).equals(";")){
				index++;
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
						"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'");
				System.exit(0);
			}
			break;
		}


	}

	/**
	 * Method that defines the following procedure:
	 * radio_buttons ::=  radio_button radio_buttons | radio_button
	 * 	
	 * @param group  Button group that radio buttons will be group to 
	 */
	public  void radioButtons(Container c, ButtonGroup group){
		radioButton(c, group);
		if(!tokens.get(index).equals("End")){			
			radioButtons(c, group);
			
		}
	}


	/**
	 * Method that defines the following procedure:
	 * radio_button ::= Radio STRING ';'
	 * 
	 * @param group  Button group that radio buttons will be group to 
	 */
	public void radioButton(Container c, ButtonGroup group){

		//Radio terminal validation
		if(tokens.get(index).equals("Radio")){
			radio = new JRadioButton();			
			group.add(radio);
			c.add(radio);
			index++;
		}else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: Radio");
			System.exit(0);
		}

		//STRING terminal validation
		if (tokens.get(index).startsWith("\"") && tokens.get(index).endsWith("\"")){
			radio.setText(tokens.get(index));
			index++;	
		}	
		else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: a STRING surrounded with \"\" ");
			System.exit(0);
		}

		//';' terminal validation
		if(tokens.get(index).equals(";")){
			index++;
		}
		else{
			JOptionPane.showMessageDialog(null, "Error! Located at Token: " + (index+1) + 
					"\n\nInvalid Token: " + tokens.get(index) + "\nValid Token: ';'");
			System.exit(0);
		}
	}
	

	/**
	 * This method implements the ActionListener interface.  If the button
	 * is a number, it will concatenate until the selection is an operator.  
	 * Then it will use a switch statement for each operator.  A boolean is 
	 * used to keep track of the status of what operator is selected.  
	 * Once the "=" sign is used it will perform the calculation. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
			String selection = e.getActionCommand();
			if(selection.matches("[0-9]")){
				concatenatedNum += selection;
				display.setText(concatenatedNum);
			}else{
				switch (selection){
				case "+":
					result = Double.parseDouble(display.getText());
					add  = true;
					sub  = false;
					mult = false;
					div  = false;
					concatenatedNum ="";
					break;
				case "-":
					result = Double.parseDouble(display.getText());
					add  = false;
					sub  = true;
					mult = false;
					div  = false;
					concatenatedNum ="";
					break;
				case "*":
					result = Double.parseDouble(display.getText());
					add  = false;
					sub  = false;
					mult = true;
					div  = false;
					concatenatedNum ="";
					break;
				case "/":
					result = Double.parseDouble(display.getText());
					add  = false;
					sub  = false;
					mult = false;
					div  = true;
					concatenatedNum ="";
					break;
				case "=":
					if(add == true){
						result += Double.parseDouble(display.getText());
						if(result == Math.floor(result)){
							display.setText(String.valueOf(((int)Math.floor(result))));
							break;
						}else{
							display.setText(String.valueOf(result));
							break;
						}
					}
					if(sub == true){
						result -= Double.parseDouble(display.getText());
						if(result == Math.floor(result)){
							display.setText(String.valueOf(((int)Math.floor(result))));
							break;
						}else{
							display.setText(String.valueOf(result));
							break;
						}
					}
					if(mult == true){
						result *= Double.parseDouble(display.getText());						
						if(result == Math.floor(result)){
							display.setText(String.valueOf(((int)Math.floor(result))));
							break;
						}else{
							display.setText(String.valueOf(result));
							break;
						}
					}
					if(div == true){						
							result /= Double.parseDouble(display.getText());
							if(Double.isInfinite(result)){
								display.setText("Cannot divide by zero");
								result = 0;
								break;
							}else{
								if(result == Math.floor(result)){
									display.setText(String.valueOf(((int)Math.floor(result))));
									break;
								}else{
									display.setText(String.valueOf(result));
									break;
								}								
							}
					}
				case "C":
					result = 0;
					concatenatedNum ="";
					display.setText(concatenatedNum);
					break;
				}
			}
	}
	
}
