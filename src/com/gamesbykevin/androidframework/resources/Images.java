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
     * Load the assets in the specified location
     * @param activity The activity containing the asset manager used to load the assets
     * @param keys Array of unique values which will be used to access the resources
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
            final boolean result = loadImage(activity, keys[index], directoryPath + "/" + paths[index]);
            
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
     * Load a single asset in the specified location
     * @param activity The activity containing the asset manager used to load the assets
     * @param key The unique key used to access this resource
     * @param location The location of the resource
     * @return true if the asset was loaded, false if the asset already exists
     * @throws Exception 
     */
    public static boolean loadImage(final Activity activity, final Object key, final String location) throws Exception
    {
        //only load the asset if it does not exist or if it has been recycled
        if (getImage(key) == null || getImage(key) != null && getImage(key).isRecycled())
        {
            IMAGES.put(key, BitmapFactory.decodeStream(activity.getAssets().open(location)));
            return true;
        }
        
        //asset already exists
        return false;
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
                if (image != null && !image.isRecycled())
                    image.recycle();
                
                image = null;
            }
            
            IMAGES.clear();
            IMAGES = null;
        }
    }
}