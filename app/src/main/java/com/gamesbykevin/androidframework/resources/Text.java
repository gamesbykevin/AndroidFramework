package com.gamesbykevin.androidframework.resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * A text file
 * @author GOD
 */
public final class Text implements Disposable
{
    //list of lines in our text file
    private List<String> lines;
    
    /**
     * Create text file
     * @param is InputStream required to load text file
     * @throws Exception 
     */
    protected Text(final InputStream is) throws Exception
    {
        //create our input object with the given input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
     
        //current line in text file
        String line;
        
        //create new list
        lines = new ArrayList<String>();
        
        //continue until we reach end of file
        while ((line = br.readLine()) != null)
        {
            //add line to list
            lines.add(line);
        }
        
        //close reader
        br.close();
        
        //close input stream
        is.close();
    }
    
    /**
     * Get the lines
     * @return A list of String characters for each line in our text file
     */
    public List<String> getLines()
    {
        return this.lines;
    }
    
    @Override
    public void dispose()
    {
        if (lines != null)
        {
            for (String line : lines)
            {
                if (line != null)
                    line = null;
            }
            
            lines.clear();
            lines = null;
        }
    }
}