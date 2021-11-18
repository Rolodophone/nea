package io.github.rolodophone.comboking

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import io.github.rolodophone.comboking.component.MoveComponent.MoveAction
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager

/**
 * The minimum distance (in pixels) that the cursor has to be dragged for it to register as a swipe
 */
private const val MIN_SWIPE_DISTANCE = 40f

/**
 * Handles detecting the touch gesture controls on Android.
 */
class TouchControlsGestureListener(
	private val gameEventManager: GameEventManager
): GestureDetector.GestureListener {

	private var touchHoldX = 0f
	private var touchHoldY = 0f

	/**
	 * Returns a customised gesture detector for this listener
	 */
	fun createGestureDetector() = GestureDetector(20f, 0.4f, 1.1f, Integer.MAX_VALUE.toFloat(), this)


	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false
	// to indicate that it should be passed on.

	override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
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
		if (x < touchHoldX - MIN_SWIPE_DISTANCE) {
			touchHoldX = x
			GameEvent.PlayerMoveInput.moveAction = MoveAction.RUN_LEFT
			gameEventManager.trigger(GameEvent.PlayerMoveInput)
			println("Left")
		}
		else if (x > touchHoldX + MIN_SWIPE_DISTANCE) {
			touchHoldX = x
			GameEvent.PlayerMoveInput.moveAction = MoveAction.RUN_RIGHT
			gameEventManager.trigger(GameEvent.PlayerMoveInput)
			println("Right")
		}

		return true
	}

	override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
		GameEvent.PlayerMoveInput.moveAction = MoveAction.STOP
		gameEventManager.trigger(GameEvent.PlayerMoveInput)
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