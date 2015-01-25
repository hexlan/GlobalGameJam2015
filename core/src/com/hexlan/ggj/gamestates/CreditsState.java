package com.hexlan.ggj.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.Game;
import com.hexlan.ggj.systems.GSM;

public class CreditsState extends GameState {

	int musicDelay = 0;
	
	Texture placeholder;
	Texture reyer, noot, jamie, cody, bryan;
	BitmapFont font;
	int y = 0;
	
	public CreditsState(GSM gsm) {
		super(gsm);
		
		placeholder = new Texture("images/team600x400.png");
		
		reyer = new Texture("images/reyer.png");
		noot = new Texture("images/noot.png");
		jamie = new Texture("images/jamie.png");
		cody = new Texture("images/cody.png");
		bryan = new Texture("images/bryan.png");
		
		font = new BitmapFont();
		y = -120;
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
		if(y < 1850) y++;
		else
		{
			if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) gsm.set(new MenuState(gsm));
		}
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		font.setScale(3f);
		font.draw(sb, "Dark Moon Gauntlet", 50, y+120);
		
		sb.draw(reyer, 50, y - 200);
		font.setScale(2.5f);
		font.draw(sb, "Reyer Swengel", 300, y-20);
		font.setScale(1.75f);
		font.draw(sb, "Programming", 310, y - 80);
		font.draw(sb, "Tile Art", 310, y - 120);
		
		sb.draw(jamie, 50, y - 200 - 250);
		font.setScale(2.5f);
		font.draw(sb, "Jamie Hess", 300, y - 20 -250);
		font.setScale(1.75f);
		font.draw(sb, "Programming", 310, y - 80 - 250);
		
		sb.draw(cody, 50, y - 200 - 500);
		font.setScale(2.5f);
		font.draw(sb, "Cody Kluska", 300, y - 20 - 500);
		font.setScale(1.75f);
		font.draw(sb, "Game Design", 310, y - 80 - 500);
		font.draw(sb, "Social Media", 310, y - 120 - 500);
		
		sb.draw(noot, 50, y - 200 - 750);
		font.setScale(2.5f);
		font.draw(sb, "Noot Vue", 300, y - 20 - 750);
		font.setScale(1.75f);
		font.draw(sb, "Concept Art", 310, y - 80 - 750);
		font.draw(sb, "Spriting", 310, y - 120 - 750);
		
		sb.draw(bryan, 50, y - 200 - 1000);
		font.setScale(2.5f);
		font.draw(sb, "Bryan Schumann", 300, y - 20 - 1000);
		font.setScale(1.75f);
		font.draw(sb, "Game Music", 310, y - 80 - 1000);
		font.draw(sb, "Sound Effects", 310, y - 120 - 1000);
		font.draw(sb, "http://www.bryanschumann.com", 310, y - 160 - 1000);
		
		sb.draw(placeholder, Game.WIDTH/2 - placeholder.getWidth()/2, y - 1750);
		
		sb.end();
		
		System.out.println("Y: "+y);
	}

}
