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
    
    //hash map of audio
    private static HashMap<Object, Sound> AUDIO = new HashMap<Object, Sound>();
    
    /**
     * Load all the assets in the specified directory at once
     * @param activity The activity containing the asset manager
     * @param keys Array of unique values for the resources
     * @param directoryPath The location of the resources
     * @return total number of assets loaded
     * @throws Exception 
     */
    public static int load(final Activity activity, final Object[] keys, final String directoryPath) throws Exception
    {
        return load(activity, keys, directoryPath, true);
    }
    
    /**
     * Load all assets at the specified directory
     * @param activity The activity containing the asset manager
     * @param keys Array of unique values for the resources
     * @param directoryPath The location of the resources
     * @param batchLoad true if we want to load all assets at the specified directory at once, false if we just want to load one resource at a time
     * @return total number of assets loaded
     * @throws Exception 
     */
    public static int load(final Activity activity, final Object[] keys, final String directoryPath, final boolean batchLoad) throws Exception
    {
        //the total number of resources loaded
        int count = 0;
        
        //get the list of images
        String[] paths = activity.getAssets().list(directoryPath);
        
        //make sure the number of existing files, matches the number of keys
        if (paths.length > keys.length)
        {
            throw new Exception("You have more files than keys, the totals must match: " + directoryPath);
        }
        else if (paths.length < keys.length)
        {
            throw new Exception("You have more keys than files, the totals must match: " + directoryPath);
        }
        
        //load each asset
        for (int index = 0; index < keys.length; index++)
        {
            //load the resource
            final boolean result = loadSound(activity, keys[index], directoryPath + "/" + paths[index]);
            
            //increase the count
            count++;
            
            //if the asset was loaded, and we don't want to do the batch load, return the count so far
            if (result && !batchLoad)
                return count;
        }
        
        //return the number of resources loaded
        return count;
    }
    
    /**
     * Load a single asset at the specified location
     * @param activity The activity containing the asset manager
     * @param key The unique key to access the resource
     * @param location The location of the resource
     * @return true if the asset was loaded, false if the asset already exists
     * @throws Exception 
     */
    public static boolean loadSound(final Activity activity, final Object key, final String location) throws Exception
    {
        //only load asset if it does not exist
        if (getSound(key) == null)
        {
            //create sound while placing in hash map
            AUDIO.put(key, new Sound(activity, location));
            return true;
        }
        
        //resource already exists
        return false;
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
        	//make sure audio hash map exists
            if (AUDIO != null)
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
     * Set the desired volume for all existing audio
     * @param volume The desired volume 0.0 (%0 volume), 1.0 (100% volume)
     */
    public static void setVolume(final float volume)
    {
        if (AUDIO != null)
        {
            for (Object key : AUDIO.keySet())
            {
                setVolume(key, volume);
            }
        }
    }
    
    /**
     * Assign the volume for the specified audio
     * @param key The key of the audio we want to manipulate
     * @param volume The desired volume 0.0 (%0 volume), 1.0 (100% volume)
     */
    public static void setVolume(final Object key, float volume)
    {
        //if the sound exists 
        if (getSound(key) != null)
        {
            //make sure the audio is available for play back
            if (getSound(key).isPrepared())
            {
                //keep volume in range
                if (volume < 0f)
                    volume = 0.0f;
                if (volume > 1f)
                    volume = 1.0f;

                getSound(key).setVolume(volume, volume);
            }
        }
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