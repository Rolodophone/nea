package io.github.rolodophone.comboking

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.rolodophone.comboking.screen.ComboKingScreen
import io.github.rolodophone.comboking.screen.GameScreen
import io.github.rolodophone.comboking.screen.MainMenuScreen
import io.github.rolodophone.comboking.system.RenderSystem
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private const val BATCH_SIZE = 1000

private val log = logger<ComboKing>()

class ComboKing: KtxGame<ComboKingScreen>() {
    val gameViewport = FitViewport(320f, 180f)
    lateinit var batch: Batch
    lateinit var comboKingTextures: ComboKingTextures
    lateinit var engine: Engine

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG

        //init stuff
        batch = SpriteBatch(BATCH_SIZE)
        comboKingTextures = ComboKingTextures()
        engine = PooledEngine()

        //add systems to engine (it is recommended to render *before* stepping the physics for some reason)
        engine.run {
            addSystem(RenderSystem(batch, gameViewport))
        }

        addScreen(MainMenuScreen(this))
        setScreen<MainMenuScreen>()
    }

    override fun dispose() {
        log.debug { "Disposing game" }

        super.dispose()

        log.debug {
            val sb = batch as SpriteBatch
            "Max sprites in batch: ${sb.maxSpritesInBatch}; size of batch: $BATCH_SIZE"
        }
        batch.dispose()

        comboKingTextures.dispose()
    }
}