package com.hexlan.ggj.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.systems.GSM;

public abstract class GameState {

	protected GSM gsm;

	protected GameState(GSM gsm) {
		this.gsm = gsm;
	}

	public abstract void handleInput();

	public abstract void update(float dt);

	public abstract void render(SpriteBatch sb);
}
