package io.github.rolodophone.comboking

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.screen.ComboKingScreen
import io.github.rolodophone.comboking.screen.GameScreen
import io.github.rolodophone.comboking.screen.MainMenuScreen
import io.github.rolodophone.comboking.system.RenderSystem
import io.github.rolodophone.comboking.system.TextRenderSystem
import ktx.app.KtxGame
import ktx.graphics.use
import ktx.log.logger

// 16x9 aspect ratio with a highest common factor of 20. This means I can scale by any multiple of 1/20
const val WORLD_WIDTH = 16*20
const val WORLD_HEIGHT = 9*20

private const val BATCH_SIZE = 1000

private val log = logger<ComboKing>()


/**
 * The main class. This is created from a platform-specific launcher to start the app.
 *
 * @param createPlayerInputProcessor The function that creates the [InputProcessor] that will be used to detect the
 * game controls input. This is passed in as a parameter as the controls are different on Android and PC.
 */
class ComboKing(
	private val createPlayerInputProcessor: (GameEventManager) -> InputProcessor
): KtxGame<ComboKingScreen>() {

	val viewport = FitViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
	val gameEventManager = GameEventManager()
	lateinit var batch: Batch
	lateinit var comboKingTextures: ComboKingTextures
	lateinit var engine: Engine

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		//init stuff
		batch = SpriteBatch(BATCH_SIZE)
		comboKingTextures = ComboKingTextures()
		engine = PooledEngine()

		Gdx.input.inputProcessor = InputMultiplexer(ButtonInputProcessor(engine, viewport))

		addScreen(MainMenuScreen(this))
		addScreen(GameScreen(this, createPlayerInputProcessor))

		//update viewport so that TextRenderSystem can save the projection matrix
		viewport.update(viewport.screenWidth, viewport.screenHeight, true)

		//add systems to engine (it is recommended to render *before* stepping the physics for some reason)
		engine.run {
			addSystem(RenderSystem(batch, viewport))
			addSystem(TextRenderSystem(batch, viewport))
		}

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