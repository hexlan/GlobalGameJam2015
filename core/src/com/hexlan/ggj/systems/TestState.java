package com.hexlan.ggj.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestState extends State{

	public TestState(GSM gsm){
		super(gsm);
	}

	public void handleInput() {
		
	}

	public void update(float dt) {
		System.out.println("Test Update");
	}

	public void render(SpriteBatch sb) {
		System.out.println("Test Render");
	}
}
