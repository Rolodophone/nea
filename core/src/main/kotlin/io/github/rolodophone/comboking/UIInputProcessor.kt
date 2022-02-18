package io.github.rolodophone.comboking

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.comp.ButtonComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.SliderComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.unprojectX
import io.github.rolodophone.comboking.util.unprojectY
import ktx.ashley.allOf
import ktx.ashley.contains
import ktx.ashley.get
import ktx.ashley.oneOf

/**
 * Handles touch and key events for UI elements.
 */
class UIInputProcessor(
	private val engine: Engine,
	private val viewport: Viewport
): InputProcessor {
	// Each method returns true to indicate that the event shouldn't be passed on to other input processors or false 
	// to indicate that it should be passed on.

	private var elementPressed: Entity? = null

	override fun keyDown(keycode: Int): Boolean {
		//toggle borderless fullscreen when F11 pressed
		if (keycode == Input.Keys.F11) {
			if (Gdx.graphics.isFullscreen) {
				Gdx.graphics.setWindowedMode(WORLD_WIDTH*3, WORLD_HEIGHT*3)
			}
			else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
			}
		}

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
		val elements = engine.getEntitiesFor(
			allOf(TransformComp::class)
			.oneOf(ButtonComp::class, SliderComp::class)
		.get())

		for (element in elements) {
			val transformComp = element.getNotNull(TransformComp.mapper)

			if (transformComp.contains(
					viewport.unprojectX(screenX.toFloat()),
					viewport.unprojectY(screenY.toFloat())
			)) {
				elementPressed = element
				return true
			}
		}

		return false
	}

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		elementPressed?.let { element ->
			if (element.contains(ButtonComp.mapper)) { //only concerned about touch up events on buttons, not sliders

				// check if the button that was pressed was released
				val transformComp = element.getNotNull(TransformComp.mapper)

				if (transformComp.contains(
						viewport.unprojectX(screenX.toFloat()),
						viewport.unprojectY(screenY.toFloat())
					)
				) {
					//execute button action
					val buttonComp = element.getNotNull(ButtonComp.mapper)
					buttonComp.onPress()
					elementPressed = null
					return true
				}
			}
		}

		elementPressed = null
		return false
	}

	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		elementPressed?.let { element ->
			if (element.contains(SliderComp.mapper)) { //only concerned about touch drag events on sliders, not buttons

				val transformComp = element.getNotNull(TransformComp.mapper)
				val graphicsComp = element[GraphicsComp.mapper]
				val sliderComp = element.getNotNull(SliderComp.mapper)
				val worldX = viewport.unprojectX(screenX.toFloat())

				when {
					worldX < transformComp.left + transformComp.width * (1/8f) -> {
						if (sliderComp.progress != 0f) {
							sliderComp.progress = 0f
							graphicsComp?.textureRegion = sliderComp.textures[0]
							sliderComp.onChange(sliderComp.progress)
						}
					}
					worldX < transformComp.left + transformComp.width * (3/8f) -> {
						if (sliderComp.progress != 0.25f) {
							sliderComp.progress = 0.25f
							graphicsComp?.textureRegion = sliderComp.textures[1]
							sliderComp.onChange(sliderComp.progress)
						}
					}
					worldX < transformComp.left + transformComp.width * (5/8f) -> {
						if (sliderComp.progress != 0.5f) {
							sliderComp.progress = 0.5f
							graphicsComp?.textureRegion = sliderComp.textures[2]
							sliderComp.onChange(sliderComp.progress)
						}
					}
					worldX < transformComp.left + transformComp.width * (7/8f) -> {
						if (sliderComp.progress != 0.75f) {
							sliderComp.progress = 0.75f
							graphicsComp?.textureRegion = sliderComp.textures[3]
							sliderComp.onChange(sliderComp.progress)
						}
					}
					else -> {
						if (sliderComp.progress != 1f) {
							sliderComp.progress = 1f
							graphicsComp?.textureRegion = sliderComp.textures[4]
							sliderComp.onChange(sliderComp.progress)
						}
					}
				}
			}

			return true
		}

		return false
	}

	override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
		return false
	}

	override fun scrolled(amountX: Float, amountY: Float): Boolean {
		return false
	}
}