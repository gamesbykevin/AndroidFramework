package com.gamesbykevin.androidframework.awt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

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
    
    //is the button visible
    private boolean visible = true;
    
    //the location of the button text
    private float textX, textY;
    
    //the text to display on top of the button
    private String text = "";
    
    /**
     * Create a button
     * @param image Image representing button
     */
    public Button(final Bitmap image)
    {
        this.image = image;
        
        //assign the default dimensions
        super.setWidth(image.getWidth());
        super.setHeight(image.getHeight());
        
        //set default bounds
        setBounds(0, 0, (int)getWidth(), (int)getHeight());
    }
    
    /**
     * Assign the text
     * @param text The text we want to render on top of the button
     */
    public void setText(final String text)
    {
        this.text = text;
    }
    
    /**
     * Get the text
     * @return The text we want to render on top of the button
     */
    public String getText()
    {
        return this.text;
    }
    
    /**
     * Position the text in the center of the button.<br>
     * The position will depend on the (x,y) and (width, height)
     * @param paint Object containing desired font metrics
     */
    public void positionText(final Paint paint)
    {
        //create temp rectangle object
        Rect tmp = new Rect();
        
        //populate the rectangle dimensions based on the text and font metrics of our paint object
        paint.getTextBounds(getText(), 0, getText().length(), tmp);
        
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
    
    /**
     * Set the button as visible.<br>
     * @param visible true if we want the button to be rendered, false otherwise
     */
    public void setVisible(final boolean visible)
    {
        this.visible = visible;
    }
    
    /**
     * Is this button visible?
     * @return true = yes, false = no
     */
    public boolean isVisible()
    {
        return this.visible;
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        this.bounds = null;
    }
    
    /**
     * Get the image
     * @return The image assigned to this button
     */
    private Bitmap getImage()
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
     * Render the button image with the assigned text
     * @param canvas Object to write pixel data
     * @param paint Paint object containing font metrics
     * @throws Exception 
     */
    public void render(final Canvas canvas, final Paint paint) throws Exception
    {
        //if not visible don't render it
        if (!isVisible())
            return;
        
        //render the button
        this.render(canvas);
        
        //now draw text on top of button
        canvas.drawText(getText(), getTextX(), getTextY(), paint);
    }
    
    /**
     * Render just the button image itself
     * @param canvas Object to write pixel data
     * @throws Exception 
     */
    @Override
    public void render(final Canvas canvas) throws Exception
    {
        //if not visible don't render it
        if (!isVisible())
            return;
        
        super.render(canvas, getImage());
    }
}