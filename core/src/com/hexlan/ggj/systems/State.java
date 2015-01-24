package com.hexlan.ggj.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

		protected GSM gsm;
		
		protected State(GSM gsm) {
			this.gsm = gsm;
		}
		public abstract void handleInput();
		public abstract void update(float dt);
		public abstract void render(SpriteBatch sb);
}
