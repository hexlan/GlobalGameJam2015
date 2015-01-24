package com.hexlan.ggj.entities;

import java.awt.Point;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public abstract class Character extends Entity
{
	protected float dx;
	protected float dy;
	
	protected float xtemp;
	protected float ytemp;
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean jumping;
	public boolean falling;
	
	public boolean facingRight;
	
	protected int fallCounter;
	
	private TiledMapTileLayer collisionLayer;
	
	protected Character()
	{
		super();
		
		facingRight = true;
	}
	
	
	public void setCollisionLayer(TiledMapTileLayer collisionLayer) { this.collisionLayer = collisionLayer; }
	public void setDX(float dx) { this.dx = dx; }
	public void setDY(float dy) { this.dy = dy; }
	
	public float getDX() { return dx; }
	public float getDY() { return dy; }
	
	private boolean hasCollisionY(int start, int stop, int y)
	{
		for(int col = start; col <= stop; col++)
		{
			Cell cell = collisionLayer.getCell(col, y);
			if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) { return true; }
		}
		
		return false;
	}
	
	private boolean hasCollisionX(int start, int stop, int x)
	{
		for(int row = start; row <= stop; row++)
		{
			Cell cell = collisionLayer.getCell(x, row);
			if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) { return true; }
		}
		
		return false;
	}
	
	private void checkDownCollision(Point topLeft, Point bottomRight)
	{
		if(hasCollisionY(topLeft.x, bottomRight.x, topLeft.y))
		{
			dy = 0;
			falling = false;
			int row = ((int)(ytemp + dy) - height/2) / (int)collisionLayer.getTileHeight();
			ytemp = row * (int)collisionLayer.getTileHeight() + height / 2;
		}
		else
		{
			ytemp += dy;
		}
	}
	
	private void checkUpCollision(Point topLeft, Point bottomRight)
	{
		if(hasCollisionY(topLeft.x, bottomRight.x, bottomRight.y))
		{
			dy = 0;
			int row = ((int)ytemp + height/2) / (int)collisionLayer.getTileHeight();
			ytemp = (row + 1) * (int)collisionLayer.getTileHeight() - height / 2 - 1;
		}
		else
		{
			ytemp += dy;
		}
	}
	
	private void checkLeftCollision(Point topLeft, Point bottomRight)
	{
		if(hasCollisionX(topLeft.y, bottomRight.y, topLeft.x))
		{
			dx = 0;
			int col = ((int)xtemp - width/2) / (int)collisionLayer.getTileWidth();
			xtemp = col * (int)collisionLayer.getTileWidth() + width / 2;
		}
		else
		{
			xtemp += dx;
		}
	}
	
	private void checkRightCollision(Point topLeft, Point bottomRight)
	{
		if(hasCollisionX(topLeft.y, bottomRight.y, bottomRight.x))
		{
			dx = 0;
			int col = ((int)xtemp + width/2) / (int)collisionLayer.getTileWidth();
			xtemp = (col + 1) * collisionLayer.getTileWidth() - width / 2 - 1;
		}
		else
		{
			xtemp += dx;
		}
	}
	
	private void checkCollisionY()
	{
		if(dy == 0) { return; }
		
		Point topLeft = new Point(((int)(xtemp) - width/2) / (int)collisionLayer.getTileWidth(), ((int)(ytemp + dy) - height/2) / (int)collisionLayer.getTileHeight());
		Point bottomRight = new Point(((int)(xtemp) + width/2) / (int)collisionLayer.getTileWidth(), ((int)(ytemp + dy) + height/2) / (int)collisionLayer.getTileHeight());
		
		// Down
		if(dy < 0) { checkDownCollision(topLeft, bottomRight); }
		
		// Up
		if(dy > 0) { checkUpCollision(topLeft, bottomRight);  }
	}
	
	private void checkCollisionX()
	{
		if(dx == 0) { return; }

		Point topLeft = new Point(((int)(xtemp + dx) - width/2) / (int)collisionLayer.getTileWidth(), ((int)ytemp - height/2) / (int)collisionLayer.getTileHeight());
		Point bottomRight = new Point(((int)(xtemp + dx) + width/2) / (int)collisionLayer.getTileWidth(), ((int)ytemp + height/2) / (int)collisionLayer.getTileHeight());
		
		// Left
		if(dx < 0) { checkLeftCollision(topLeft, bottomRight); }
		
		// Right
		if(dx > 0) { checkRightCollision(topLeft, bottomRight); }
	}
	
	private void checkForGround()
	{
		Point topLeft = new Point(((int)xtemp - width/2) / (int)collisionLayer.getTileWidth(), ((int)ytemp - height/2) / (int)collisionLayer.getTileHeight());
		Point bottomRight = new Point(((int)xtemp + width/2) / (int)collisionLayer.getTileWidth(), ((int)ytemp + height/2 + 1) / (int)collisionLayer.getTileHeight());
		
		if(!falling)
		{
			if(!hasCollisionY(topLeft.x, bottomRight.x, bottomRight.y)) 
			{

				falling = true;
				fallCounter = 10;
			}
		}
	}
	
	public void checkCollision()
	{
		xtemp = x;
		ytemp = y;
		
		checkCollisionX();
		checkCollisionY();
		checkForGround();
	}
}