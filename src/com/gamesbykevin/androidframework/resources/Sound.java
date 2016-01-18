package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

/**
 * This class will represent a sound
 * @author GOD
 */
public final class Sound extends MediaPlayer implements Disposable
{
    /**
     * The start position of the sound resource
     */
    private static final int POSITION_START = 0;
    
    //is this resource prepared
    private boolean prepared = false;
    
    //is this resource paused
    private boolean paused = false;
    
    //the current play back position (milliseconds)
    private int position = 0;
    
    /**
     * Create a new sound resource
     * @param activity Object containing asset manager to load resource
     * @param path The physical location of the resource
     * @throws Exception 
     */
    protected Sound(final Activity activity, final String path) throws Exception
    {
        //call parent constructor
        super();
        
        //the object used to read the data of this resource
        AssetFileDescriptor afd = activity.getAssets().openFd(path);
        
        //set the data source
        setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        
        //assign the listener so we will know when the resource is ready for play back
        setOnPreparedListener(new OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer player)
            {
                //flag prepared as true
                setPrepared(true);
            }
        });
        
        //prepare resource for play back
        super.prepare();
    }

    /**
     * Has the audio been prepared for play back
     * @return true = yes, false = no
     */
    public boolean isPrepared()
    {
        return this.prepared;
    }
    
    /**
     * Flag the audio prepared
     * @param prepared true = prepared, false = otherwise
     */
    public void setPrepared(final boolean prepared)
    {
        this.prepared = prepared;
    }
    
    /**
     * Is the audio paused
     * @return true = yes, false = no
     */
    public boolean isPaused()
    {
        return this.paused;
    }
    
    /**
     * Assign the position based on the current position of play back (milliseconds)
     */
    private void setPosition()
    {
    	this.position = super.getCurrentPosition();
    }
    
    /**
     * Get the previous position
     * @return The previous position of desired play back (milliseconds)
     */
    private int getPosition()
    {
    	return this.position;
    }
    
    @Override
    public void pause()
    {
        //don't bother if the resource isn't prepared
        if (!isPrepared())
            return;
        
        //store the current position
        setPosition();
        
        //pause the audio
        super.pause();
        
        //flag paused true
        setPaused(true);
    }
    
    /**
     * Stop play back of the resource.<br>
     * We move the audio play back position back to the beginning and then pause
     */
    @Override
    public void stop()
    {
        //don't bother if the resource isn't prepared
        if (!isPrepared())
            return;
        
        //if looping or playing
        if (isLooping() || isPlaying())
        {
            //move the position back to beginning
            seekTo(POSITION_START);
            
            //pause the audio
            pause();
        }
    }
    
    /**
     * Play the sound resource with no loop
     */
    public void play()
    {
        play(false);
    }
    
    /**
     * Play the sound resource
     * @param loop Should the sound loop?
     */
    public void play(final boolean loop)
    {
        //don't bother if the resource isn't prepared
        if (!isPrepared())
            return;
        
        //set loop
        setLooping(loop);
        
        //if the game
        if (isPaused())
        {
        	//start play back at the previous position
        	seekTo(getPosition());
        }
        else
        {
            //start from the beginning
            seekTo(POSITION_START);
        }
        
        //play the audio
        start();
    }
    
    /**
     * Flag the audio paused
     * @param paused true = yes, false = no
     */
    public void setPaused(final boolean paused)
    {
        this.paused = paused;
    }
    
    /**
     * Recycle resources
     */
    @Override
    public void dispose()
    {
    	//reset the media player
    	super.reset();
    	
    	//release allocated resources
        super.release();
    }
}