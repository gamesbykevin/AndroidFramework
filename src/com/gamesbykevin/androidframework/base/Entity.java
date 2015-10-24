package com.gamesbykevin.androidframework.base;

import com.gamesbykevin.androidframework.anim.Spritesheet;
import com.gamesbykevin.androidframework.resources.Disposable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.UUID;

/**
 * A basic entity
 * @author ABRAHAM
 */
public class Entity extends Cell implements Disposable
{
    //the unique id for this entity
    private final UUID id;
    
    //each entity will have an (x,y) coordinate
    private double x, y;
    
    //each entity will have a width and height
    private double w, h;
    
    //each entity will have a velocity
    private double dx, dy;
    
    //list of animations
    private Spritesheet spritesheet;
    
    //the source and destination of the rectangle
    private Rect source, destination;
    
    /**
     * Create a new Entity
     */
    public Entity()
    {
        //default location (0, 0)
        super(0, 0);
        
        //create new sprite sheet
        this.spritesheet = new Spritesheet();
        
        //assign a random id
        this.id = UUID.randomUUID();
    }
    
    /**
     * Get the distance
     * @param entity The entity we want to compare
     * @return The distance between the current and specified entities
     */
    public double getDistance(final Entity entity)
    {
        return getDistance(entity.getX(), entity.getY());
    }
    
    /**
     * Get the distance
     * @param x x-coordinate
     * @param y y-coordinate
     * @return The distance between the entity and specified (x,y)
     */
    public double getDistance(final double x, final double y)
    {
        return Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
    }
    
    /**
     * Get the id
     * @return The unique id that identifies this entity
     */
    public UUID getId()
    {
        return this.id;
    }
    
    /**
     * Do we have this id?
     * @param entity The entity containing the unique key we want to check
     * @return true if the id's match, false otherwise
     */
    public boolean hasId(final Entity entity)
    {
        return hasId(entity.getId());
    }
    
    /**
     * Do we have this id?
     * @param id The unique key we want to check
     * @return true if the id's match, false otherwise
     */
    public boolean hasId(final UUID id)
    {
        return (getId().equals(id));
    }
    
    /**
     * Get the sprite sheet
     * @return The sprite sheet containing the animations
     */
    public Spritesheet getSpritesheet()
    {
        return this.spritesheet;
    }
    
    /**
     * Update the (x,y) coordinate based on the assigned (dx, dy) velocity
     */
    public void update()
    {
        setX(getX() + getDX());
        setY(getY() + getDY());
    }
    
    /**
     * Get the x-velocity
     * @return the assigned x-velocity
     */
    public double getDX()
    {
        return this.dx;
    }
    
    /**
     * Get the y-velocity
     * @return the assigned y-velocity
     */
    public double getDY()
    {
        return this.dy;
    }
    
    /**
     * Assign the x-velocity
     * @param dx The desired x-velocity
     */
    public void setDX(final double dx)
    {
        this.dx = dx;
    }
    
    /**
     * Assign the y-velocity
     * @param dy The desired y-velocity
     */
    public void setDY(final double dy)
    {
        this.dy = dy;
    }
    
    /**
     * Assign the x-coordinate
     * @param entity The entity containing the desired x-coordinate
     */
    public void setX(final Entity entity)
    {
        setX(entity.getX());
    }
    
    /**
     * Assign the x-coordinate
     * @param x the desired x-coordinate
     */
    public void setX(final double x)
    {
        this.x = x;
    }
    
    /**
     * Assign the y-coordinate
     * @param entity The entity containing the desired y-coordinate
     */
    public void setY(final Entity entity)
    {
        setY(entity.getY());
    }
    
    /**
     * Assign the y-coordinate
     * @param y the desired y-coordinate
     */
    public void setY(final double y)
    {
        this.y = y;
    }
    
    /**
     * Get the x-coordinate
     * @return the x-coordinate
     */
    public double getX()
    {
        return this.x;
    }
    
    /**
     * Get the y-coordinate
     * @return the y-coordinate
     */
    public double getY()
    {
        return this.y;
    }
    
    /**
     * Get the width
     * @return get the width
     */
    public double getWidth()
    {
        return this.w;
    }
    
    /**
     * Get the height
     * @return get the height
     */
    public double getHeight()
    {
        return this.h;
    }
    
    /**
     * Assign the width
     * @param entity The object containing the width
     */
    public void setWidth(final Entity entity)
    {
        setWidth(entity.getWidth());
    }
    
    /**
     * Assign the width
     * @param w The desired width
     */
    public void setWidth(final double w)
    {
        this.w = w;
    }
    
    /**
     * Assign the height
     * @param entity The object containing the height
     */
    public void setHeight(final Entity entity)
    {
        setHeight(entity.getHeight());
    }
    
    /**
     * Assign the height
     * @param h The desired height
     */
    public void setHeight(final double h)
    {
        this.h = h;
    }
    
    /**
     * Recycle objects
     */
    @Override
    public void dispose()
    {
        if (spritesheet != null)
        {
            spritesheet.dispose();
            spritesheet = null;
        }
    }
    
    /**
     * Get the coordinates where the entity is to be rendered
     * @return The rectangle where the entity is to be rendered
     */
    public Rect getDestination()
    {
        if (this.destination == null)
            this.destination = new Rect();
        
        //assign the destination
        this.destination.set((int)getX(), (int)getY(), (int)(getX() + getWidth()), (int)(getY() + getHeight()));
        
        //return result
        return this.destination;
    }
    
    /**
     * Get the source coordinates of the image
     * @param image The image we want to get the coordinates
     * @return Rectangle with values (0,0,width, height) of the specified image
     */
    private Rect getSource(final Bitmap image)
    {
        //create if not exists
        if (this.source == null)
            this.source = new Rect();
        
        //assign the source
        this.source.set(0, 0, image.getWidth(), image.getHeight());
        
        //return result
        return this.source;
    }
    
    /**
     * Render the entity based on the current location in the current sprite sheet
     * @param canvas Object we want to write our pixel data
     * @throws Exception 
     */
    public void render(final Canvas canvas) throws Exception
    {
        if (getSpritesheet() != null)
        {
            if (getSpritesheet().get() != null)
            {
                render(canvas, getSpritesheet().get().getImage());
            }
            else
            {
                throw new Exception("Current animation is not set or does not exist");
            }
        }
        else
        {
            throw new Exception("Spritesheet does not exist");
        }
    }
    
    /**
     * Render the entity
     * @param canvas Object we want to write our pixel data
     * @param image The image we are rendering
     * @throws Exception 
     */
    public void render(final Canvas canvas, final Bitmap image) throws Exception
    {
        if (getWidth() < 1)
            throw new Exception("width has to be at least 1 pixel");
        if (getHeight() < 1)
            throw new Exception("height has to be at least 1 pixel");
        
        //draw the provided image at the current location
        canvas.drawBitmap(image, getSource(image), getDestination(), null);
    }
}