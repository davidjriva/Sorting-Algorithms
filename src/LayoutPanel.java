import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;

public class LayoutPanel extends JPanel implements ActionListener
{
	private OptionPanel optionP;
	private BarPanel barP;
	
	public LayoutPanel() {
		this.setLayout(new BorderLayout());
		optionP = new OptionPanel(this);
		this.add(optionP, BorderLayout.NORTH);
		barP = new BarPanel(this);
		this.add(barP, BorderLayout.CENTER);
		this.barP = barP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(optionP.isAutorunOn() == true)
		{
			barP.stepForward();
		}
	}
	
	public void performOption(OptionEvent e) {
		if(e == OptionEvent.ADD) {
			barP.addBar();
		}
		
		if(e == OptionEvent.RANDOMIZE) {
			barP.randomize();
		}
		
		if(e == OptionEvent.SORT)
		{
			if(optionP.getSortChoice() == "BUBBLE SORT")
			{
				barP.bubbleSort();
			}
			else if(optionP.getSortChoice()=="SELECTION SORT")
			{
				barP.selectionSort();
			}
			else if(optionP.getSortChoice()=="INSERTION SORT")
			{
				barP.insertionSort();
			}
			else if(optionP.getSortChoice()=="MERGE SORT")
			{
				barP.mergeSort();
			}
		}
		
		if(e == OptionEvent.STEP) 
		{
			barP.stepForward();
		}
	}
}
