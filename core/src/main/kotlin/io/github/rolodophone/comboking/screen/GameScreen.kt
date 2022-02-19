package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.WORLD_HEIGHT
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.sys.*
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing when the game is being played.
 */
class GameScreen(
	game: ComboKing,
	private val createPlayerInputProcessor: (GameEventManager: GameEventManager, screenWidth: Int, screenHeight: Int)
	-> InputProcessor
): ComboKingScreen(game) {

	private lateinit var playerInputProcessor: InputProcessor

	private var gameScreenSystems: List<EntitySystem>? = null

	override fun show() {
		//add player controls input processor
		playerInputProcessor = createPlayerInputProcessor(gameEventManager, viewport.screenWidth, viewport.screenHeight)
		(Gdx.input.inputProcessor as InputMultiplexer).addProcessor(playerInputProcessor)

		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1f
			position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
			update()
		}

		//start counting gameUptime
		game.timeSys.gameUptime = 0f

		// add entities
		val player = engine.entity {
			with<InfoComp> {
				name = "Player"
			}
			with<TransformComp> {
				x = 120f
				y = 5f
				setSizeFromTexture(textures.player_idle0)
			}
			with<GraphicsComp>()
			with<AnimationComp> {
				animationLoops = listOf(
					AnimationComp.AnimationLoop(0.462f, listOf(textures.player_idle0, textures.player_idle1)),
					AnimationComp.AnimationLoop(0.077f, listOf(textures.player_run0, textures.player_run1,
						textures.player_run2, textures.player_run3, textures.player_run4, textures.player_run5,
						textures.player_run6, textures.player_run7)),
					AnimationComp.AnimationLoop(0.231f, listOf(textures.player_punch0, textures.player_punch1,
						textures.player_punch0)),
					AnimationComp.AnimationLoop(0.231f, listOf(textures.player_spinning_punch0,
						textures.player_spinning_punch1, textures.player_spinning_punch2,
						textures.player_spinning_punch1, textures.player_spinning_punch0)),
				)
				determineAnimationLoop = { _, action ->
					when (action) {
						Action.IDLE -> 0
						Action.RUN -> 1
						Action.PUNCH -> 2
						Action.SPIN_PUNCH -> 3
						Action.UP_STAIRS, Action.DOWN_STAIRS -> 0
						else -> throw RuntimeException("No animation loop for action $action")
					}
				}
				animationLoop = 1 //set not equal to 0 so that it triggers an AnimationLoop change
			}
			with<ActionComp> {
				runSpeed = 120f
			}
			with<HitboxComp> {
				x = 136f
				y = 8f
				width = 16f
				height = 52f
			}
			with<HPComp> {
				deleteWhenHP0 = false
				hp = 100f
			}
		}
		val scoreEntity = engine.entity {
			with<InfoComp> {
				name = "Score"
			}
			with<TransformComp> {
				x = 1f
				y = WORLD_HEIGHT - TextComp.FONT_HEIGHT - 1f
			}
			with<ScoreComp>()
			with<TextComp> {
				colour = Color.BLACK
				//text is set each frame by ScoreSys
			}
		}

		//add systems
		gameScreenSystems = listOf(
			PlayerInputSys(player, gameEventManager, game.timeSys),
			AISys(player, game.timeSys),
			ActionSys(player, sounds, game.timeSys),
			SpawningSys(player, textures, game.timeSys),
			KillingSys(scoreEntity),
			ScoreSys(player, scoreEntity, game.timeSys),
			BackgroundSys(textures, player),
			CameraSys(viewport, player),
			GameOverSys(game, gameEventManager, player, scoreEntity),
			AnimationSys(game.timeSys),
			TextRenderSys(batch, viewport, fonts),
			//DebugRenderSys(viewport),
			HPRenderSys(batch, viewport, fonts)
		)

		gameScreenSystems?.forEach { system ->
			engine.addSystem(system)
		}
	}

	override fun hide() {
		//stop counting gameUptime
		game.timeSys.gameUptime = -1f

		engine.removeAllEntities()

		(Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(playerInputProcessor)

		gameScreenSystems?.forEach { system ->
			engine.removeSystem(system)
		}

		gameScreenSystems = null
	}
}