package com.hexlan.ggj.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.systems.GSM;

public class CreditsState extends GameState {

	int musicDelay = 0;
	
	protected CreditsState(GSM gsm) {
		super(gsm);
		
	}

	@Override
	public void handleInput() {}

	@Override
	public void update(float dt) 
	{
		if(musicDelay < 120)
		{
			musicDelay++;
		}
		if(musicDelay == 119)
		{
			GSM.creditMusic();
		}
	}

	@Override
	public void render(SpriteBatch sb) 
	{
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
