package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.WORLD_HEIGHT
import io.github.rolodophone.comboking.component.*
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.system.*
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
			with<TransformComponent> {
				x = 120f
				y = 5f
				setSizeFromTexture(textures.player_idle0)
			}
			with<GraphicsComponent> {
				textureRegion = textures.player_idle0
			}
			with<MoveComponent> {
				runSpeed = 120f
			}
			with<PlayerComponent>()
		}
		val scoreEntity = engine.entity {
			with<TransformComponent> {
				x = 1f
				y = WORLD_HEIGHT - TextComponent.FONT_HEIGHT - 1f
			}
			with<ScoreComponent>()
			with<TextComponent>() //text is set each frame by ScoreSystem
		}

		//add systems
		gameScreenSystems = listOf(
			PlayerInputSystem(player, gameEventManager),
			MoveSystem(),
			CameraSystem(viewport, player),
			BackgroundSystem(textures, player),
			ScoreSystem(player, scoreEntity),
			EnemySpawningSystem(player, textures),
			AISystem(player),
			AnimationSystem()
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