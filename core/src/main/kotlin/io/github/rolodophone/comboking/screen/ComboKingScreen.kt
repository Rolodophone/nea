package io.github.rolodophone.comboking.screen

import io.github.rolodophone.comboking.ComboKing
import ktx.app.KtxScreen

abstract class ComboKingScreen(val game: ComboKing): KtxScreen {
    val batch = game.batch
    val gameViewport = game.gameViewport
    val engine = game.engine
    val textures = game.comboKingTextures
}