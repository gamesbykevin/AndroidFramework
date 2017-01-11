package com.gamesbykevin.androidframework.text;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * This class will format a time with the specified
 * @author GOD
 */
public class TimeFormat 
{
	/**
	 * Time format mm:ss.SSS
	 */
	public static final String FORMAT_1 = "mm:ss.SSS";
	
	/**
	 * Time format ss.SSS
	 */
	public static final String FORMAT_2 = "ss.SSS";
	
	/**
	 * Time format mm:ss.S
	 */
	public static final String FORMAT_3 = "mm:ss.S";
	
    /**
     * Our object used to format the time
     */
    private static final SimpleDateFormat FORMAT_OBJECT = new SimpleDateFormat();
    
    /**
     * Our time object
     */
    private static final Date DATE = new Date();
    
    /**
     * Get the description using the time and format.<br>
     * @param format The desired format
     * @param time The time (milliseconds)
     * @return A string description of the time
     */
    public static final String getDescription(final String format, final long time)
    {
        //assign the time
        DATE.setTime(time);
        
        //apply the pattern
        FORMAT_OBJECT.applyPattern(format);
        
        //return our formatted description
        return FORMAT_OBJECT.format(DATE);
    }
}
