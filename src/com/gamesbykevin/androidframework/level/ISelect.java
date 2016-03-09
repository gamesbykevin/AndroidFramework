package com.gamesbykevin.androidframework.level;

import com.gamesbykevin.androidframework.awt.Button;
import com.gamesbykevin.androidframework.resources.Disposable;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface ISelect extends Disposable
{
	public void setCheck(final int checkX, final int checkY);
	
	public void validate() throws Exception;
	
	public void reset() throws Exception;
	
	public void update() throws Exception;
	
	public void render(final Canvas canvas, final Paint paint) throws Exception;
	
    public void setButtonNext(final Button button);
    
    public void setButtonPrevious(final Button button);

    public void setButtonSolved(final Button button);
    
    public void setButtonOpen(final Button button);
    
    public void setButtonLocked(final Button button);
    
    public void setDescription(final String description, final int descriptionX, final int descriptionY);
    
    public String getDescription();
        
    public void setTotal(final int total);
    
    public void setCompleted(final int index, final boolean status);
    
    public void setLocked(final int index, final boolean status);
    
    public boolean isLocked(final int index);
    
    public int getTotal();
    
    public void setStartX(final int startX);
    
    public void setStartY(final int startY);
    
    public int getStartX();
    
    public int getStartY();
    
    public void setCols(final int cols);
    
    public int getCols();
    
    public void setRows(final int rows);
    
    public int getRows();
    
    public void setDimension(final int dimension);
    
    public int getDimension();
    
    public int getPadding();
    
    public void setPadding(final int padding);
    
    public int getPageIndex();
    
    public void setPageIndex(final int pageIndex);
    
    public void setLevelIndex(final int levelIndex);
    
    public int getLevelIndex();
    
	/**
	 * Flag selection
	 * @param selected true if we selected a level, false otherwise
	 */
	public void setSelection(final boolean selected);
	
	/**
	 * Do we have a selection?
	 * @return true if we selected a level, false otherwise
	 */
	public boolean hasSelection();
}
