package com.howlinteractive.mech;

import java.util.ArrayList;
import java.util.Collections;

public class Room {
	
	static float playerStartX = Game.width / 2, playerStartY = 200;
	static Player p;
	
	ArrayList<Object> objs;
	
	float scrollX, scrollY;
	
	Room(float scrollX, float scrollY) {
		this.scrollX = scrollX;
		this.scrollY = scrollY;
		objs = new ArrayList<Object>();
		if(p == null) { p = new Player(playerStartX, playerStartY); }
		objs.add(p);
		initializeEmptyRoom();
	}

	Room() {
		this(0, 0);
	}

	void reset() {
		objs = new ArrayList<Object>();
		p.x = playerStartX;
		p.y = playerStartY;
		p.setDir(0, true);
		objs.add(p);
		initializeEmptyRoom();
	}
	
	void update() {
		createObjects();
		for(Object obj : objs) {
			if(obj.y < -Game.height - obj.h / 2) { obj.isAlive = false; }
			if(obj.isAlive) { obj.update(); }
		}
		for(int i = objs.size() - 1; i >= 0; i--) {
			if(!objs.get(i).isAlive) { objs.remove(i); }
		}
		scroll();
	}
	
	void scroll() {
		p.negateScroll();
		for(Object obj : objs) {
			if(obj instanceof Bullet) {
				obj.negateScroll();
			}
		}
		for(Object obj : objs) {
			obj.x += scrollX;
			obj.y += scrollY;
		}
		LevelCreator.scrollCounter += Math.abs(scrollY);
	}
	
	void draw() {
		rearrangeByDepth();
		for(Object obj : objs) {
			obj.draw();
		}
	}

	void createObjects() {
		ArrayList<Object> section = LevelCreator.createSection(true);
		for(Object obj : section) {
			objs.add(obj);
		}
	}
	
	void initializeEmptyRoom() {
		ArrayList<Object> section = LevelCreator.initializeEmptyRoom();
		for(Object obj : section) {
			objs.add(obj);
		}
	}
	
	void rearrangeByDepth() {
		Collections.sort(objs);
	}
	
	void onTouch() {
		if(Game.getXInput() < p.x - p.speed) {
			p.velX = -p.speed;
		}
		else if(Game.getXInput() > p.x + p.speed) {
			p.velX = p.speed;
		}
		if(Game.getYInput() < p.y - p.speed) {
			p.velY = -p.speed;
		}
		else if(Game.getYInput() > p.y + p.speed) {
			p.velY = p.speed;
		}
		p.adjustRotation();
	}
	
	static void drawLine(float x1, float y1, float x2, float y2) {
		//TODO
	}
}
