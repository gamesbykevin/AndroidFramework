package com.gamesbykevin.androidframework.maze;

import android.graphics.Canvas;

import com.gamesbykevin.androidframework.resources.Disposable;
import com.gamesbykevin.androidframework.util.Progress;

import java.util.Random;

/**
 * Necessary classes each maze must implement
 * @author GOD
 */
public interface IMaze extends Disposable
{
    /**
     * Update the maze generation
     * @param random Object used to make random decisions
     * @throws Exception 
     */
    public void update(final Random random) throws Exception;
    
    /**
     * Get progress
     * @return Our object used to get the progress of the maze generation
     */
    public Progress getProgress();
    
    /**
     * Verify the location is within the maze.
     * @param col Column
     * @param row Row
     * @return true if the location is in the maze, false otherwise
     */
    public boolean hasBounds(final int col, final int row);
    
    /**
     * Provide a basic method to render the top-down maze as well as the progress
     * @param canvas Object we will write pixel information to
     */
    public void render(final Canvas canvas);
}
