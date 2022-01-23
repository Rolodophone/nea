package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.EntitySystem

class TimeSys : EntitySystem(-100) {

	/**
	 * The number of seconds for which the app has running.
	 *
	 * On PC this continues increasing when the game is minimised, whereas on Android it only increases when the game
	 * is in the foreground. On both platforms, it will never increase by more than
	 * [io.github.rolodophone.comboking.screen.MAX_DELTA_TIME], so that the game state doesn't change by a huge
	 * amount after a lag spike, and also so that the game behaves properly when being debugged.
	 */
	var appUptime = 0f
		private set

	/**
	 * The number of seconds for which the game has been running, or -1 if the game isn't running
	 *
	 * On PC this continues increasing when the game is minimised, whereas on Android it only increases when the game
	 * is in the foreground. On both platforms, it will never increase by more than
	 * [io.github.rolodophone.comboking.screen.MAX_DELTA_TIME], so that the game state doesn't change by a huge
	 * amount after a lag spike, and also so that the game behaves properly when being debugged.
	 */
	var gameUptime = -1f

	override fun update(deltaTime: Float) {
		appUptime += deltaTime

		//increment gameUptime if game is running
		if (gameUptime != -1f) gameUptime += deltaTime
	}
}