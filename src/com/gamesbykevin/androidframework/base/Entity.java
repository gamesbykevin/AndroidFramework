package com.gamesbykevin.androidframework.base;

import com.gamesbykevin.androidframework.anim.Spritesheet;
import com.gamesbykevin.androidframework.resources.Disposable;

/**
 * Entity
 * @author ABRAHAM
 */
public class Entity implements Disposable
{
    //each entity will have an (x,y) coordinate
    private double x, y;
    
    //each entity will have a width and height
    private double w, h;
    
    //each entity will have a velocity
    private double dx, dy;
    
    //list of animations
    private Spritesheet spritesheet;
    
    public Entity()
    {
        
    }
    
    public void update()
    {
        setX(getX() + getDX());
        setY(getY() + getDY());
    }
    
    public double getDX()
    {
        return this.dx;
    }
    
    public double getDY()
    {
        return this.dy;
    }
    
    public void setDX(final double dx)
    {
        this.dx = dx;
    }
    
    public void setDY(final double dy)
    {
        this.dy = dy;
    }
    
    public void setX(final double x)
    {
        this.x = x;
    }
    
    public void setY(final double y)
    {
        this.y = y;
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public double getY()
    {
        return this.y;
    }
    
    public double getWidth()
    {
        return this.w;
    }
    
    public double getHeight()
    {
        return this.h;
    }
    
    public void setWidth(final double w)
    {
        this.w = w;
    }
    
    public void setHeight(final double h)
    {
        this.h = h;
    }
    
    @Override
    public void dispose()
    {
        if (spritesheet != null)
        {
            spritesheet.dispose();
            spritesheet = null;
        }
    }
}