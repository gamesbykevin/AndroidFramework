package com.gamesbykevin.androidframework.base;

/**
 * Cell is a location (column, row)
 * @author ABRAHAM
 */
public class Cell 
{
    //the locaton
    private double col, row;
    
    /**
     * Create a new cell of the specified location
     * @param col Column
     * @param row Row
     */
    public Cell(final double col, final double row)
    {
        this.col = col;
        this.row = row;
    }
    
    /**
     * Get the assigned column
     * @return The assigned column
     */
    public double getCol()
    {
        return this.col;
    }
    
    /**
     * Get the assigned row
     * @return The assigned row
     */
    public double getRow()
    {
        return this.row;
    }
    
    /**
     * Assign the column
     * @param cell Cell containing the desired column
     */
    public void setCol(final Cell cell)
    {
        setCol(cell.getCol());
    }
    
    /**
     * Assign the column
     * @param col 
     */
    public void setCol(final double col)
    {
        this.col = col;
    }
    
    /**
     * Assign the row
     * @param cell Cell containing the desired row
     */
    public void setRow(final Cell cell)
    {
        setRow(cell.getRow());
    }
    
    /**
     * Assign the row
     * @param row 
     */
    public void setRow(final double row)
    {
        this.row = row;
    }
}