package com.howlinteractive.mech;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.graphics.Texture;

public class Sprite {

	private static Hashtable<String, Texture> loadedTextures = new Hashtable<String, Texture>();
	
	private ArrayList<Texture> textures;
	private int curTexture;
	private Texture texture;
	private void changeTexture(int newTexture) { curTexture = newTexture; texture = textures.get(curTexture); }
	
	int width() { return texture.getWidth(); }
	int height() { return texture.getHeight(); }
	
	private boolean cycling;
	void toggleAnimation() { cycling = !cycling; }
	
	float rotation = 0;
	
	float depth;
	
	Sprite(String[] files, boolean cycling, int depth) {
		this.cycling = cycling;
		this.depth = depth;
		textures = new ArrayList<Texture>();
		for(String file : files) {
			textures.add(loadTexture(file));
		}
		changeTexture(0);
	}

	Sprite(String file, int depth) {
		this(new String[] { file }, false, depth);
	}
	
	Sprite(String file) {
		this(new String[] { file }, false, 0);
	}
	
	void draw(float x, float y, int w, int h) {
		Game.sB.draw(texture, x, y, w / 2, h / 2, w, h, 1, 1, (float)Math.toDegrees(-rotation), 0, 0, w, h, false, false);
		if(cycling) { changeTexture((curTexture + 1) % textures.size()); }
	}
	
	static Texture loadTexture(String file) {
		Texture loaded = loadedTextures.get(file);
		if(loaded == null) {
			Texture t = new Texture(file);
			loadedTextures.put(file, t);
			return t;
		}
		return loaded;
	}
}