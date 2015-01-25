package com.hexlan.ggj.entities;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Arrow extends Entity
{
	public float dx;
	public TiledMapTileLayer mapLayer;
	public boolean goingRight;
	
	public Arrow(float x, float y, TiledMapTileLayer mapLayer, boolean goingRight)
	{
		super();
		image = new Texture("images/Arrow.png");
		this.x = x;
		this.y = y;
		width = image.getWidth() - 8;
		height = image.getHeight() - 4;
		this.mapLayer = mapLayer;
		this.goingRight = goingRight;
		dx = 5.5f;
	}
	
	public boolean Collision()
	{
		Point topLeft = new Point(((int)(x) - width/2) / (int)mapLayer.getTileWidth(), ((int)y - height/2) / (int)mapLayer.getTileHeight());
		Point bottomRight = new Point(((int)(x) + width/2) / (int)mapLayer.getTileWidth(), ((int)y + height/2) / (int)mapLayer.getTileHeight());
		
		for(int col = topLeft.x; col <= bottomRight.x; col++)
		{
			for(int row = topLeft.y; row <= bottomRight.y; row++)
			{
				Cell cell = mapLayer.getCell(col, row);
				if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) 
				{ 
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void hitPlayer(Player player)
	{
		if(getRect().intersects(player.getRect()))  { player.respawn(); }
	}
	
	public void update(float dt)
	{
		if(goingRight)
		{
			x += dx;
		}
		else
		{
			x -= dx;
		}
	}
}
