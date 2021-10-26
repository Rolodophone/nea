package io.github.rolodophone.comboking

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager

/**
 * Handles detecting the touch gesture controls
 */
class TouchControlsGestureListener(
	private val gameEventManager: GameEventManager
): GestureDetector.GestureListener {
	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false
	// to indicate that it should be passed on.

	private var touchDownX = 0f
	private var touchDownY = 0f

	override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
		touchDownX = x
		touchDownY = y
		return false
	}

	override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
		return false
	}

	override fun longPress(x: Float, y: Float): Boolean {
		return false
	}

	override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
		return false
	}

	override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
		if (x < touchDownX) {
			gameEventManager.trigger(GameEvent.PlayerMoveInput.RunLeft)
		}
		else {
			gameEventManager.trigger(GameEvent.PlayerMoveInput.RunRight)
		}

		return true
	}

	override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
		gameEventManager.trigger(GameEvent.PlayerMoveInput.Stop)
		return false
	}

	override fun zoom(initialDistance: Float, distance: Float): Boolean {
		return false
	}

	override fun pinch(
		initialPointer1: Vector2?,
		initialPointer2: Vector2?,
		pointer1: Vector2?,
		pointer2: Vector2?
	): Boolean {
		return false
	}

	override fun pinchStop() {
	}
}