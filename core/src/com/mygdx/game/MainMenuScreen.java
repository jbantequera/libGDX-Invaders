package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
	final Invaders game;
	OrthographicCamera camera;
	Music mainMenuMusic;
	Texture mainMenuImage;
	
	public MainMenuScreen(final Invaders game) {
		this.game = game;

		mainMenuImage = new Texture(Gdx.files.internal("mainmenu.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("mainmenu.mp3"));
		mainMenuMusic.setLooping(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(mainMenuImage, 0, 0);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (touchPos.x >= 238 && touchPos.x <= 561){
				if (touchPos.y >= 16 && touchPos.y <= 88){
					game.setScreen(new GameScreen(game));
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		mainMenuMusic.play();
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
		mainMenuMusic.dispose();
	}
}
