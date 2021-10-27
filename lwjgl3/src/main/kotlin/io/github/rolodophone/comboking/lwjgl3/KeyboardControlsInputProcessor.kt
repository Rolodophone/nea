package io.github.rolodophone.comboking.lwjgl3


import com.badlogic.gdx.InputProcessor
import io.github.rolodophone.comboking.event.GameEventManager

/**
 * Handles keyboard and mouse events for player controls on PC.
 */
class KeyboardControlsInputProcessor(
	private val gameEventManager: GameEventManager
): InputProcessor {
	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false 
	// to indicate that it should be passed on.

	override fun keyDown(keycode: Int): Boolean {
		return false
	}

	override fun keyUp(keycode: Int): Boolean {
		return false
	}

	override fun keyTyped(character: Char): Boolean {
		return false
	}

	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}

	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		return false
	}

	override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
		return false
	}

	override fun scrolled(amountX: Float, amountY: Float): Boolean {
		return false
	}
}