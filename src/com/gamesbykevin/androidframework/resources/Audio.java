package com.gamesbykevin.androidframework.resources;

import android.media.MediaPlayer;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;

import java.util.HashMap;

/**
 * This class will contain the collection of audio
 * @author GOD
 */
public class Audio 
{
    /**
     * Is the audio enabled?
     */
    private static boolean AudioEnabled = true;
    
    //hashmap of audio
    private static HashMap<Object, MediaPlayer> AUDIO = new HashMap<Object, MediaPlayer>();
    
    /**
     * Load the asset
     * @param activity The activity containing the asset manager
     * @param keys Array of unique values for the resources
     * @param directoryPath The location of the resources
     * @throws Exception 
     */
    public static void load(final Activity activity, final Object[] keys, final String directoryPath) throws Exception
    {
        //get the list of images
        String[] paths = activity.getAssets().list(directoryPath);
        
        //make sure the number of existing files, matches the number of keys
        if (paths.length > keys.length)
        {
            throw new Exception("You have more files than keys, the totals must match");
        }
        else if (paths.length < keys.length)
        {
            throw new Exception("You have more keys than files, the totals must match");
        }
        
        //load each asset
        for (int index = 0; index < keys.length; index++)
        {
            //only load asset if it does not exist
            if (getAudio(keys[index]) == null)
            {
                //asset file descriptor
                AssetFileDescriptor afd = activity.getAssets().openFd(directoryPath + "/" + paths[index]);
                
                //create the media player
                MediaPlayer player = new MediaPlayer();
                
                //assign the datasource
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                
                //prepare for playback
                player.prepare();
                
                //place in hashmap
                AUDIO.put(keys[index], player);
            }
        }
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    private static MediaPlayer getAudio(final Object key)
    {
        if (AUDIO == null)
            AUDIO = new HashMap<Object, MediaPlayer>();
        
        return AUDIO.get(key);
    }
    
    /**
     * Recycle objects
     */
    public static void dispose()
    {
        if (AUDIO != null)
        {
            for (MediaPlayer player : AUDIO.values())
            {
                if (player != null)
                {
                    player.release();
                    player = null;
                }
            }
            
            AUDIO.clear();
            AUDIO = null;
        }
    }
    
    /**
     * Is the audio enabled?
     * @return true = yes, false = no
     */
    public static boolean isAudioEnabled()
    {
        return AudioEnabled;
    }
    
    /**
     * Do we turn audio on or off
     * @param audioEnabled true = yes, false = no
     */
    public static void setAudioEnabled(final boolean audioEnabled)
    {
        AudioEnabled = audioEnabled;
        
        //if audio is not enabled, stop all sounds
        if (!AudioEnabled)
        {
            //pause all existing sound
            for (MediaPlayer player : AUDIO.values())
            {
                //if exists pause sound
                if (player != null)
                    player.pause();
            }
        }
        else
        {
            //move all audio to the beginning
            for (MediaPlayer player : AUDIO.values())
            {
                if (player != null)
                    player.seekTo(0);
            }
        }
    }
    
    /**
     * 
     * @param key 
     */
    public static void play(final Object key)
    {
        play(key, false);
    }
    
    /**
     * 
     * @param key
     * @param loop 
     */
    public static void play(final Object key, final boolean loop)
    {
        if (!isAudioEnabled())
            return;
        
        //don't play if already playing
        if (!getAudio(key).isPlaying())
        {
            getAudio(key).setLooping(loop);
            getAudio(key).start();
        }
    }
    
    /**
     * 
     * @param key 
     */
    public static void stop(final Object key)
    {
        if (getAudio(key) != null)
        {
            if (getAudio(key).isPlaying())
                getAudio(key).pause();
        }
    }
}