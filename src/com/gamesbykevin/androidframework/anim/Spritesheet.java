package com.gamesbykevin.androidframework.anim;

import java.util.HashMap;

import com.gamesbykevin.androidframework.resources.Disposable;

/**
 * Sprite sheet containing animations
 * @author ABRAHAM
 */
public class Spritesheet implements Disposable
{
    private HashMap<Object, Animation> animations;
    
    public Spritesheet()
    {
        this.animations = new HashMap<Object, Animation>();
    }
    
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
