package tech.harmless.mc.whatwasthat.config;

public class WhatConfig {
	// public static final int = ;

	/**
	 * How long until thing should attempt an action or lock onto a player. Default
	 * is 5 seconds.
	 */
	public static final int ticksPerAction = 100;

	/**
	 * The minimum amount of time thing will lock onto a player for. Default is 1
	 * minute.
	 */
	public static final int ticksMinBother = 1200;
	/**
	 * The maximum amount of time thing will lock onto a player for. Default is 15
	 * minutes.
	 */
	public static final int ticksMaxBother = 18000;

	/**
	 * How much more likely thing is to take an action or lock onto when someone has
	 * traveled to the nether.
	 */
	public static final int angerNether = 3;
	/**
	 * How much more likely thing is to take an action or lock onto when someone has
	 * traveled to the end.
	 */
	public static final int angerEnd = 5;

	/**
	 * The chance for thing to think about bothering a player. 1/25
	 */
	public static final int chanceThinkBother = 25;
	/**
	 * The chance for thing to take an action, when in a cave. 1/1000
	 */
	public static final int chanceInCave = 1000;
	/**
	 * The chance for thing to take an action, when outside. 1/3000
	 */
	public static final int chanceOutsideCave = 3000;
}
