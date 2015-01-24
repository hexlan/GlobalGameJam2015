package com.hexlan.ggj.entities;


public class Player extends Character
{

	public Player()
	{
		super();
	}
	
	private void getNextPosition()
	{
		if(left) { dx = -7.5f; }
		else if(right) { dx = 7.5f; }
		else      { dx = 0; }
	
		if(jumping && !falling || (jumping && fallCounter > 0) ) 
		{
			fallCounter = 0;
			dy = 18;
			falling = true;
		}
		
		if(falling) 
		{
			dy -= 0.85f;
			if(dy > 0 && jumping) dy += 0.31f;
		}
	}
	
	public void update(float dt)
	{
		if(left) { facingRight = false; }
		if(right) { facingRight = true; }
		if(fallCounter > 0) { fallCounter--; }
		getNextPosition();
		checkCollision();
		setPosition(xtemp, ytemp);
		animation.update();
	}
}