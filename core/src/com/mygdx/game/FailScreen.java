/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Javier
 */
public class FailScreen implements Screen{

	final Invaders game;
	OrthographicCamera camera;
	Music failScreenMusic;
	Texture failScreenImage;
	int score;
	
	public FailScreen (final Invaders game, int score){
		this.game = game;
		this.score = score;
		
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		game.font.getData().setScale(3);
		failScreenImage = new Texture(Gdx.files.internal("failmenu.png"));
		failScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("failscreen.mp3"));
		failScreenMusic.setLooping(true);
	}
	
	@Override
	public void show() {
		failScreenMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(failScreenImage, 0, 0);
		game.font.draw(game.batch, String.valueOf(score), 403, 265);
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
				
				if (touchPos.y >= 105 && touchPos.y <= 303){
					game.setScreen(new MainMenuScreen(game));
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		failScreenMusic.dispose();
	}
	
}
