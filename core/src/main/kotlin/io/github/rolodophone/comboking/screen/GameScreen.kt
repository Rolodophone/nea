package io.github.rolodophone.comboking.screen

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

	private lateinit var playerInputSystem: PlayerInputSystem
	private lateinit var moveSystem: MoveSystem
	private lateinit var cameraSystem: CameraSystem
	private lateinit var backgroundSystem: BackgroundSystem
	private lateinit var scoreSystem: ScoreSystem
	private lateinit var enemySpawningSystem: EnemySpawningSystem

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
				setSizeFromTexture(textures.prototype_player)
			}
			with<GraphicsComponent> {
				textureRegion = textures.prototype_player
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
		playerInputSystem = PlayerInputSystem(player, gameEventManager)
		moveSystem = MoveSystem()
		cameraSystem = CameraSystem(viewport, player)
		backgroundSystem = BackgroundSystem(textures, player)
		scoreSystem = ScoreSystem(player, scoreEntity)
		enemySpawningSystem = EnemySpawningSystem(player, textures)

		engine.run {
			addSystem(playerInputSystem)
			addSystem(moveSystem)
			addSystem(cameraSystem)
			addSystem(backgroundSystem)
			addSystem(scoreSystem)
			addSystem(enemySpawningSystem)
		}
	}

	override fun hide() {
		engine.removeAllEntities()

		(Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(playerInputProcessor)

		engine.run {
			removeSystem(playerInputSystem)
			removeSystem(moveSystem)
			removeSystem(cameraSystem)
		}
	}
}