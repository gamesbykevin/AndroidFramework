package com.gamesbykevin.androidframework.anim;

import com.gamesbykevin.androidframework.resources.Disposable;

import android.graphics.Bitmap;

public interface IAnimation extends Disposable
{
	public void reset();
	
	public void update();
	
	public Bitmap getImage();
}
