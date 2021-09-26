package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.*
import io.github.rolodophone.comboking.event.GameEventManager
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.createWorld

private val tempVector = Vector2()

private const val MAX_DELTA_TIME = 1/10f

private const val WALL_WIDTH = 3f

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
				//TODO
			}
			with<GraphicsComponent> {
				//TODO
			}
			with<PlayerComponent> {}
		}

//		val background = engine.entity {
//			with<TransformComponent> {
//				setSizeFromTexture(textures.background)
//				rect.setPosition(0f, 0f)
//				z = -10
//			}
//			with<GraphicsComponent> {
//				sprite.setRegion(textures.background)
//			}
//		}
//
//		val paddle = engine.entity {
//			with<TransformComponent> {
//				setSizeFromTexture(textures.paddle_normal)
//				rect.setCenter(gameViewport.halfWorldWidth(), PaddleComponent.Y)
//			}
//			with<GraphicsComponent> {
//				sprite.setRegion(textures.paddle_normal)
//			}
//			with<PaddleComponent>()
//		}
//
//		val ball = engine.entity {
//			with<GraphicsComponent> {
//				sprite.setRegion(textures.ball)
//			}
//			with<PhysicsComponent> {
//				body = world.body(type = BodyDef.BodyType.DynamicBody) {
//					position.set(
//						gameViewport.halfWorldWidth(),
//						PaddleComponent.Y + textures.paddle_normal.regionHeight / 2f + textures.ball.regionHeight / 2f
//					)
//
//					circle(radius = textures.ball.regionWidth / 2f)
//				}
//			}
//			with<BallComponent>()
//  		}
//
//		val firingLine = engine.entity {
//			with<TransformComponent> {
//				setSizeFromTexture(textures.firing_line)
//				rect.setPosition(ball.getNotNull(PhysicsComponent.mapper).body!!.position)
//				rect.x -= textures.firing_line.regionWidth / 2f
//			}
//			with<GraphicsComponent> {
//				sprite.setRegion(textures.firing_line)
//				sprite.setOrigin(textures.firing_line.regionWidth / 2f, 0f)
//				visible = false
//			}
//			with<FiringLineComponent>()
//		}
//
//		val brick = engine.entity {
//			with<TransformComponent> {
//				setSizeFromTexture(textures.brick_red)
//				rect.setCenter(gameViewport.halfWorldWidth(), gameViewport.worldHeight - 60)
//			}
//			with<GraphicsComponent> {
//				sprite.setRegion(textures.brick_red)
//			}
//			with<BrickComponent>()
//		}
//
//		//add systems to engine (it is recommended to render *before* stepping the physics for some reason)
//		engine.run {
//			addSystem(PlayerInputSystem(gameViewport, gameEventManager, WALL_WIDTH))
//			addSystem(RenderSystem(batch, gameViewport))
//			addSystem(AimAndFireSystem(gameEventManager, paddle, ball, firingLine))
//			addSystem(PhysicsSystem(world))
//			addSystem(SpinSystem())
//			addSystem(BallBounceSystem(gameViewport, paddle, WALL_WIDTH))
//			addSystem(DebugSystem(gameEventManager, paddle, ball, world, gameViewport))
//		}
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