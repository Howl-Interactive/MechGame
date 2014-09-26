package com.howlinteractive.mech;

public class Environmental extends Object {

	@Override
	Type type() { return Type.NONE; }
	
	Environmental(float x, float y, String file) {
		super(x, y, new Sprite(file));
	}
}
