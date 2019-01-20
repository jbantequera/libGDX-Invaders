package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Invaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("C:\\Users\\Javier\\Documents\\NetBeansProjects\\libGDX-Invaders\\desktop\\applicationicon.png", Files.FileType.Internal);
		new LwjglApplication(new Invaders(), config);
	}
}