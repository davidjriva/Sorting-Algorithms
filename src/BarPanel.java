import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarPanel extends JPanel{
	//constant
	private static final int STEPSIZE = 50;
	
	//fields
	private LayoutPanel layoutP;
	private ArrayList<Bar> bars;
	private ArrayList<Step> steps;
	int stepCounter;
	private ArrayList<Bar> startBars;
	private ArrayList<Bar> buffer;
	
	//constructor
	public BarPanel(LayoutPanel lp)
	{
		this.layoutP = lp;
		this.bars = new ArrayList<Bar>();
		this.steps = new ArrayList<Step>();
		this.startBars = new ArrayList<Bar>();
		this.buffer = new ArrayList<Bar>();
		stepCounter = -1;
	}
	
	//methods
	public void addBar()
	{
		int barHeight = (int) (Math.random()*(this.getHeight()-STEPSIZE)+STEPSIZE); //range: [50,200]
		Bar b = new Bar(barHeight);
		bars.add(b);
		buffer.add(null);
		this.repaint();
		
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(bars.size()==0)
		{
			return;
		}
		int barWidth = Math.min(this.getWidth()/bars.size() , 300);
		// alternative: int barWidth = (this.getWidth/bars.size()<300) ? Use this.getWidth()/bars.size() : 300;
		
		int totalBarWidth = barWidth * bars.size();
		int startX = this.getWidth()/2 - totalBarWidth/2;
		
		for(int i = 0; i<bars.size(); i++)
		{
			Bar temp = bars.get(i);
			g.setColor(temp.getColor());
			int y= this.getHeight()-temp.getHeight();
			g.fillRect(getBarX(i), y, barWidth, temp.getHeight());
			startX += barWidth;
		}
		this.drawBuffer(g);
		drawStepMark(g);
	}
	
	private void swapBars(int index1, int index2)
	{
		Bar temp = bars.get(index2);
		bars.set(index2, bars.get(index1));
		bars.set(index1, temp);
	}
	
	public void randomize()
	{
		for(int i = 0; i<bars.size() ; i++)
		{
			int k = (int)(Math.random()*(bars.size()-1));
			swapBars(i, k);
		}
		this.repaint();
		clearSteps();
	}
	
	public void bubbleSort()
	{
		if(bars.size() == 0)
		{
			return;
		}
		else
		{
			clearSteps();
		}
		storeStartBars();
		for(int sortedIndex = bars.size(); sortedIndex>0; sortedIndex--)
		{

			
			for(int index = 0; index<sortedIndex-1; index++)
			{
				
				Bar bar1 = bars.get(index);
				Bar bar2 = bars.get(index+1);
				Step compareStep = new Step(index,index+1,Step.COMPARE);
				steps.add(compareStep);
				if(bar1.getHeight() > bar2.getHeight())
				{
					Step swapStep = new Step(index, index+1, Step.SWAP);
					steps.add(swapStep);
					swapBars(index, index+1);
				}
				
			}
		}
		restoreStart();
		this.repaint();
		
	}
	
	public void selectionSort()
	{
		if(bars.size() == 0)
		{
			return;
		}
		storeStartBars();
		for(int j = 0; j<bars.size();j++)
		{
			int minIndex = j;
			for(int k = j+1; k<bars.size() ; k++)
			{
				Step compareStep = new Step(minIndex,k, Step.COMPARE);
				steps.add(compareStep);
				if(bars.get(minIndex).getHeight() > bars.get(k).getHeight())
				{
					minIndex = k;
				}
			}
			Step swapStep = new Step(minIndex, j, Step.SWAP);
			steps.add(swapStep);
			swapBars(minIndex, j);
		}
		restoreStart();
		this.repaint();
	}
	
	public int getBarX(int index)
	{
		int barWidth = Math.min(this.getWidth()/bars.size() , 300);
		int totalBarWidth = barWidth * bars.size();
		int startX = this.getWidth()/2 - totalBarWidth/2;
		return (startX+barWidth*index);
	}
	
	public void drawStepMark(Graphics g)
	{
		if(stepCounter == -1 || stepCounter == steps.size())
		{
			return;
		}
		
		Step s = steps.get(stepCounter);
		int x1 = getBarX(s.getIndex1());
		int x2 = getBarX(s.getIndex2());
				
		if(s.getType()==Step.COMPARE)
		{
			g.setColor(Color.RED);
		}
		
		if(s.getType()==Step.SWAP)
		{
			g.setColor(Color.ORANGE);
		}
		
		g.fillRect(x1, (this.getHeight()-10), Math.min(this.getWidth()/bars.size() , 300) , 10);
		g.fillRect(x2, (this.getHeight()-10), Math.min(this.getWidth()/bars.size() , 300), 10);
	}
	
	public void clearSteps()
	{
		steps.clear();
		stepCounter = -1;
	}
	
	private void storeStartBars()
	{
		startBars.clear();
		for(int i = 0; i< bars.size(); i++)
		{
			startBars.add(bars.get(i));
		}
	}
	
	public void restoreStart()
	{
		bars.clear();
		for(int i = 0; i< startBars.size(); i++)
		{
			bars.add(startBars.get(i));
		}
		stepCounter = 0;
	}
	
	public void stepForward()
	{
		if(stepCounter == -1 || stepCounter == steps.size())
		{
			return;
		}
		Step s = steps.get(stepCounter);
		if(s.getType()==Step.SWAP)
		{
			swapBars(s.getIndex1(), s.getIndex2());
		}
		if(s.getType()==Step.TOBUFFER)
		{
			buffer.set(s.getIndex1(),bars.get(s.getIndex2()));
		}
		if(s.getType()==Step.TOBARS)
		{
			bars.set(s.getIndex1(), buffer.get(s.getIndex2()));
			buffer.set(s.getIndex2(), null);
		}
		stepCounter++;
		this.repaint();
	}
	
	public void insertionSort() 
	{
		if(bars.size()==0)
		{
			return;
		}
		
		storeStartBars();
		for(int i = 1; i<bars.size();i++) 
		{
			Step compareStep = new Step(i,i-1, Step.COMPARE);
			steps.add(compareStep);
			for(int j = i; j>0; j--)
			{
				Bar secondBar = bars.get(j);
				Bar firstBar = bars.get(j-1);
				if(firstBar.getHeight()>secondBar.getHeight()) {
					Step swapStep = new Step(j, j-1, Step.SWAP);
					steps.add(swapStep);
					swapBars(j, j-1);
				}
			}
		}
		restoreStart();
		this.repaint();
	}
	
	public void mergeSortRecursion(int lowIndex, int highIndex)
	{	
		if(lowIndex >= highIndex)
		{
			return;
		}
		if(lowIndex + 1 == highIndex)
		{
			Step compareStep = new Step(lowIndex, highIndex, Step.COMPARE);
			steps.add(compareStep);
			if(bars.get(lowIndex).getHeight()>bars.get(highIndex).getHeight())
			{
				Step swapStep = new Step(highIndex, lowIndex ,Step.SWAP);
				steps.add(swapStep);
				swapBars(lowIndex, highIndex);
				return;
			}
		}
		int midIndex = (lowIndex + highIndex)/2;
		mergeSortRecursion(lowIndex,midIndex);
		mergeSortRecursion(midIndex+1, highIndex);
		int frontIndex = lowIndex;
		int backIndex = midIndex+1;
		boolean frontDone = false;
		boolean backDone = false;
		for(int bufferIndex = lowIndex; bufferIndex<=highIndex;bufferIndex++)
		{
			if(frontDone == true)
			{
				bufferSetAndSave(bufferIndex, backIndex);
				backIndex++;
				continue;
			}
			if(backDone == true)
			{
				bufferSetAndSave(bufferIndex, frontIndex);
				frontIndex++;
				continue;
			}
			
			Bar frontBar = bars.get(frontIndex);
			Bar backBar = bars.get(backIndex);
			
			if(frontBar.getHeight()<backBar.getHeight()) 
			{
				bufferSetAndSave(bufferIndex, frontIndex);
				frontIndex++;
				if(frontIndex > midIndex)
				{
					frontDone = true;
				}
			}else{
				bufferSetAndSave(bufferIndex, backIndex);
				backIndex++;
				if(backIndex > highIndex) {
					backDone = true;
				}
			}
			
		}
		for(int i = lowIndex; i <= highIndex;i++)
		{
			bars.set(i, buffer.get(i));
			buffer.set(i, null);
			Step bufferStepNumero2 = new Step(i,i,Step.TOBARS);
			steps.add(bufferStepNumero2);
		}
	}
	
	public void mergeSort()
	{
		storeStartBars();
		mergeSortRecursion(0, bars.size()-1);
		restoreStart();
		this.repaint();
	}
	
	public void bufferSetAndSave(int bufferIndex, int barIndex)
	{
		Step bufferStep = new Step(bufferIndex, barIndex, Step.TOBUFFER);
		steps.add(bufferStep);
		buffer.set(bufferIndex, bars.get(barIndex));
	}
	
	private void drawBuffer(Graphics g)
	{
		for(int i = 0; i< buffer.size(); i++)
		{
			Bar bufferBar = buffer.get(i);
			if(bufferBar == null)
			{
				continue;
			}
			g.setColor(Color.BLACK);
			int x = getBarX(i);
			int width = Math.min(this.getWidth()/bars.size() , 300);
			int height = bufferBar.getHeight();
			int y = this.getHeight()-bufferBar.getHeight();
			g.drawRect(x,y,width, height);
		}
	}
}

	