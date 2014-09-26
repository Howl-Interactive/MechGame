package com.howlinteractive.mech;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	static OrthographicCamera camera;
	
	static Random rand = new Random();
	
	static SpriteBatch sB;
	
	static int width;
	static int height;
	
	static ArrayList<Room> rooms;
	static private int curRoom;
	static Room room;
	static void nextRoom() { changeRoom(curRoom + 1); }
	static void changeRoom(int nextRoom) {
		curRoom = nextRoom;
		room = rooms.get(curRoom);
		Room.p.x = Room.playerStartX;
		Room.p.y = Room.playerStartY;
	}
	
	static boolean inputPanelEnabled = true;
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false);
		sB = new SpriteBatch();
		rooms = new ArrayList<Room>();
		rooms.add(new Room(0, -2));
		changeRoom(0);
		InputPanel.create();
	}
	
	void handleInput() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if(!inputPanelEnabled) { room.onTouch(); }			
			else { InputPanel.onTouch(); }
		}
	}

	void update() {
		room.update();
	}
	
	void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sB.setProjectionMatrix(camera.combined);
		sB.begin();
		room.draw();
		if(inputPanelEnabled) { InputPanel.draw(); }
		sB.end();
	}
	
	@Override
	public void render () {
		handleInput();
		update();
		draw();
	}
	
	static int getXInput() {
		return Gdx.input.getX();
	}
	
	static int getYInput() {
		return Game.height - Gdx.input.getY();
	}
	
	static void gameOver() {
		room.reset();
	}
}