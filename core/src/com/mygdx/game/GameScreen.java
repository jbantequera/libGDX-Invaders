package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class GameScreen implements Screen {
  	static Invaders game;
	
	//Camera
	static OrthographicCamera camera;
	static ArrayList<Entity> entities;
	
	//Logistic
	static int score;
	static int intentos;
	double alienShotCooldown;
	double cooldownModifier;
	
	public GameScreen(final Invaders game) {
		this.game = game;
		
		// LOGISTIC
		this.score = 0;
		this.intentos = 3;
		
		// TEXTURES
		game.font.getData().setScale(2);
		
		// CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// SPAWN ENTITIES
		entities = new ArrayList();
		entities.add(new Player());
		entities.add(new Alien());
		
		for (int i = 0; i < entities.size(); i++){
			entities.get(i).spawn();
		}
	}
	
	private static void newLife(){
		for (int i = 0; i < entities.size(); i++){
			entities.get(i).spawn();
		}
	}

	public void drawEverything(){
		// Tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// Begin a new batch and draw everything
		game.batch.begin();
		// Draws background
		game.batch.draw(backgroundImage, 0, 0);
		// Draws text
		game.font.draw(game.batch, "LIFES: " + intentos, 5, 470);
		game.font.draw(game.batch, "SCORE: " + score, 5, 440);
		// Draws spaceship and aliens
		game.batch.draw(spaceshipImage, spaceship.x, spaceship.y);
		game.batch.draw(alienImage, alien.x, alien.y);
		// Draws
		for (Rectangle shot : alienShots) {
			game.batch.draw(alienShotImage, shot.x, shot.y);
		}
		
		for (Rectangle shot : playerShots) {
			game.batch.draw(playerShotImage, shot.x, shot.y);
		}
		game.batch.end();
	}
	
	@Override
	public void render(float delta) {
		// Set background color
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the camera and draw everything
		camera.update();
		drawEverything();
		
		//Render entities
		
		
		// move the shots, remove any that are beneath the bottom edge of
		// the screen or that hit the spaceship. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iterAlien = alienShots.iterator();
		while (iterAlien.hasNext()) {
			Rectangle shot = iterAlien.next();
			shot.y -= shotSpeed * Gdx.graphics.getDeltaTime();
			if (shot.y + shot.height < 0){
				iterAlien.remove();
			}
			if (shot.overlaps(spaceship)) {
				playerExplosionSound.play();
				iterAlien.remove();
				intentos--;
				
				if (intentos == 0){
					gameMusic.dispose();
					game.setScreen(new FailScreen(game, score));
				}
				else
					newLife();
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
		
		Iterator<Rectangle> iterPlayer = playerShots.iterator();
		while (iterPlayer.hasNext()){
			Rectangle shot = iterPlayer.next();
			shot.y += shotSpeed * Gdx.graphics.getDeltaTime();
			if (shot.y > 800)
				iterPlayer.remove();
			
			//check if we hit the alien
			if (shot.overlaps(alien)) {
				alienExplosionSound.play();
				iterPlayer.remove();
				score++;
				spawnAlien();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		SoundManager.playGameMusic();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	public static void goToFailScreen() {
		game.setScreen(new FailScreen(game, score));
	}
	
}
