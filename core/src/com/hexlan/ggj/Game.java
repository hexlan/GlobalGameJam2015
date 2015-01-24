package com.hexlan.ggj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.systems.GSM;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private SpriteBatch sb;
	private GSM gsm;
	@Override
	public void create () {
		sb = new SpriteBatch();
		
		gsm = new GSM();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
}
