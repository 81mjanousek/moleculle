package cz.deejay.molleculle;

import cz.deejay.molleculle.managers.GameManager;

public class GameData {

	public static int score = 0;
	public static int time = 240;
	public static int seconds = time % 60;;
	public static int minutes = time / 60;
	public static long endTime = System.nanoTime();
	public static long runningTime = endTime - GameManager.startTime;

}
