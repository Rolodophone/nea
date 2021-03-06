package io.github.rolodophone.comboking

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.event.PlayerInput
import kotlin.math.abs

/**
 * The minimum distance (in pixels) that the cursor has to be dragged for it to register as a swipe
 */
private const val MIN_SWIPE_DISTANCE = 40f

/**
 * Handles detecting the touch gesture controls on Android.
 */
class TouchControlsGestureListener(
	private val gameEventManager: GameEventManager,
	private val screenWidth: Int,
	private val screenHeight: Int
): GestureDetector.GestureListener {

	private var touchHoldXMove = 0f
	private var touchHoldYMove = 0f
	private var touchHoldXCombat = 0f

	private var usingLeftControls = false
	private var usingRightControls = false

	/**
	 * Returns a customised gesture detector for this listener
	 */
	fun createGestureDetector() = GestureDetector(20f, 0.4f, 1.1f, Integer.MAX_VALUE.toFloat(), this)


	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false
	// to indicate that it should be passed on.

	override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
		if (x < screenWidth/2f) {
			touchHoldXMove = x
			touchHoldYMove = y
			usingLeftControls = true
		}
		else {
			touchHoldXCombat = x
			usingRightControls = true
		}

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
		//LHS controls
		if (usingLeftControls) {
			val distX = abs(x - touchHoldXMove)
			val dispY = touchHoldYMove - y
			val distY = abs(dispY)

			//using doors
			if (distY > MIN_SWIPE_DISTANCE && distY > distX) {
				if (dispY < 0) { //swipe down
					touchHoldXMove = x
					touchHoldYMove = y
					GameEvent.PlayerInputEvent.input = PlayerInput.DOWN_STAIRS
					gameEventManager.trigger(GameEvent.PlayerInputEvent)
				}
				else { //swipe up
					touchHoldXMove = x
					touchHoldYMove = y
					GameEvent.PlayerInputEvent.input = PlayerInput.UP_STAIRS
					gameEventManager.trigger(GameEvent.PlayerInputEvent)
				}
			}
			else if (x < touchHoldXMove - MIN_SWIPE_DISTANCE) {
				touchHoldXMove = x
				touchHoldYMove = y
				GameEvent.PlayerInputEvent.input = PlayerInput.LEFT
				gameEventManager.trigger(GameEvent.PlayerInputEvent)
			}
			else if (x > touchHoldXMove + MIN_SWIPE_DISTANCE) {
				touchHoldXMove = x
				touchHoldYMove = y
				GameEvent.PlayerInputEvent.input = PlayerInput.RIGHT
				gameEventManager.trigger(GameEvent.PlayerInputEvent)
			}
		}
		// RHS controls
		else {
			if (x < touchHoldXCombat - MIN_SWIPE_DISTANCE) {
				touchHoldXCombat = x
				GameEvent.PlayerInputEvent.input = PlayerInput.PUNCH_LEFT
				gameEventManager.trigger(GameEvent.PlayerInputEvent)
			}
			else if (x > touchHoldXCombat + MIN_SWIPE_DISTANCE) {
				touchHoldXCombat = x
				GameEvent.PlayerInputEvent.input = PlayerInput.PUNCH_RIGHT
				gameEventManager.trigger(GameEvent.PlayerInputEvent)
			}
		}

		return true
	}

	override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
		if (usingLeftControls) {
			GameEvent.PlayerInputEvent.input = PlayerInput.STOP
			gameEventManager.trigger(GameEvent.PlayerInputEvent)
		}

		usingLeftControls = false
		usingRightControls = false

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