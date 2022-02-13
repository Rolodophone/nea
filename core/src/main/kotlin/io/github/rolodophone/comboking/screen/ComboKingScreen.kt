package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.Screen
import io.github.rolodophone.comboking.ComboKing
import ktx.app.KtxScreen

private const val MAX_DELTA_TIME = 1/10f

/**
 * Base class for all [KtxScreen]s in the game.
 */
abstract class ComboKingScreen(val game: ComboKing): KtxScreen {
	val batch = game.batch
	val viewport = game.viewport
	val engine = game.engine
	val textures = game.comboKingTextures
	val fonts = game.comboKingFonts
	val music = game.comboKingMusic
	val sounds = game.comboKingSounds
	val gameEventManager = game.gameEventManager

	var subScreen: Screen? = null

	override fun dispose() {
		subScreen?.dispose()
	}

	override fun hide() {
		subScreen?.hide()
	}

	override fun pause() {
		subScreen?.pause()
	}

	override fun render(delta: Float) {
		subScreen.let { subScreen ->
			if (subScreen == null) { //only render if this has no sub-screens so that we don't render twice in one frame
				val newDeltaTime = if (delta > MAX_DELTA_TIME) MAX_DELTA_TIME else delta
				engine.update(newDeltaTime)
			}
			else {
				subScreen.render(delta)
			}
		}
	}

	override fun resize(width: Int, height: Int) {
		subScreen.let { subScreen ->
			if (subScreen == null) { //only resize if this has no sub-screens so that we don't resize twice in one frame
				viewport.update(width, height, false)
			}
			else {
				subScreen.resize(width, height)
			}
		}
	}

	override fun resume() {
		subScreen?.resume()
	}

	override fun show() {
		subScreen?.show()
	}
}