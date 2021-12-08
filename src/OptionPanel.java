import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class OptionPanel extends JPanel implements ActionListener 
{
	//fields
	private JButton[] buttons; //buttons to display in options area, allow user interaction
	private LayoutPanel layoutP; //reference to the containing layout
	private JCheckBox autorunToggle;
	private JComboBox<String> sortAlgorithms;
	
	public OptionPanel (LayoutPanel lp)
	{
		this.layoutP = lp;
		buttons = new JButton[4];
		buttons[0] = new JButton("ADD");
		buttons[1] = new JButton("RANDOMIZE");
		buttons[2] = new JButton("STEP");
		buttons[3] = new JButton("SORT");
		this.autorunToggle = new JCheckBox("Autorun");
		this.add(autorunToggle);
		this.sortAlgorithms = new JComboBox<String>();
		this.sortAlgorithms.addItem("SELECTION SORT");
		this.sortAlgorithms.addItem("BUBBLE SORT");
		this.sortAlgorithms.addItem("INSERTION SORT");
		this.sortAlgorithms.addItem("MERGE SORT");
		this.add(sortAlgorithms);
		
		for (JButton button : buttons)
		{
			this.add(button); //adds button to this instance of OptionPanel
			button.addActionListener(this); //makes this panel listen for events (presses) on the button
		}
	}
	
	public String getSortChoice()
	{
		return (String) this.sortAlgorithms.getSelectedItem();
	}
	
	public boolean isAutorunOn()
	{
		if(autorunToggle.isSelected() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			JButton b = (JButton) e.getSource();
			System.out.println("Button " + b.getText() + " was pressed!");
			
			//insert statements to perform actions depending on button that was pressed.
			//
			if(b.getText().equals("ADD"))
			{
				layoutP.performOption(OptionEvent.ADD);
			}
			else if(b.getText().equals("RANDOMIZE"))
			{
				layoutP.performOption(OptionEvent.RANDOMIZE);
			}
			else if(b.getText().equals("SORT"))
			{
				layoutP.performOption(OptionEvent.SORT);
			}
			else if(b.getText().equals("STEP"))
			{
				layoutP.performOption(OptionEvent.STEP);
			}
		}
	}

}

