package com.hexlan.ggj.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.Game;
import com.hexlan.ggj.systems.GSM;

public class MenuState extends GameState {

	private Texture bg;
	private BitmapFont font;
	int counter = 0;
	boolean draw = true;

	public MenuState(GSM gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		font = new BitmapFont();
		font.setScale(2);
		bg = new Texture("images/herowallpaper.png");
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		counter++;
		if (counter > 60) {
			counter = 0;
			draw = !draw;
		}

		if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
			gsm.set(new PlayState(gsm));
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		sb.draw(bg, 0, 0);
		if (draw)
			font.draw(sb, "- Press Any Key -", Game.WIDTH / 2 - 140, 40);
		sb.end();
	}

}
