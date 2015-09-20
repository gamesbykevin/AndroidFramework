package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

/**
 * This class will represent a sound
 * @author GOD
 */
public class Sound extends MediaPlayer implements Disposable
{
    /**
     * The start position of the sound resource
     */
    private static final int POSITION_START = 0;
    
    //is this resource prepared
    private boolean prepared = false;
    
    //is this resource paused
    private boolean paused = false;
    
    /**
     * Create a new sound resource
     * @param activity Object containing asset manager to load resource
     * @param path The physical location of the resource
     * @throws Exception 
     */
    public Sound(final Activity activity, final String path) throws Exception
    {
        //call parent constructor
        super();
        
        //the object used to read the data of this resource
        AssetFileDescriptor afd = activity.getAssets().openFd(path);
        
        //set the data sounce
        setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        
        //assign the listnener so we will know when the resource is ready for playback
        setOnPreparedListener(new OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer player)
            {
                //flag prepared as true
                setPrepared(true);
            }
        });
        
        //prepare resource for playback
        super.prepare();
    }

    
    /**
     * 
     * @return 
     */
    public boolean isPrepared()
    {
        return this.prepared;
    }
    
    /**
     * 
     * @param prepared 
     */
    public void setPrepared(final boolean prepared)
    {
        this.prepared = prepared;
    }
    
    public boolean isPaused()
    {
        return this.paused;
    }
    
    @Override
    public void pause()
    {
        //don't bother if the resource isn't prepared
        if (!isPrepared())
            return;
        
        //pause the audio
        super.pause();
        
        //flag paused true
        setPaused(true);
    }
    
    /**
     * Stop playback of the resource.<br>
     * We move the audio playback position back to the beginning and then pause
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
        
        //don't play if already playing
        if (!isPlaying())
        {
            //set loop
            setLooping(loop);
            
            //play the audio
            start();
        }
        else
        {
            //go to beginning
            seekTo(POSITION_START);
            
            //play the audio
            start();
        }
    }
    
    /**
     * 
     * @param paused 
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
        super.release();
    }
}