package com.hexlan.ggj.systems;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.gamestates.CreditsState;
import com.hexlan.ggj.gamestates.GameState;

public class GSM {

	private Stack<GameState> states;
	
	public static Music mainMusic;
	public static Music fastMusic;
	public static Sound fastIntro;
	public static Sound win;
	
	public static boolean shaking = false;
	
	
	public GSM() {
		states = new Stack<GameState>();
		states.push(new MenuState(this));
		//states.push(new CreditsState(this));
		
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/MainLoop.wav"));
		fastMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/fast.wav"));
		fastIntro = Gdx.audio.newSound(Gdx.files.internal("sound/alarm.wav"));
		win = Gdx.audio.newSound(Gdx.files.internal("sound/win.wav"));
		mainMusic.setLooping(true);
		mainMusic.play();
	}
	
	public static void switchSongs()
	{
		mainMusic.stop();
		fastIntro.play();
		fastMusic.setLooping(true);
		fastMusic.play();
		shaking = true;
	}
	
	public static void creditMusic()
	{
		mainMusic.setLooping(true);
		
		mainMusic.play();
	}
	
	public static void playVictory()
	{
		fastMusic.stop();
		win.play();
	}
	
	public void push(GameState s) {
		states.push(s);
	}
	public void pop() {
		states.pop();
	}
	public void set(GameState s) {
		states.pop();
		states.push(s);
	}
	public void update(float dt){
		states.peek().handleInput();
		states.peek().update(dt);
	}
	
	public void render(SpriteBatch sb){
		states.peek().render(sb);
	}
}
