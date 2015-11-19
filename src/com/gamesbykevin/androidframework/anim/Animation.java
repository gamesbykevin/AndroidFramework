package com.gamesbykevin.androidframework.anim;

import android.graphics.Bitmap;

/**
 * This class represents an animation
 * @author ABRAHAM
 */
public class Animation implements IAnimation
{
    /**
     * The number of nanoseconds per second
     */
    public static final long NANO_SECONDS_PER_SECOND = 1000000000;
    
    /**
     * The number of nanoseconds per millisecond
     */
    public static final long NANO_SECONDS_PER_MILLISECOND = 1000000;
    
    /**
     * The number of milliseconds per second
     */
    public static final long MILLISECONDS_PER_SECOND = 1000;
    
    /**
     * The index at which to start
     */
    private static final int FRAME_INDEX_START = 0;
    
    /**
     * The frames that make up our animation
     */
    private Bitmap[] frames;
    
    /**
     * Here we will keep track of the current frame
     */
    private int frameIndex = FRAME_INDEX_START;
    
    /**
     * Our timer to determine when to update the animation frame
     */
    private long time = 0;
    
    /**
     * The time delay between each animation frame
     */
    private long delay;
    
    //does the animation loop?
    private boolean loop = false;
    
    //has the animation finished
    private boolean finished = false;
    
    /**
     * Create a single animation the size of our provided Bitmap
     * @param spritesheet Image containing our animation
     */
    public Animation(final Bitmap spritesheet)
    {
        this(spritesheet, 0, 0, spritesheet.getWidth(), spritesheet.getHeight(), 1, 1, 1);
    }

    /**
     * Create a single animation from the provided Bitmap and specifications
     * @param spritesheet Image containing our animation
     * @param x starting x-coordinate
     * @param y starting y-coordinate
     * @param width pixel width of a single frame in the animation
     * @param height pixel height of a single frame in the animation
     */
    public Animation(final Bitmap spritesheet, final int x, final int y, final int width, final int height)
    {
        this(spritesheet, x, y, width, height, 1, 1, 1);
    }
    
    /**
     * Create an animation
     * @param spritesheet Image containing our animation
     * @param x starting x-coordinate
     * @param y starting y-coordinate
     * @param width pixel width of a single frame in the animation
     * @param height pixel height of a single frame in the animation
     * @param cols columns
     * @param rows rows
     * @param total total number of frames in our animation
     */
    public Animation(final Bitmap spritesheet, final int x, final int y, final int width, final int height, final int cols, final int rows, final int total)
    {
        //create a new array to hold our animation frames
        this.frames = new Bitmap[total];
        
        //the current index
        int index = 0;
        
        for (int col = 0; col < cols; col++)
        {
            //the starting x-coordinate
            int startX = x + (col * width);
            
            for (int row = 0; row < rows; row++)
            {
                //the starting y-coordinate
                int startY = y + (row * height);
                
                //create the image for this specific frame
                this.frames[index] = Bitmap.createBitmap(spritesheet, startX, startY, width, height);
                
                //increase the index
                index++;
                
                //if we have reached the end, exit loop
                if (index == getFrames().length)
                    break;
            }
            
            //if we have reached the end, exit loop
            if (index == getFrames().length)
                break;
        }
    }
    
    /**
     * Assign the animation to loop
     * @param loop true if the animation should loop, false otherwise 
     */
    public void setLoop(final boolean loop)
    {
        this.loop = loop;
    }
    
    /**
     * Does this animation loop when finished?
     * @return true = yes, false = no
     */
    public boolean hasLoop()
    {
        return this.loop;
    }
    
    /**
     * Get the list of images in this animations
     * @return Get all the images in this animation
     */
    private Bitmap[] getFrames()
    {
        return this.frames;
    }
    
    /**
     * Get the frame index
     * @return The current frame index of our animation
     */
    public int getFrameIndex()
    {
        return this.frameIndex;
    }
    
    /**
     * Assign the frame index.<br>
     * If the frameIndex is out of range, 0 will be assigned.
     * @param frameIndex The frame index
     */
    public void setFrameIndex(final int frameIndex)
    {
        this.frameIndex = frameIndex;
        
        //make sure the frame index remains in range
        if (getFrameIndex() < 0 || getFrameIndex() >= getFrames().length)
            setFrameIndex(FRAME_INDEX_START);
    }
    
    /**
     * Set the time delay (milliseconds)
     * @param delay The delay per each animation frame (milliseconds)
     */
    public void setDelay(final long delay)
    {
        this.delay = delay;
    }
    
    /**
     * Get the time delay (milliseconds)
     * @return The delay per each animation frame
     */
    public long getDelay()
    {
        return this.delay;
    }
    
    /**
     * Assign the current time stamp
     */
    private void assignTime()
    {
        this.time = System.nanoTime();
    }
    
    /**
     * Get the start time for the current animation
     * @return The time when the animation frame began
     */
    private long getTime()
    {
        return this.time;
    }
    
    /**
     * Update the animation
     */
    @Override
    public void update()
    {
    	//if the animation finished, no need to update
    	if (hasFinished())
    		return;
    	
        //if the time has not been set yet
        if (getTime() <= 0)
            assignTime();
        
        //calculate the number of milliseconds passed
        final long elapsed = ((System.nanoTime() - getTime()) / NANO_SECONDS_PER_MILLISECOND);
        
        //if enough time has elapsed to move to the animation
        if (elapsed >= getDelay())
        {
            //increase the frame index
            final int nextFrameIndex = getFrameIndex() + 1;
            
            //update our time
            assignTime();
            
            //if at the end of the animation
            if (nextFrameIndex >= getFrames().length)
            {
                if (hasLoop())
                {
                    //if we are looping, go back to start
                    setFrameIndex(FRAME_INDEX_START);
                }
                else
                {
                    //if we aren't looping move back 1 frame
                    setFrameIndex(nextFrameIndex - 1);
                    
                    //flag finished since we don't loop
                    setFinished(true);
                }
            }
            else
            {
            	//assign the next frame index
            	setFrameIndex(nextFrameIndex);
            }
        }
    }
    
    /**
     * Reset the animation
     */
    @Override
    public void reset()
    {
    	//start at the first animation
        setFrameIndex(FRAME_INDEX_START);
        
        //set the current time
        assignTime();
        
        //flag finished false
        setFinished(false);
    }
    
    /**
     * Flag the animation as finished.<br>
     * You won't be able to flag finished if the animation is set to loop
     * @param finished true = the animation has finished, false otherwise or if the animation is set to loop
     */
    private void setFinished(final boolean finished)
    {
    	this.finished = finished;
    }
    
    /**
     * Has the animation finished?<br>
     * If the animation loops, it will never be finished.<br>
     * The animation will be finished if time expires while on the last frame index.
     * @return true if the animation does not loop and time expires while on the last frame index, otherwise false
     */
    public boolean hasFinished()
    {
    	//it will never finish if looping
    	if (hasLoop())
    		setFinished(false);
    	
    	return this.finished;
    }
    
    /**
     * Get the image
     * @return The current image in our animation
     */
    @Override
    public Bitmap getImage()
    {
        return getFrames()[this.getFrameIndex()];
    }
    
    @Override
    public void dispose()
    {
        if (getFrames() != null)
        {
            for (int index = 0; index < getFrames().length; index++)
            {
                if (getFrames()[index] != null)
                {
                	//if not already recycled
                	if (!getFrames()[index].isRecycled())
                		getFrames()[index].recycle();
                	
                	//assign null
                    getFrames()[index] = null;
                }
            }
        }
        
        this.frames = null;
    }
}