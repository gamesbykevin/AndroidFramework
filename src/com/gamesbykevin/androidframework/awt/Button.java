package com.gamesbykevin.androidframework.awt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    
    @Override
    public void render(final Canvas canvas) throws Exception
    {
        super.render(canvas, getImage());
    }
}