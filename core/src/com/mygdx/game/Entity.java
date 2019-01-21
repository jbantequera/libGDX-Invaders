/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Javier
 */
public abstract class Entity {
	protected Rectangle hitbox;
	protected Array<Rectangle> shots;
	
	public abstract void spawn();
	public abstract void spawnShot();
	
	public abstract void render(float delta);
	public abstract void dispose();
}
