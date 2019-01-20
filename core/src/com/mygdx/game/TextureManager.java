/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Javier
 */
public class TextureManager {
	
	static Texture mainMenuImage = new Texture(Gdx.files.internal("mainmenu.png"));
	static Texture failScreenImage = new Texture(Gdx.files.internal("failmenu.png"));
		
	static Texture spaceshipImage = new Texture(Gdx.files.internal("spaceship.png"));
	static Texture playerShotImage = new Texture (Gdx.files.internal("playerShot.png"));
	static Texture alienImage = new Texture(Gdx.files.internal("alien.png"));
	static Texture alienShotImage = new Texture(Gdx.files.internal("alienShot.png")); 
	static Texture backgroundImage = new Texture(Gdx.files.internal("gamebackground.png"));
	
}
