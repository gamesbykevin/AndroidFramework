package com.gamesbykevin.androidframework.anim;

import java.util.HashMap;

import com.gamesbykevin.androidframework.resources.Disposable;

/**
 * Sprite sheet containing animations
 * @author ABRAHAM
 */
public class Spritesheet implements Disposable
{
    //list of animations in our sprite sheet
    private HashMap<Object, Animation> animations;
    
    //the current animation
    private Object key;
    
    /**
     * Create a new sprite sheet
     */
    public Spritesheet()
    {
        this.animations = new HashMap<Object, Animation>();
    }
    
    /**
     * Add the animation
     * @param key The unique key to access the animation
     * @param animation The animation we want to add
     */
    public void add(final Object key, final Animation animation)
    {
        this.animations.put(key, animation);
        
        //if the current animation is not assigned, we will do it now
        if (getKey() == null)
            setKey(key);
    }
    
    /**
     * Get the animation.
     * @param key The key of the desired hash map
     * @return The animation in our hash map
     */
    public Animation get(final Object key)
    {
        return this.animations.get(key);
    }
 
    /**
     * Assign the current animation
     * @param key The unique key of the desired animation
     */
    public void setKey(final Object key)
    {
        this.key = key;
    }
    
    /**
     * Get the current key
     * @return The key of the current animation
     */
    public Object getKey()
    {
        return this.key;
    }
    
    /**
     * Get the current animation
     * @return The current animation
     */
    public Animation get()
    {
        return (get(getKey()));
    }
    
    /**
     * Recycle resources
     */
    public void dispose()
    {
        if (this.animations != null)
        {
            for (Animation animation : animations.values())
            {
                if (animation != null)
                {
                    animation.dispose();
                    animation = null;
                }
            }
            
            animations.clear();
            animations = null;
        }
    }
}