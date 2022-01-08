package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
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
	private val createPlayerInputProcessor: (GameEventManager) -> InputProcessor
): ComboKingScreen(game) {

	private lateinit var playerInputProcessor: InputProcessor

	private var gameScreenSystems: List<EntitySystem>? = null

	override fun show() {
		//add player controls input processor
		playerInputProcessor = createPlayerInputProcessor(gameEventManager)
		(Gdx.input.inputProcessor as InputMultiplexer).addProcessor(playerInputProcessor)

		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1f
			position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
			update()
		}

		// add entities
		val player = engine.entity {
			with<TransformComp> {
				x = 120f
				y = 5f
				setSizeFromTexture(textures.player_idle0)
			}
			with<GraphicsComp> {
				textureRegion = textures.player_idle0
			}
			with<MoveComp> {
				runSpeed = 120f
			}
			with<PlayerComp>()
		}
		val scoreEntity = engine.entity {
			with<TransformComp> {
				x = 1f
				y = WORLD_HEIGHT - TextComp.FONT_HEIGHT - 1f
			}
			with<ScoreComp>()
			with<TextComp>() //text is set each frame by ScoreSys
		}

		//add systems
		gameScreenSystems = listOf(
			PlayerInputSys(player, gameEventManager),
			MoveSys(),
			CameraSys(viewport, player),
			BackgroundSys(textures, player),
			ScoreSys(player, scoreEntity),
			EnemySpawningSystem(player, textures),
			AISys(player),
			AnimationSys()
		)

		gameScreenSystems?.forEach { system ->
			engine.addSystem(system)
		}
	}

	override fun hide() {
		engine.removeAllEntities()

		(Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(playerInputProcessor)

		gameScreenSystems?.forEach { system ->
			engine.removeSystem(system)
		}

		gameScreenSystems = null
	}
}