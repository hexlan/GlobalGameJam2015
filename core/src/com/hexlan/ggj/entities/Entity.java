package com.hexlan.ggj.entities;

import java.awt.Rectangle;

public abstract class Entity 
{
	protected float x, y;
	protected int width;
	protected int height;
	
	protected Entity() {}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public Rectangle getRect() { return new Rectangle((int)x, (int)y, width, height); }
}