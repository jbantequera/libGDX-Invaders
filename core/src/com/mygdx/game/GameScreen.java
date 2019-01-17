package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Random;

public class GameScreen implements Screen {
  	final Drop game;

	//Textures
	Texture spaceshipImage;
	Texture playerShotImage;
	Texture alienImage;
	Texture alienShotImage;
	
	//Sound
	Sound dropSound;
	Music rainMusic;
	
	//Camera
	OrthographicCamera camera;
	
	//Player
	Rectangle spaceship;
	Array<Rectangle> playerShots;
	long lastPlayerShotTime;
	static int playerSpeed = 350;
	static int shotSpeed = 250;

	//IA
	Rectangle alien;
	int alienDirection;
	Array<Rectangle> alienShots;
	long lastAlienShotTime;
	static int alienSpeed = 200;
	
	//Logistic
	int score;
	int intentos;

	public GameScreen(final Drop game) {
		this.game = game;
		
		// TEXTURES
		spaceshipImage = new Texture(Gdx.files.internal("spaceship.png")); //99x75
		playerShotImage = new Texture (Gdx.files.internal("playerShot.png")); //9x37
		alienImage = new Texture(Gdx.files.internal("alien.png")); //104x84
		alienShotImage = new Texture(Gdx.files.internal("alienShot.png")); //9x37 

		// SOUND
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		// CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// SPAWN PLAYER AND IA
		spawnSpaceship();
		spawnAlien();
		
		// LOGISTIC
		this.score = 0;
		this.intentos = 3;
	}

	public GameScreen(final Drop game, int score, int intentos){
		this(game);
		this.score = score;
		this.intentos = intentos;
	}
	
	private void spawnSpaceship() {
		spaceship = new Rectangle();
		spaceship.width = 99;
		spaceship.height = 75;

		spaceship.x = 800 / 2 - spaceship.width / 2; // center the spaceship horizontally
		spaceship.y = 10; // bottom left corner of the spaceship is 20 pixels above
						// the bottom screen edge

		playerShots = new Array<Rectangle>();
	}
	
	private void spawnAlien() {
		// create a Rectangle to represent the alien
		alien = new Rectangle();
		alien.width = 104;
		alien.height = 84;
		
		alien.x = 800 / 2 - alien.width / 2;
		alien.y = 400;
		alienDirection = 1;
		
		// create the shots arrays and spawn the first shot
		alienShots = new Array<Rectangle>();
		spawnAlienShot();
	}
	
	private void spawnAlienShot() {
		Rectangle shot = new Rectangle();
		shot.width = 9;
		shot.height = 37;
		
		shot.x = alien.x + (alien.width / 2) - (shot.width / 2);
		shot.y = alien.y - shot.height;
		alienShots.add(shot);
		lastAlienShotTime = TimeUtils.nanoTime();
	}

	private void spawnPlayerShot() {
		if ((TimeUtils.nanoTime() - lastPlayerShotTime > 500000000) || playerShots.isEmpty()){
			Rectangle shot = new Rectangle();
			shot.width = 9;
			shot.height = 37;
			
			shot.x = spaceship.x + (spaceship.width / 2) - (shot.width / 2);
			shot.y = spaceship.y + spaceship.height;
			playerShots.add(shot);
			lastPlayerShotTime = TimeUtils.nanoTime();
		}
	}
	
	public void drawEverything(){
		// Tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// Begin a new batch and draw everything
		game.batch.begin();
		// Draws text
		game.font.draw(game.batch, "LIFES: " + intentos, 0, 480);
		game.font.draw(game.batch, "SCORE: " + score, 0, 460);
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
		
		// Process user input
		// Movement
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			spaceship.x = touchPos.x - spaceship.width / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spaceship.x -= playerSpeed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			spaceship.x += playerSpeed * Gdx.graphics.getDeltaTime();
		
		// Shooting
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()){
			spawnPlayerShot();
		}
		
		// Make sure the spaceship stays within the screen bounds
		if (spaceship.x < 0)
			spaceship.x = 0;
		if (spaceship.x > 800 - spaceship.width)
			spaceship.x = 800 - spaceship.width;
		
		// check if we need to create a new shot
		if (TimeUtils.nanoTime() - lastAlienShotTime > 1200000000)
			spawnAlienShot();

		//move the alien
		alien.x += alienDirection * alienSpeed * Gdx.graphics.getDeltaTime();
		if (alien.x < 0){
			alien.y = alien.y - (alien.height + 10);
			alienDirection = 1;
		}
		if (alien.x >= 800 - alien.width){
			alien.y = alien.y - (alien.width + 10);
			alienDirection = -1;
		}
		
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
				dropSound.play();
				iterAlien.remove();
				intentos--;
				
				if (intentos == 0)
					game.setScreen(new FailScreen(game, score));
				else
					game.setScreen(new GameScreen(game, score, intentos));
			}
		}
		
		if (alien.y <= (spaceship.height + 10)){
			intentos--;
			game.setScreen(new GameScreen(game, score, intentos));
		}
		
		Iterator<Rectangle> iterPlayer = playerShots.iterator();
		while (iterPlayer.hasNext()){
			Rectangle shot = iterPlayer.next();
			shot.y += shotSpeed * Gdx.graphics.getDeltaTime();
			if (shot.y > 800)
				iterPlayer.remove();
			
			//check if we hit the alien
			if (shot.overlaps(alien)) {
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
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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
		alienShotImage.dispose();
		spaceshipImage.dispose();
		alienImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

}
