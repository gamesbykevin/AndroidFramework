package com.gamesbykevin.androidframework.screen;

import com.gamesbykevin.androidframework.resources.Disposable;

import android.graphics.Canvas;

/**
 * The interface for the game screen
 * @author ABRAHAM
 */
public interface Screen extends Disposable
{
    /**
     * Logic to reset the screen
     */
    public void reset();
    
    /**
     * This method implementation will allow the user to handle motion events on the screen
     * @param action The action of the motion event (ACTION_DOWN, ACTION_UP, etc....)
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if successful
     * @throws Exception 
     */
    public boolean update(final int action, final float x, final float y) throws Exception;
    
    /**
     * Update necessary objects (if needed)
     * @throws Exception 
     */
    public void update() throws Exception;
    
    /**
     * Render the screen
     * @param canvas Object writing pixels to
     * @throws Exception 
     */
    public void render(final Canvas canvas) throws Exception;
}
