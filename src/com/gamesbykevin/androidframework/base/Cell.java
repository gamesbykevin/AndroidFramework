package com.gamesbykevin.androidframework.base;

/**
 * Cell is a location (column, row)
 * @author ABRAHAM
 */
public class Cell 
{
    private double col, row;
    
    public Cell(final double col, final double row)
    {
        this.col = col;
        this.row = row;
    }
    
    public double getCol()
    {
        return this.col;
    }
    
    public double getRow()
    {
        return this.row;
    }
    
    public void setCol(final double col)
    {
        this.col = col;
    }
    
    public void setRow(final double row)
    {
        this.row = row;
    }
}