/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author Javier
 */
public class Player extends Entity{

	long lastPlayerShotTime;
	static Array<Rectangle> shots;
	static int speed;
	
	@Override
	public void spawn() {
		hitbox = new Rectangle();
		hitbox.width = 99;
		hitbox.height = 75;

		hitbox.x = 800 / 2 - hitbox.width / 2; // center the spaceship horizontally
		hitbox.y = 10; // bottom left corner of the spaceship is 20 pixels above
						// the bottom screen edge

		shots = new Array<Rectangle>();
	}

	@Override
	public void spawnShot(){
		if ((TimeUtils.nanoTime() - lastPlayerShotTime > 500000000) || shots.isEmpty()){
			Rectangle shot = new Rectangle();
			shot.width = 9;
			shot.height = 37;
			
			shot.x = hitbox.x + (hitbox.width / 2) - (shot.width / 2);
			shot.y = hitbox.y + hitbox.height;
			shots.add(shot);
			SoundManager.playPlayerShot();
			lastPlayerShotTime = TimeUtils.nanoTime();
		}
	}
	
	@Override
	public void render(float delta) {
	if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			GameScreen.camera.unproject(touchPos);
			hitbox.x = touchPos.x - hitbox.width / 2;
		}
	
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			hitbox.x -= speed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			hitbox.x += speed * Gdx.graphics.getDeltaTime();
		
		// Shooting
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()){
			spawnShot();
		}
		
		// Make sure the spaceship stays within the screen bounds
		if (hitbox.x < 0)
			hitbox.x = 0;
		if (hitbox.x > 800 - hitbox.width)
			hitbox.x = 800 - hitbox.width;
	}

	@Override
	public void dispose() {}
	
}
