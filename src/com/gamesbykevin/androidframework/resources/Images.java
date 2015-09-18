package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

/**
 * This class will contain a list of images
 * @author GOD
 */
public class Images
{
    //list of images
    private static HashMap<Object, Bitmap> IMAGES = new HashMap<Object, Bitmap>();
    
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
            if (getImage(keys[index]) == null)
                IMAGES.put(keys[index], BitmapFactory.decodeStream(activity.getAssets().open(directoryPath + "/" + paths[index])));
        }
    }
    
    /**
     * Get the desired image
     * @param key Unique key to get the image
     * @return The desired image
     */
    public static Bitmap getImage(final Object key)
    {
        if (IMAGES == null)
            IMAGES = new HashMap<Object, Bitmap>();
        
        return IMAGES.get(key);
    }
    
    /**
     * Recycle objects
     */
    public static void dispose()
    {
        if (IMAGES != null)
        {
            for (Bitmap image : IMAGES.values())
            {
                if (image != null)
                {
                    image.recycle();
                    image = null;
                }
            }
            
            IMAGES.clear();
            IMAGES = null;
        }
    }
}