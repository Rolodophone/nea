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

private const val WORLD_WIDTH = 320f
private const val WORLD_HEIGHT = 180f

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

	val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
	val gameEventManager = GameEventManager()
	lateinit var batch: Batch
	lateinit var comboKingTextures: ComboKingTextures
	lateinit var engine: Engine

	lateinit var testFont: BitmapFont

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		//init stuff
		batch = SpriteBatch(BATCH_SIZE)
		comboKingTextures = ComboKingTextures()
		engine = PooledEngine()

		Gdx.input.inputProcessor = InputMultiplexer(ButtonInputProcessor(engine, viewport))

		addScreen(MainMenuScreen(this))
		addScreen(GameScreen(this, createPlayerInputProcessor))

		setScreen<MainMenuScreen>()

		//add systems to engine (it is recommended to render *before* stepping the physics for some reason)
		engine.run {
			addSystem(RenderSystem(batch, viewport))
			addSystem(TextRenderSystem(batch, viewport))
		}

		testFont = BitmapFont()
	}

	override fun render() {
		super.render()
		batch.use {
			testFont.color = Color.WHITE
			testFont.draw(batch, "Testing", 0f, 0f)
		}
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