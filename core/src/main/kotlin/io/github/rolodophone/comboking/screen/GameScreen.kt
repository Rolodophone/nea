package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.MoveComponent
import io.github.rolodophone.comboking.component.PlayerComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.system.MoveSystem
import io.github.rolodophone.comboking.system.PlayerInputSystem
import ktx.ashley.entity
import ktx.ashley.with

class GameScreen(
	game: ComboKing,
	private val createPlayerInputProcessor: (GameEventManager) -> InputProcessor
): ComboKingScreen(game) {

	private lateinit var playerInputProcessor: InputProcessor

	private lateinit var playerInputSystem: PlayerInputSystem
	private lateinit var moveSystem: MoveSystem

	@Suppress("UNUSED_VARIABLE")
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
				setSizeFromTexture(textures.prototype_player)
				rect.setPosition(120f, 5f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_player)
			}
			with<MoveComponent>()
			with<PlayerComponent> {}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_ground)
				rect.setPosition(0f, 0f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_ground)
			}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_platform)
				rect.setPosition(0f, 90f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_platform)
			}
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_stairs)
				rect.setPosition(190f, 5f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_stairs)
			}
		}

		//add systems
		playerInputSystem = PlayerInputSystem(player, gameEventManager)
		moveSystem = MoveSystem()
		engine.run {
			addSystem(playerInputSystem)
			addSystem(moveSystem)
		}
	}

	override fun hide() {
		engine.removeAllEntities()

		(Gdx.input.inputProcessor as InputMultiplexer).removeProcessor(playerInputProcessor)

		engine.run {
			removeSystem(playerInputSystem)
			removeSystem(moveSystem)
		}
	}
}