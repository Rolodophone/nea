package io.github.rolodophone.comboking

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.rolodophone.comboking.asset.ComboKingFonts
import io.github.rolodophone.comboking.asset.ComboKingMusic
import io.github.rolodophone.comboking.asset.ComboKingSounds
import io.github.rolodophone.comboking.asset.ComboKingTextures
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.screen.ComboKingScreen
import io.github.rolodophone.comboking.screen.GameOverScreen
import io.github.rolodophone.comboking.screen.GameScreen
import io.github.rolodophone.comboking.screen.MenuScreen
import io.github.rolodophone.comboking.sys.MusicSys
import io.github.rolodophone.comboking.sys.SpriteRenderSys
import io.github.rolodophone.comboking.sys.TimeSys
import ktx.app.KtxGame

// 16x9 aspect ratio with a highest common factor of 20. This means I can scale by any multiple of 1/20
const val WORLD_WIDTH = 16*20
const val WORLD_HEIGHT = 9*20

private const val BATCH_SIZE = 1000

private val log = ckLogger<ComboKing>()


/**
 * The main class. This is created from a platform-specific launcher to start the app.
 *
 * @param createPlayerInputProcessor The function that creates the [InputProcessor] that will be used to detect the
 * game controls input. This is passed in as a parameter as the controls are different on Android and PC.
 */
class ComboKing(
	private val createPlayerInputProcessor: (GameEventManager: GameEventManager, screenWidth: Int, screenHeight: Int)
	-> InputProcessor
): KtxGame<ComboKingScreen>() {

	val viewport = FitViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
	val gameEventManager = GameEventManager()
	lateinit var batch: Batch
	lateinit var comboKingTextures: ComboKingTextures
	lateinit var comboKingFonts: ComboKingFonts
	lateinit var comboKingMusic: ComboKingMusic
	lateinit var comboKingSounds: ComboKingSounds
	lateinit var ckPrefs: CKPrefs
	lateinit var engine: Engine

	lateinit var timeSys: TimeSys

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		//init stuff
		batch = SpriteBatch(BATCH_SIZE)
		comboKingTextures = ComboKingTextures()
		comboKingFonts = ComboKingFonts()
		comboKingMusic = ComboKingMusic()
		comboKingSounds = ComboKingSounds()
		ckPrefs = CKPrefs()
		engine = PooledEngine()

		Gdx.input.inputProcessor = InputMultiplexer(UIInputProcessor(engine, viewport))

		addScreen(MenuScreen(this))
		addScreen(GameScreen(this, createPlayerInputProcessor))
		addScreen(GameOverScreen(this))

		timeSys = TimeSys()

		//add systems to engine
		engine.run {
			addSystem(timeSys)
			addSystem(SpriteRenderSys(batch, viewport))
			addSystem(MusicSys(comboKingMusic, this@ComboKing, ckPrefs))
		}

		comboKingSounds.setVolume(ckPrefs.getSFXVolume())

		setScreen<MenuScreen>()
	}

	override fun dispose() {
		log.debug { "Disposing game" }

		super.dispose() // disposes all registered screens

		log.debug {
			val sb = batch as SpriteBatch
			"Max sprites in batch: ${sb.maxSpritesInBatch}; size of batch: $BATCH_SIZE"
		}
		batch.dispose()

		comboKingTextures.dispose()
		comboKingFonts.dispose()
		comboKingMusic.dispose()
		comboKingSounds.dispose()
	}
}