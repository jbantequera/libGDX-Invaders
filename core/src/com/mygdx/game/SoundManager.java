/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author Javier
 */
public class SoundManager {
	
	static Music mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("mainmenu.mp3"));
	static Music failScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("failscreen.mp3"));
	
	static Sound playerExplosionSound = Gdx.audio.newSound(Gdx.files.internal("playerExplosion.wav"));
	static Sound alienExplosionSound = Gdx.audio.newSound(Gdx.files.internal("alienExplosion.wav"));
	static Sound playerShotSound = Gdx.audio.newSound(Gdx.files.internal("playerShot.wav"));
	static Sound alienShotSound = Gdx.audio.newSound(Gdx.files.internal("alienShot.wav"));
	static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("game.mp3"));
	
	public static void playMainMenu(){
		mainMenuMusic.setLooping(true);
		mainMenuMusic.play();
	}
	
	public static void playFailMenu(){
		failScreenMusic.setLooping(true);
		failScreenMusic.play();
	}
	
	public static void playGameMusic(){
		gameMusic.setLooping(true);
		gameMusic.play();
	}
	
	public static void playPlayerExplosion(){
		playerExplosionSound.play();
	}
	
	public static void playAlienExplosion(){
		alienExplosionSound.play();
	}
	
	public static void playPlayerShot(){
		playerShotSound.play();
	}
	
	public static void playAlienShot(){
		alienShotSound.play();
	}
	
	public static void disposeMusic(){
		mainMenuMusic.dispose();
		failScreenMusic.dispose();
		gameMusic.dispose();
	}
	
	public static void disposeSounds(){
		playerExplosionSound.dispose();
		alienExplosionSound.dispose();
		playerShotSound.dispose();
		alienShotSound.dispose();
	}
}
