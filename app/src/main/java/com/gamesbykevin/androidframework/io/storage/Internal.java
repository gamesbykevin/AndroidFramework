package com.gamesbykevin.androidframework.io.storage;

import com.gamesbykevin.androidframework.resources.Disposable;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * This class will handle internal storage.<br>
 * Use this if you want to store data on the internal android device
 * @author GOD
 */
public class Internal implements Disposable
{
    //file name for storage
    private final String filename;
    
    //our required activity reference
    private final Activity activity;
    
    //all contents of the file
    private StringBuilder total;
    
    /**
     * Create internal storage object
     * @param filename Name of our file
     * @param activity Our activity reference
     */
    public Internal(final String filename, final Activity activity)
    {
    	this(filename, activity, false);
    }
    
    /**
     * Create internal storage object
     * @param filename Name of our file
     * @param activity Our activity reference
     * @param debug Do we display the contents loaded?
     */
    public Internal(final String filename, final Activity activity, final boolean debug)
    {
        //store the file name
        this.filename = filename;
        
        //store the activity reference
        this.activity = activity;
        
        //create new string builder where content will be added/stored
        this.total = new StringBuilder();
        
        try
        {
            //create file reference for the desired file
            File file = getActivity().getBaseContext().getFileStreamPath(getFilename());
            
            //if the file exists, read the data
            if (file.exists())
            {
                //attempt to open file input stream
                FileInputStream is = getActivity().openFileInput(getFilename());
                
                //use our stream to create the reader object
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                
                //current line
                String tmp;
                
                //load all content from the input file
                while ((tmp = br.readLine()) != null) 
                {
                    total.append(tmp);
                }
                
                //close the reader
                br.close();
                
                //close the input stream
                is.close();
                
                //display file content loaded
                if (debug)
                	System.out.println("File Contents: " + total);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Get activity reference
     * @return Activity object needed to read/write data
     */
    private Activity getActivity()
    {
        return this.activity;
    }
    
    /**
     * Get file name
     * @return The name of our file
     */
    private String getFilename()
    {
        return this.filename;
    }
    
    /**
     * Get the content
     * @return The file content for this storage
     */
    public StringBuilder getContent()
    {
        return this.total;
    }
    
    /**
     * Save the existing content to the internal storage
     */
    public void save()
    {
        try
        {
            //create output stream
            FileOutputStream os = getActivity().openFileOutput(getFilename(), Context.MODE_PRIVATE);
            
            //write content to file
            os.write(getContent().toString().getBytes());
            
            //recycle resources
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void dispose()
    {
        total = null;
    }
}