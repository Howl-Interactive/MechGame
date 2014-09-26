package com.howlinteractive.mech;

import com.badlogic.gdx.graphics.Texture;

public class InputPanel {

	enum INPUT_TYPE { MOVE, SHOOT }
	
	private static float lx = 50, ly = 50, rx = Game.width - 50, ry = 50, ls = 50, rs = 50;
	
	private static ControlStick leftStick, rightStick;
	
	static void create(INPUT_TYPE lInputType, INPUT_TYPE rInputType, String lStick, String rStick) {
		leftStick = new ControlStick(lx, ly, ls, lInputType, lStick);
		rightStick = new ControlStick(rx, ry, rs, rInputType, rStick);
	}

	static void create() {
		create(INPUT_TYPE.MOVE, INPUT_TYPE.SHOOT, "controlstick.png", "controlstick.png");
	}
	
	static void draw() {
		leftStick.draw();
		rightStick.draw();
	}
	
	static void onTouch() {
		leftStick.onTouch();
		rightStick.onTouch();
	}
	
	private static class ControlStick {
		
		INPUT_TYPE inputType;
		
		float x, y, s;
		Texture texture;
		
		ControlStick(float x, float y, float s, INPUT_TYPE inputType, String texture) {
			this.x = x;
			this.y = y;
			this.s = s;
			this.inputType = inputType;
			this.texture = new Texture(texture);
		}
		
		void draw() {
			Game.sB.draw(texture, x - s / 2, y - s / 2, s, s);
		}
		
		void onTouch() {
			if(isPressed()) {
				float angle = getAngle();
				switch(inputType) {
				case MOVE:
					Room.p.setVel(angle, true);
					break;
				case SHOOT:
					Room.p.shoot(angle);
					break;
				}
			}
		}
		
		boolean isPressed() {
			return Math.sqrt(Math.pow(Game.getXInput() - x, 2) + Math.pow(Game.getYInput() - y, 2)) < s / 2;
		}
		
		float getAngle() {
			return (float)Math.atan2(Game.getYInput() - y, Game.getXInput() - x);
		}
	}
}