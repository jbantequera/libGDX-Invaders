/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Javier
 */
public class Alien extends Entity{

	int direction;
	int speed;
	int shotSpeed;
	int shotCooldown;
	double lastShotTime;
	static int numberOfAliens = 0;
	static Random dado = new Random();
	
	@Override 
	public void spawn(){
		numberOfAliens++;
		
		hitbox = new Rectangle();
		hitbox.width = 104;
		hitbox.height = 84;
		
		hitbox.x = dado.nextInt((int) (801 - hitbox.width));
		hitbox.y = 390;
		direction = 1;
		
		// increments the speed of the alien everytime we kill 2 aliens
		if (numberOfAliens % 2 == 0){
			speed *= 1.1;
			Player.speed *= 1.05;
			shotSpeed *= 1.1;
			shotCooldown *= 0.5;
		}
		
		// create the shots arrays and spawn the first shot
		shotCooldown = (dado.nextInt(2) + 1) * shotCooldown;
		Player.shots = new Array<Rectangle>();
		shots = new Array<Rectangle>();
		lastShotTime = 0;
	}
	
	@Override
	public void spawnShot() {
		
	}
	
	@Override
	public void render(float delta) {
		if (lastShotTime >= shotCooldown){
			spawnShot();
			lastShotTime = 0;
		} else {
			lastShotTime += delta;
		}
		
		hitbox.x += direction * speed * Gdx.graphics.getDeltaTime();
		if (hitbox.x < 0){
			direction = 1;
		}
		if (hitbox.x >= 800 - hitbox.width){
			direction = -1;
		}
	}

	public void renderShots(float delta) {
		Iterator<Rectangle> iterAlien = shots.iterator();
		
		while (iterAlien.hasNext()) {
			Rectangle shot = iterAlien.next();
			shot.y -= shotSpeed * Gdx.graphics.getDeltaTime();
			if (shot.y + shot.height < 0){
				iterAlien.remove();
			}
			
			if (shot.overlaps(Player.hitbox)) {
				SoundManager.playPlayerExplosion();
				iterAlien.remove();
				GameScreen.intentos--;
				
				if (GameScreen.intentos == 0){
					SoundManager.disposeMusic();
					GameScreen.goToFailScreen();
				}
				else
					GameScreen.newLife();
			}
		}
		
		if (alien.y <= (spaceship.height + 10)){
			intentos--;
			
			if (intentos == 0){
					gameMusic.dispose();
					game.setScreen(new FailScreen(game, score));
			} else 
				newLife();
		}
	}
	
	@Override
	public void dispose() {
	}
	
}
