package com.gamesbykevin.androidframework.awt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import com.gamesbykevin.androidframework.base.Entity;
import com.gamesbykevin.androidframework.resources.Disposable;

/**
 * A class representing a button
 * @author ABRAHAM
 */
public class Button extends Entity implements Disposable
{
    //our graphic image representing the button
    private final Bitmap image;
    
    //object representing the boundary
    private Rect bounds;
    
    //the location of the button text
    private float textX, textY;
    
	//list of descriptions that can be displayed
	private List<String> descriptions;

	//our index selection
	private int index = 0;
    
	//is the button visible
	private boolean visible = true;
	
	//has the button been pressed
	private boolean pressed = false;
	
	//has the button been released
	private boolean released = false;
	
    /**
     * Create a button
     * @param image Image representing button
     */
    public Button(final Bitmap image)
    {
    	//assign the image reference
        this.image = image;
		
		//create our list of descriptions
		this.descriptions = new ArrayList<String>();
        
        //assign the default dimensions
        super.setWidth(image.getWidth());
        super.setHeight(image.getHeight());
        
        //set default bounds
        setBounds(0, 0, (int)getWidth(), (int)getHeight());
    }
    
    /**
     * Get the descriptions
     * @return The list of text descriptions for this button
     */
    public List<String> getDescriptions()
    {
    	return this.descriptions;
    }
    
    /**
     * Flag the button pressed
     * @param pressed true = yes, false = no
     */
    public void setPressed(final boolean pressed)
    {
    	this.pressed = pressed;
    }
    
    /**
     * Is the button pressed?
     * @return true = yes, false = no
     */
    public boolean isPressed()
    {
    	return this.pressed;
    }
    
    /**
     * Flag the button released
     * @param released true = yes, false = no
     */
    public void setReleased(final boolean released)
    {
    	this.released = released;
    }
    
    /**
     * Has the button been released?
     * @return true = yes, false = no
     */
    public boolean isReleased()
    {
    	return this.released;
    }
    
    /**
     * Flag the visibility of the button
     * @param visible true = button will be rendered, false otherwise
     */
    public void setVisible(final boolean visible)
    {
    	this.visible = visible;
    }
    
    /**
     * Is the button visible?
     * @return true = button is visible, false otherwise
     */
    public boolean isVisible()
    {
    	return this.visible;
    }
    
	/**
	 * Add the text description to the list.<br>
	 * This will be the text the user is shown.<br>
	 * Descriptions will be displayed in the order they are added.
	 * @param description The text description
	 */
	public void addDescription(final String description)
	{
		getDescriptions().add(description);
	}
    
	/**
	 * Get the desired text description
	 * @param index The position of the desired text description
	 * @return The text we want to render on top of the button
	 */
    public String getDescription(final int index)
    {
        return getDescriptions().get(index);
    }
    
    /**
     * Get the description
     * @return The text description at the current assigned Index
     */
    public String getDescription()
    {
    	return getDescription(getIndex());
    }
    
    /**
     * Set the description at the specified index location.<br>
     * If the index location is out of bounds it will be added and ignore the index location
     * @param index The index location where we want to update the description
     * @param description The text we want to display
     */
    public void setDescription(final int index, final String description)
    {
    	//if the list is empty or the index is out of bounds
    	if (getDescriptions().isEmpty() || index < 0 || index >= getDescriptions().size())
    	{
    		addDescription(description);
    	}
    	else
    	{
    		getDescriptions().set(index, description);
    	}
    }
    
	/**
	 * Get the index selection
	 * @return the index selection of the description we want displayed
	 */
	public int getIndex()
	{
		return this.index;
	}
	
	/**
	 * Assign the index selection.<br>
	 * This will control the description displayed to the user.<br><br>
	 * If the index provided is out of bounds it will be corrected as follows<br>
	 * If the index value exceeds the number of descriptions it will be reset to 0.<br>
	 * If the index value is less than 0, it will be assigned the limit (total number of descriptions).<br>
	 * If the descriptions list is empty 0 will always be assigned.
	 * @param index The position of the desired description to be displayed to the user
	 */
	public void setIndex(final int index)
	{
		//assign index
		this.index = index;
		
		//if not empty make sure we are inbounds
		if (!getDescriptions().isEmpty())
		{
			//make sure the index stays in bounds
			if (getIndex() < 0)
				setIndex(getDescriptions().size() - 1);
			if (getIndex() > getDescriptions().size() - 1)
				setIndex(0);
		}
		else
		{
			//default 0
			this.index = 0;
		}
	}
    
    /**
     * Position the text in the center of the button.<br>
     * The position will depend on the (x,y) and (width, height)
     * @param paint Object containing desired font metrics
     */
    public void positionText(final Paint paint)
    {
        //create temporary rectangle object
        Rect tmp = new Rect();
        
        //populate the rectangle dimensions based on the text and font metrics of our paint object
        paint.getTextBounds(getDescription(), 0, getDescription().length(), tmp);
        
        //position the x-coordinate in the middle
        setTextX((float)(getX() + (getWidth() / 2) - (tmp.width() / 2)));
        
        //position the y-coordinate in the middle
        setTextY((float)(getY() + (getHeight() / 2) + (tmp.height() / 2)));
    }
    
    /**
     * Assign the text coordinate
     * @param textX The desired x-coordinate where we want the text to display
     */
    public void setTextX(final float textX)
    {
        this.textX = textX;
    }
    
    /**
     * Assign the text coordinate
     * @param textY The desired y-coordinate where we want the text to display
     */
    public void setTextY(final float textY)
    {
        this.textY = textY;
    }
    
    /**
     * Get the text coordinate
     * @return The desired x-coordinate where we assigned the text to display
     */
    public float getTextX()
    {
        return this.textX;
    }
    
    /**
     * Get the text coordinate
     * @return The desired y-coordinate where we assigned the text to display
     */
    public float getTextY()
    {
        return this.textY;
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        this.bounds = null;
        
		if (descriptions != null)
		{
			descriptions.clear();
			descriptions = null;
		}
    }
    
    /**
     * Get the image
     * @return The image assigned to this button
     */
    public Bitmap getImage()
    {
        return this.image;
    }
    
    /**
     * Get the bounds
     * @return The rectangle containing the boundary we will check for collision
     */
    public Rect getBounds()
    {
        return this.bounds;
    }
    
    /**
     * Update the bounds.<br>
     * The boundary will be set according to the current button location (x, y) and current dimensions (width, height).
     */
    public void updateBounds()
    {
        setBounds((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
    }
    
    /**
     * Set the bounds of this button.<br>
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     */
    public final void setBounds(final int x, final int y, final int w, final int h)
    {
        if (getBounds() == null)
            this.bounds = new Rect();

        //assign the bounds values
        getBounds().set(x, y, x + w, y + h);
    }
    
    /**
     * Is the provided coordinate inside the bounds of this button?
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if inside the boundary, false otherwise
     */
    public boolean contains(final double x, final double y)
    {
        return getBounds().contains((int)x, (int)y);
    }
    
    /**
     * Render the button image with the assigned text.<br>
     * If the button is not visible, it will not be rendered
     * @param canvas Object to write pixel data
     * @param paint Paint object containing font metrics
     * @throws Exception 
     */
    public void render(final Canvas canvas, final Paint paint) throws Exception
    {
    	//if not visible we will not render
    	if (!isVisible())
    		return;
    	
        //render the button
        this.render(canvas);
        
        //now draw text on top of button
        canvas.drawText(getDescription(), getTextX(), getTextY(), paint);
    }
    
    /**
     * Render just the button image itself.<br>
     * If the button is not visible, it will not be rendered
     * @param canvas Object to write pixel data
     * @throws Exception 
     */
    @Override
    public void render(final Canvas canvas) throws Exception
    {
    	//if not visible we will not render
    	if (!isVisible())
    		return;
    	
        super.render(canvas, getImage());
    }
}