package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import java.util.HashMap;

/**
 * Here we will load and manage text files
 * @author GOD
 */
public class Files 
{
    //list of text files
    private static HashMap<Object, Text> FILES = new HashMap<Object, Text>();
    
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
     * Load all the assets in the specified directory
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
            final boolean result = loadText(activity, keys[index], directoryPath + "/" + paths[index]);
            
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
     * Load a single text file asset
     * @param activity The activity containing the asset manager
     * @param key Unique key to access the resource
     * @param location The location of the resource
     * @return true if the asset was loaded, false if the asset already exists
     * @throws Exception 
     */
    public static boolean loadText(final Activity activity, final Object key, final String location) throws Exception
    {
        //only load asset if it does not exist
        if (getText(key) == null)
        {
            FILES.put(key, new Text(activity.getAssets().open(location)));
            return true;
        }
        
        //resource was not loaded
        return false;
    }
    
    /**
     * Get the desired text file
     * @param key Unique key to get the text file
     * @return The desired text file
     */
    public static Text getText(final Object key)
    {
        if (FILES == null)
            FILES = new HashMap<Object, Text>();
        
        //get result
        return FILES.get(key);
    }
    
    /**
     * Recycle resources
     */
    public static void dispose()
    {
        if (FILES != null)
        {
            for (Text text : FILES.values())
            {
                if (text != null)
                {
                    text.dispose();
                    text = null;
                }
            }
            
            FILES.clear();
            FILES = null;
        }
    }
}