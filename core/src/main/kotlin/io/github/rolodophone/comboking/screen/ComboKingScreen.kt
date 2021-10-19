package io.github.rolodophone.comboking.screen

import io.github.rolodophone.comboking.ComboKing
import ktx.app.KtxScreen

private const val MAX_DELTA_TIME = 1/10f

abstract class ComboKingScreen(val game: ComboKing): KtxScreen {
    val batch = game.batch
    val gameViewport = game.gameViewport
    val engine = game.engine
    val textures = game.comboKingTextures

    override fun render(delta: Float) {
        val newDeltaTime = if (delta > MAX_DELTA_TIME) MAX_DELTA_TIME else delta
        engine.update(newDeltaTime)
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, false)
    }
}