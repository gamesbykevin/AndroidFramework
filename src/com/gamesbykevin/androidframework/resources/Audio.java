package com.gamesbykevin.androidframework.resources;

import android.app.Activity;

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
    private static HashMap<Object, Sound> AUDIO = new HashMap<Object, Sound>();
    
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
            if (getSound(keys[index]) == null)
            {
                //create a new sound object
                Sound sound = new Sound(activity, directoryPath + "/" + paths[index]);
                
                //place in hashmap
                AUDIO.put(keys[index], sound);
            }
        }
    }
    
    /**
     * Get the audio resource
     * @param key The unique key of the desired resource
     * @return The audio resource
     */
    public static Sound getSound(final Object key)
    {
        if (AUDIO == null)
            AUDIO = new HashMap<Object, Sound>();
        
        return AUDIO.get(key);
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
            for (Sound sound : AUDIO.values())
            {
                //if exists pause sound
                if (sound != null)
                    sound.stop();
            }
        }
    }
    
    /**
     * Play the audio with no loop
     * @param key The key of the desired audio
     */
    public static void play(final Object key)
    {
        play(key, false);
    }
    
    /**
     * Play the audio
     * @param key The unique key of the desired audio
     * @param loop Do we loop the audio
     */
    public static void play(final Object key, final boolean loop)
    {
        if (!isAudioEnabled())
            return;
        
        if (getSound(key) != null)
            getSound(key).play(loop);
    }
    
    /**
     * Stop all audio, technically we just pause it
     */
    public static void stop()
    {
        if (AUDIO != null)
        {
            for (Object key : AUDIO.keySet())
            {
                stop(key);
            }
        }
    }
    
    /**
     * Stop the audio.<br>
     * We actually just pause the audio if it is already playing
     * @param key The key of the desired audio
     */
    public static void stop(final Object key)
    {
        if (getSound(key) != null)
            getSound(key).stop();
    }
    
    /**
     * Recycle objects
     */
    public static void dispose()
    {
        if (AUDIO != null)
        {
            for (Sound sound : AUDIO.values())
            {
                if (sound != null)
                {
                    sound.dispose();
                    sound = null;
                }
            }
            
            AUDIO.clear();
            AUDIO = null;
        }
    }
}