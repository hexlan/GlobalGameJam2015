package com.hexlan.ggj.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Character
{
	private Sound jumpSound;
	
	public Animation animation;
	public ArrayList<TextureRegion[]> sprites;
	public final int[] NUMFRAMES = { 4, 6, 1, 1  };
	public final int[] FRAMEWIDTHS = { 64, 72, 72, 72 };
	public final int[] FRAMEHEIGHTS = { 64, 72, 72, 72 };
	public final int[] SPRITEDELAYS = { 12, 8, -1, -1 };
	
	public static final int IDLE = 0;
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	
	protected int currentAction;
	protected int prevAction;

	public Player()
	{
		super();
		
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav"));
		
	}
	
	private void getNextPosition()
	{
		if(left) 
		{ 
			dx = -7.5f; 
			if(!falling || fallCounter > 0) { currentAction = WALKING; }
		}
		else if(right) 
		{ 
			dx = 7.5f;
			if(!falling || fallCounter > 0) { currentAction = WALKING; }
		}
		else      
		{ 
			dx = 0; 
			if(!falling || fallCounter > 0) { currentAction = IDLE; }
		}
	
		if(jumping && !falling || (jumping && fallCounter > 0) ) 
		{
			fallCounter = 0;
			dy = 16;
			falling = true;
			jumpSound.play();
		}
		
		if(falling) 
		{
			dy -= 0.85f;
			if(dy > 0 && jumping) dy += 0.37f;
			
			if(dy > 0 && fallCounter == 0)
			{
				currentAction = JUMPING; 
			}
			else if (dy < 0 && fallCounter < 1)
			{
				currentAction = FALLING;
			}
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
		hazardCollision();
		
		if(currentAction != prevAction)
		{
			animation.setFrames(sprites.get(currentAction));
	        animation.setDelay(SPRITEDELAYS[currentAction]);
	        animation.setNumFrames(NUMFRAMES[currentAction]);
	        animation.setFrame(0);
		}
		
		prevAction = currentAction;
	}
}