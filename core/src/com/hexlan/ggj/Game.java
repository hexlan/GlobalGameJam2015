package com.hexlan.ggj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.systems.GSM;
import com.hexlan.ggj.systems.TestState;

public class Game extends ApplicationAdapter {
	private SpriteBatch sb;
	private GSM gsm;
	@Override
	public void create () {
		sb = new SpriteBatch();
		
		gsm = new GSM();
		gsm.push(new TestState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
}
