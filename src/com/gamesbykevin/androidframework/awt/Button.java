package com.gamesbykevin.androidframework.awt;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.gamesbykevin.androidframework.base.Entity;

/**
 * A class representing a button
 * @author ABRAHAM
 */
public class Button extends Entity
{
    private final Bitmap image;
    
    public Button(final Bitmap image)
    {
        this.image = image;
        
        //store the default dimensions
        super.setWidth(image.getWidth());
        super.setHeight(image.getHeight());
    }
    
    public boolean hasBoundary(final double x, final double y)
    {
        //if either coordinate is not within this button, we are not in the boundary
        if (x < getX() || x > getX() + getWidth())
            return false;
        if (y < getY() || y > getY() + getHeight())
            return false;
        
        //we are in the boundary
        return true;
    }
    
    public void draw(final Canvas canvas)
    {
        canvas.drawBitmap(image, (float)getX(), (float)getY(), null);
    }
}