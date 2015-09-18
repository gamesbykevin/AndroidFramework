package com.gamesbykevin.androidframework.resources;

import android.app.Activity;
import android.graphics.Typeface;
import java.util.HashMap;

/**
 *
 * @author GOD
 */
public class Font 
{
    //hashmap of fonts
    private static HashMap<Object, Typeface> FONTS = new HashMap<Object, Typeface>();
    
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
            if (getFont(keys[index]) == null)
                FONTS.put(keys[index], Typeface.createFromAsset(activity.getAssets(), directoryPath + "/" + paths[index]));
        }
    }
    
    public static final Typeface getFont(final Object key)
    {
        if (FONTS == null)
            FONTS = new HashMap<Object, Typeface>();
        
        return FONTS.get(key);
    }
    
    /**
     * Recycle objects
     */
    public static void dispose()
    {
        if (FONTS != null)
        {
            for (Typeface font : FONTS.values())
            {
                if (font != null)
                    font = null;
            }
            
            FONTS.clear();
            FONTS = null;
        }
    }
}