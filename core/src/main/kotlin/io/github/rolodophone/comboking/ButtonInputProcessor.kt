package io.github.rolodophone.comboking

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.component.ButtonComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.unprojectX
import io.github.rolodophone.comboking.util.unprojectY
import ktx.ashley.EngineEntity
import ktx.ashley.allOf

/**
 * Handles touch and key events for buttons
 */
class ButtonInputProcessor(
	private val engine: Engine,
	private val viewport: Viewport
): InputProcessor {
	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false 
	// to indicate that it should be passed on.

	private var buttonPressed: Entity? = null

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
		// check if a button was pressed down
		val buttons = engine.getEntitiesFor(allOf(ButtonComponent::class, TransformComponent::class).get())

		for (buttonEntity in buttons) {
			val transformComp = buttonEntity.getNotNull(TransformComponent.mapper)

			if (transformComp.rect.contains(
					viewport.unprojectX(screenX.toFloat()),
					viewport.unprojectY(screenY.toFloat())
			)) {
				buttonPressed = buttonEntity
				return true
			}
		}

		return false
	}

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		buttonPressed?.let { buttonEntity ->

			// check if the button that was pressed was released
			val transformComp = buttonEntity.getNotNull(TransformComponent.mapper)

			if (transformComp.rect.contains(
					viewport.unprojectX(screenX.toFloat()),
					viewport.unprojectY(screenY.toFloat())
			)) {
				//execute button action
				val buttonComp = buttonEntity.getNotNull(ButtonComponent.mapper)
				buttonComp.onPress()
				buttonPressed = null
				return true
			}
		}

		buttonPressed = null
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