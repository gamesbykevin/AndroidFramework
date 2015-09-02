package com.gamesbykevin.androidframework.screen;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * The interface for the game screen
 * @author ABRAHAM
 */
public interface Screen 
{
    /**
     * This method implementation will allow the user to handle motion events on the screen
     * @param event Motion Event containing the performed action
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if successful
     * @throws Exception 
     */
    public boolean update(final MotionEvent event, final float x, final float y) throws Exception;
    
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
