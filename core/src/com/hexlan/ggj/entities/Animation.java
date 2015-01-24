package com.hexlan.ggj.entities;

import java.awt.image.BufferedImage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	private TextureRegion[] frames;
	private int currentFrame;
	private int numFrames;
	
	private int count;
	private int delay;
	
	private int timesPlayed;
	
	public Animation() {
		timesPlayed = 0;
	}
	
	public void setFrames(TextureRegion[] frames){
		this.frames=frames;
		currentFrame = 0;
		count=0;
		timesPlayed=0;
		delay=2;
		numFrames = frames.length;
	}
	public void setDelay(int i){delay = i;}
	public void setFrame(int i){currentFrame = i;}
	public void setNumFrames(int i){numFrames = i;}
	
	public void update() {
		if(delay == -1) return;
		count++;
		if(count == delay) {
			currentFrame = 0;
			timesPlayed++;
			
		}
	}
	public int getFrame() {return currentFrame;}
	public int getCount() {return count;}
	public TextureRegion getImage() { return frames[currentFrame];}
	public boolean hasPlayedOnce() {return timesPlayed >0;}
	public boolean hasPlayed(int i) {return timesPlayed == i; }
}
