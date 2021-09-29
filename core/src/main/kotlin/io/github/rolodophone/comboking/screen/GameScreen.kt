package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.*
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.system.DebugSystem
import io.github.rolodophone.comboking.system.PhysicsSystem
import io.github.rolodophone.comboking.system.PlayerInputSystem
import io.github.rolodophone.comboking.system.RenderSystem
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.createWorld

private val tempVector = Vector2()

private const val MAX_DELTA_TIME = 1/10f

class GameScreen(game: ComboKing): ComboKingScreen(game) {
	private val gameEventManager = GameEventManager()
	private lateinit var world: World

	@Suppress("UNUSED_VARIABLE")
	override fun show() {
		//set up Box2d
		Box2D.init()
		world = createWorld()

		// add entities

		val player = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.prototype_player)
				rect.setPosition(120f, 5f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.prototype_player)
			}
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

		//add systems to engine (it is recommended to render *before* stepping the physics for some reason)
		engine.run {
			addSystem(PlayerInputSystem(gameViewport, gameEventManager))
			addSystem(RenderSystem(batch, gameViewport))
			addSystem(PhysicsSystem(world))
			addSystem(DebugSystem(gameEventManager, world, gameViewport))
		}
	}

	override fun hide() {
		//remove game entities and systems
		engine.removeAllEntities()
		engine.removeAllSystems()

		world.dispose()
	}

	override fun render(delta: Float) {
		val newDeltaTime = if (delta > MAX_DELTA_TIME) MAX_DELTA_TIME else delta
		engine.update(newDeltaTime)
	}

	override fun resize(width: Int, height: Int) {
		gameViewport.update(width, height, true)
	}
}