package com.gamesbykevin.androidframework.level;

import com.gamesbykevin.androidframework.awt.Button;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * This class will represent a level select screen
 * @author GOD
 */
public class Select implements ISelect
{
    //the current level index
    private int levelIndex = 0;
	
    //the page index
    private int pageIndex = 0;
    
    //(x, y) coordinates
    private int checkX = 0, checkY = 0;
    
    //do we check the coordinates
    private boolean check = false;
    
    //has a level selection been made
    private boolean selected = false;
    
    //the description to display
    private String description = "";
    
    //the location where we start to draw our description
    private int descriptionX = 0, descriptionY = 0;
    
    //the buttons on the level select screen
    private Button open, solved, lock;
    private Button next, previous;

    //the starting location
    private int startX = 0, startY = 0;
    
    //the pixel space between each selection
    private int padding = 0;
    
    //the dimension of a single selection
    private int dimension = 0;
    
    //the number of columns and rows per page
    private int cols = 0, rows = 0;
    
    //the total number of selections
    private int total = 0;
    
    //track the completed levels
    private boolean[] completed;
    
    //track the locked levels
    private boolean[] locked;
    
    public Select()
    {
    	//default constructor
    }
    
    @Override
    public void dispose()
    {
    	if (open != null)
    	{
    		open.dispose();
    		open = null;
    	}
    	
    	if (solved != null)
    	{
    		solved.dispose();
    		solved = null;
    	}
    	
    	if (lock != null)
    	{
    		lock.dispose();
    		lock = null;
    	}
    	
    	if (next != null)
    	{
    		next.dispose();
    		next = null;
    	}
    	
    	if (previous != null)
    	{
    		previous.dispose();
    		previous = null;
    	}
    }
    
	@Override
    public void setButtonNext(final Button button)
    {
    	this.next = button;
    }
    
	@Override
    public void setButtonPrevious(final Button button)
    {
    	this.previous = button;
    }

	@Override
    public void setButtonSolved(final Button button)
    {
    	this.solved = button;
    }
    
	@Override
    public void setButtonOpen(final Button button)
    {
    	this.open = button;
    }
	
	@Override
	public void setButtonLocked(Button button) 
	{
		this.lock = button;
	}
    
	@Override
    public void setDescription(final String description, final int descriptionX, final int descriptionY)
    {
    	this.description = description;
    	this.descriptionX = descriptionX;
    	this.descriptionY = descriptionY;
    }
    
	@Override
    public String getDescription()
    {
    	return this.description;
    }
    
    private int getDescriptionX()
    {
    	return this.descriptionX;
    }
    
    private int getDescriptionY()
    {
    	return this.descriptionY;
    }
    
	@Override
    public void setTotal(final int total)
    {
    	this.total = total;
    	
    	//create list to track level completion
    	this.completed = new boolean[total];
    	
    	//create list to track locked levels
    	this.locked = new boolean[total];
    }
    
	@Override
    public void setCompleted(final int index, final boolean status)
    {
    	this.completed[index] = status;
    }
    
    private boolean isCompleted(final int index)
    {
    	return this.completed[index];
    }
    
	@Override
    public void setLocked(final int index, final boolean status)
    {
    	this.locked[index] = status;
    }
    
	@Override
	public boolean isLocked(final int index)
	{
		return this.locked[index];
	}
	
	@Override
    public int getTotal()
    {
    	return this.total;
    }
    
	@Override
    public void setStartX(final int startX)
    {
    	this.startX = startX;
    }
    
	@Override
    public void setStartY(final int startY)
    {
    	this.startY = startY;
    }
    
	@Override
    public int getStartX()
    {
    	return this.startX;
    }
    
	@Override
    public int getStartY()
    {
    	return this.startY;
    }
    
	@Override
    public void setCols(final int cols)
    {
    	this.cols = cols;
    }
    
	@Override
    public int getCols()
    {
    	return this.cols;
    }
    
	@Override
    public void setRows(final int rows)
    {
    	this.rows = rows;
    }
    
	@Override
    public int getRows()
    {
    	return this.rows;
    }
    
	@Override
	public void setDimension(final int dimension)
	{
		this.dimension = dimension;
	}
	
	@Override
    public int getDimension()
    {
    	return this.dimension;
    }
    
	@Override
    public int getPadding()
    {
    	return this.padding;
    }

	@Override
	public void setPadding(final int padding)
	{
		this.padding = padding;
	}
	
	@Override
    public int getPageIndex()
    {
    	return this.pageIndex;
    }
    
	@Override
    public void setPageIndex(final int pageIndex)
    {
    	this.pageIndex = pageIndex;
    	
    	//make sure the page index remains in bounds
    	if (getPages() > 0)
    	{
	    	if (getPageIndex() >= getPages())
	    		this.pageIndex = 0;
	    	if (getPageIndex() < 0)
	    		this.pageIndex = (getPages() - 1);
    	}
    	else
    	{
    		//if there are no pages set 0
    		this.pageIndex = 0;
    	}
    }
    
	@Override
    public void setLevelIndex(final int levelIndex)
    {
    	this.levelIndex = levelIndex;
    	
    	//make sure the level index remains in bounds
    	if (getLevelIndex() >= getTotal())
    		this.levelIndex = 0;
    	if (getLevelIndex() < 0)
    		this.levelIndex = (getTotal() - 1); 
    }
    
	@Override
    public int getLevelIndex()
    {
    	return this.levelIndex;
    }
    
    private int getLevelsPerPage()
    {
    	return (getCols() * getRows());
    }
    
	/**
	 * Flag selection
	 * @param selected true if we selected a level, false otherwise
	 */
	@Override
	public void setSelection(final boolean selected)
	{
		this.selected = selected;
	}
	
	/**
	 * Do we have a selection?
	 * @return true if we selected a level, false otherwise
	 */
	@Override
	public boolean hasSelection()
	{
		return this.selected;
	}
	
	@Override
	public void setCheck(final int checkX, final int checkY)
	{
		this.check = true;
		this.checkX = checkX;
		this.checkY = checkY;
	}
	
	@Override
	public void reset() throws Exception
	{
		//flag check false
		this.check = false;
		
		//flag selection false
		setSelection(false);
		
		//reset page index
		setPageIndex(0);
		
		//reset level index
		setLevelIndex(0);
	}
	
	private int getX(final int col)
	{
		return getStartX() + (col * getDimension()) + (col * getPadding());
	}
	
	private int getY(final int row)
	{
		return getStartY() + (row * getDimension()) + (row * getPadding());
	}
	
	private int getPages()
	{
		if (getTotal() > this.getLevelsPerPage())
		{
			if (getTotal() % this.getLevelsPerPage() != 0)
			{
				return (getTotal() / this.getLevelsPerPage()) + 1;
			}
			else
			{
				return (getTotal() / this.getLevelsPerPage());
			}
		}
		else
		{
			return (getTotal() / this.getLevelsPerPage());
		}
	}
	
	@Override
	public void validate() throws Exception
	{
		if (this.open == null)
			throw new Exception("The open button is null and must be assigned");
		if (this.solved == null)
			throw new Exception("The solved button is null and must be assigned");
		//we don't have to worry about the locked button, as it is not required
		
		
		if (this.next == null)
			throw new Exception("The next button is null and must be assigned");
		if (this.previous == null)
			throw new Exception("The previous button is null and must be assigned");
		if (getDimension() < 1)
			throw new Exception("The dimension is not set");
		if (getTotal() < 1)
			throw new Exception("You must set the total selections");
		if (getCols() < 1)
			throw new Exception("You must set the number of columns");
		if (getRows() < 1)
			throw new Exception("You must set the number of rows");
	}
	
	@Override
	public void update() throws Exception
	{
		//make sure we have the appropriate assigned
		validate();
		
		//do we need to check the coordinates
		if (check)
		{
			//flag check false
			check = false;
			
			//don't continue if we have a selection
			if (hasSelection())
				return;
			
			if (next.contains(checkX, checkY))
			{
				//change the index
				setPageIndex(getPageIndex() + 1);
			}
			else if (previous.contains(checkX, checkY))
			{
				//change the index
				setPageIndex(getPageIndex() - 1);
			}
			else
			{
				//the counted levels
				int count = 0;
				
				for (int row = 0; row < getRows(); row++)
				{
					for (int col = 0; col < getCols(); col++)
					{
						//calculate the level index
						final int levelIndex = (getPageIndex() * getLevelsPerPage()) + count;
						
						//if the level index exceeds the total, skip it
						if (levelIndex >= getTotal())
							continue;
						
						//determine the coordinates
						final int x = getX(col);
						final int y = getY(row);
						
						//if in the bounds of the level we made a selection
						if (checkX >= x && checkX <= x + getDimension() && checkY >= y && checkY <= y + getDimension())
						{
							//assign the level number index
							setLevelIndex(levelIndex);
							
							//flag a selection was found
							setSelection(true);
							
							//we are done here
							return;
						}
						
						//increase the count
						count++;
					}
				}
			}
		}
	}
	
	@Override
    public void render(final Canvas canvas, final Paint paint) throws Exception
    {
		//make sure we have the appropriate assigned
		validate();
		
		//there won't be anything to render if a selection was made
		if (hasSelection())
			return;
		
		//the level number
		int count = 1;
		
		for (int row = 0; row < getRows(); row++)
		{
			for (int col = 0; col < getCols(); col++)
			{
				//determine the coordinates
				final int x = getX(col);
				final int y = getY(row);
				
				//level number to be displayed
				final int levelNumber = (getPageIndex() * getLevelsPerPage()) + count;
				
				//if the level number exceeds the total, we will skip
				if (levelNumber > getTotal())
					continue;
				
				//our temporary button
				final Button button;
				
				if (isCompleted(levelNumber - 1))
				{
					//if flagged as completed use the solved button
					button = solved;
				}
				else
				{
					//if the level is locked and the lock image exists
					if (isLocked(levelNumber - 1) && lock != null)
					{
						button = lock;
					}
					else
					{
						//else the level is open
						button = open;
					}
				}
				
				//set the description
				button.setDescription(0, "" + levelNumber);
				
				//set button dimension
				button.setWidth(getDimension());
				button.setHeight(getDimension());
				
				//position the button
				button.setX(x);
				button.setY(y);

				//position the number in the center
				button.positionText(paint);
				
				//render the button
				button.render(canvas, paint);
				
				//increase the count
				count++;
			}
		}
		
		//make sure there is more than one page before rending the buttons
		if (getPages() > 1)
		{
			//render the pagination buttons
			next.setWidth(getDimension());
			next.setHeight(getDimension());
			next.setX(getX(getCols() - 1));
			next.setY(getY(getRows()));
			next.updateBounds();
			next.render(canvas);
			
			//render the pagination buttons
			previous.setWidth(getDimension());
			previous.setHeight(getDimension());
			previous.setX(getX(0));
			previous.setY(getY(getRows()));
			previous.updateBounds();
			previous.render(canvas);
			
			//draw page # with description
			canvas.drawText(
				getDescription() + "Page: " + (getPageIndex() + 1) + " of " + getPages(),
				getDescriptionX(), getDescriptionY(), 
				paint
			);
		}
		else
		{
			//if there is only 1 page, move "next" & "previous" buttons off the screen
			if (next.getX() >= 0 || next.getY() >= 0)
			{
				next.setX(-next.getWidth());
				next.setY(-next.getHeight());
				next.updateBounds();
			}
			
			if (previous.getX() >= 0 || previous.getY() >= 0)
			{
				previous.setX(-previous.getWidth());
				previous.setY(-previous.getHeight());
				previous.updateBounds();
			}
			
			//draw description
			canvas.drawText(getDescription(), getDescriptionX(), getDescriptionY(), paint);
		}
	}
}