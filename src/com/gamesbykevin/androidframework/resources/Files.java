package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import java.io.InputStream;
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
            if (getText(keys[index]) == null)
                FILES.put(keys[index], new Text(activity.getAssets().open(directoryPath + "/" + paths[index])));
        }
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