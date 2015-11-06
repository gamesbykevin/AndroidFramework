package com.gamesbykevin.androidframework.util;

import com.gamesbykevin.androidframework.resources.Disposable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Progress implements Disposable
{
    //the goal will be the logic used to determine Progress isComplete()
    private int goal  = 0;
    
    //the count will be the logic used to track how close we are to goal
    private int count = 0;
    
    //text to be displayed to the user
    private String description = null;
 
    //the location where the description is rendered
    private int descriptionX, descriptionY;
    
    //the location where the progress bar is rendered
    private Rect progressBar;
    
    //our paint object
    private Paint paint;
    
    //the area the progress will be drawn within
    private Rect screen;
    
    //temporary rectangle object
    private Rect tmp;
    
    //the progress bar will be a % of the assigned screen
    private static final double PROGRESS_RATIO_WIDTH = .75;
    
    /**
     * Default loading text to be displayed
     */
    private static final String DEFAULT_DESCRIPTION_LOADING_TEXT = "Loading ";
    
    /**
     * Default screen to set if none exist
     */
    private static final Rect DEFAULT_SCREEN = new Rect(0,0,100,100);
    
    //extra text to help us position the load description
    private static final String EXTRA_TEXT = " 000%";
    
    /**
     * Create new Progress tracker with the desired goal set.<br>
     * @param goal The goal we are trying to reach
     */
    public Progress(final int goal)
    {
        setGoal(goal);
    }
    
    /**
     * Set the screen where the progress will be shown
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     */
    public void setScreen(final int x, final int y, final int w, final int h)
    {
        //if exists update coordinates
        if (getScreen() != null)
        {
            getScreen().left = x;
            getScreen().right = x + w;
            getScreen().top = y;
            getScreen().bottom = y + h;
        }
        else
        {
            setScreen(new Rect(x, y, x + w, y + h));
        }
    }
    
    /**
     * Set the screen where the progress will be shown
     * @param screen Dimensions of our window
     */
    public void setScreen(final Rect screen)
    {
        this.screen = screen;
    }
    
    /**
     * Get the rectangle boundary where we will be drawing the progress
     * @return Dimensions of our window
     */
    public Rect getScreen()
    {
        if (this.screen == null)
            setScreen(DEFAULT_SCREEN);
        
        return this.screen;
    }
    
    public void setPaint(final Paint paint)
    {
        this.paint = paint;
    }
            
    public Paint getPaint()
    {
        if (this.paint == null)
            setPaint(new Paint());
    	
        return this.paint;
    }
    
    @Override
    public void dispose()
    {
        this.description = null;
        this.screen = null;
    }
    
    /**
     * Assign the text to display when rendering the progress.<br>
     * Here we will also assign the (x, y) coordinates where the description will be drawn
     * @param description The description to display next to the progress. Example "Loading .."
     */
    public void setDescription(final String description)
    {
        //assign the description
        this.description = description;
        
        //initialize if null
        if (tmp == null)
            tmp = new Rect();
        
        //get the bounds of the text description plus the load progress
        getPaint().getTextBounds(getDescription() + EXTRA_TEXT, 0, getDescription().length(), tmp);
        
        //get middle of the assigned screen
        final int middleX = getScreen().left + (getScreen().width() / 2);
        final int middleY = getScreen().top + (getScreen().height() / 2);
        
        //initialize if null
        if (this.progressBar == null)
            this.progressBar = new Rect();
        
        //assign progress bar location
        this.progressBar.left = middleX - (int)((getScreen().width() * PROGRESS_RATIO_WIDTH) / 2);
        this.progressBar.right = progressBar.left + (int)(getScreen().width() * PROGRESS_RATIO_WIDTH);
        this.progressBar.top = middleY + (tmp.height() / 2);
        this.progressBar.bottom = progressBar.top + tmp.height();
        
        //assign description to be rendered in the middle
        this.descriptionX = progressBar.left;
        this.descriptionY = progressBar.top - tmp.height();
    }
    
    /**
     * Get the description that will be displayed when Progress is rendered
     * @return String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Get the goal we have set
     * @return double The goal we are trying to reach
     */
    public int getGoal()
    {
        return goal;
    }
    
    /**
     * Set the goal we want.
     * @param goal The goal we are trying to reach
     */
    public final void setGoal(final int goal)
    {
        this.goal = goal;
    }
    
    /**
     * Where are we currently at in regards to our goal
     * @return double The number of updates made to this object
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * Increase the progress towards our goal by 1
     */
    public void increase()
    {
        setCount(getCount() + 1);
    }
    
    /**
     * Set the progress towards our goal
     * @param count The completed progress
     */
    public void setCount(final int count)
    {
        this.count = count;
    }
    
    /**
     * Make the progress 100% complete<br>
     * We do this my assigning the count the same value of the goal
     */
    public void markComplete()
    {
        setCount(getGoal());
    }
    
    /**
     * Get the progress towards reaching the goal in the form of a decimal.
     * @return double Progress typically ranging from 0.0 to 1.0
     */
    public double getProgress()
    {
        return ((double)getCount() / (double)getGoal());
    }
    
    /**
     * Has the count reached the goal
     * @return boolean
     */
    public boolean isComplete()
    {
        return (getCount() >= getGoal());
    }
    
    /**
     * Draw the progress
     * @param canvas Object used to write image
     */
    public void render(Canvas canvas)
    {
        //set a default description if not already set
        if (getDescription() == null)
            setDescription(DEFAULT_DESCRIPTION_LOADING_TEXT);
        
        //make progress background black
        getPaint().setColor(Color.BLACK);
        getPaint().setStyle(Paint.Style.FILL);
        canvas.drawRect(getScreen(), getPaint());
        
        //draw the text description
        getPaint().setColor(Color.WHITE);
        canvas.drawText(getDescription() + getCompleteProgress() + "%", descriptionX, descriptionY, getPaint());
        
        //draw the progress border
        getPaint().setStyle(Paint.Style.STROKE);
        canvas.drawRect(progressBar, getPaint());
        
        //fill the progress border
        getPaint().setStyle(Paint.Style.FILL);
        canvas.drawRect(
            progressBar.left, 
            progressBar.top, 
            progressBar.left + (int)(progressBar.width() * getProgress()), 
            progressBar.bottom, 
            getPaint()
        );
    }
    
    /**
     * Calculate the progress complete as a percentage. (not a decimal)
     * @return int Percent complete typically from 0% - 100%
     */
    private int getCompleteProgress()
    {
        int complete = (int)(getProgress() * 100);
        
        if (complete >= 100)
            complete = 100;
        
        return complete;
    }
}