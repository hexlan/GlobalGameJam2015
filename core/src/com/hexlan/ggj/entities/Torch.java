package com.hexlan.ggj.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Torch extends Entity {
	public Animation animation;

	public Torch() {
		super();
	}

	public Animation getAnimation() {
		return animation;
	}

	public TextureRegion getImage() {
		return animation.getImage();
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void update(float dt) {
		animation.update();
	}
}
