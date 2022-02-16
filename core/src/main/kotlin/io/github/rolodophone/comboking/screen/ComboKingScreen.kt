package io.github.rolodophone.comboking.screen

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

	override fun render(delta: Float) {
		val newDeltaTime = if (delta > MAX_DELTA_TIME) MAX_DELTA_TIME else delta
		engine.update(newDeltaTime)
	}

	override fun resize(width: Int, height: Int) {
		viewport.update(width, height, false)
	}
}