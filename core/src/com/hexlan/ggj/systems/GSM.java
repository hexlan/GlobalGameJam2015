package com.hexlan.ggj.systems;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexlan.ggj.gamestates.GameState;
import com.hexlan.ggj.gamestates.PlayState;

public class GSM {

	private Stack<GameState> states;
	
	public GSM() {
		states = new Stack<GameState>();
		states.push(new PlayState(this));
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
