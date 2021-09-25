package io.github.rolodophone.comboking.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager

/**
 * Controls debugging features. This System will probably be disabled in the release.
 */
@Suppress("unused")
class DebugSystem(
	private val gameEventManager: GameEventManager,
	private val paddle: Entity,
	private val ball: Entity,
	private val world: World,
	private val gameViewport: Viewport
): EntitySystem() {
	private lateinit var debugRenderer: Box2DDebugRenderer

	override fun addedToEngine(engine: Engine) {
		debugRenderer = Box2DDebugRenderer()
	}

	override fun removedFromEngine(engine: Engine) {
		debugRenderer.dispose()
	}

	override fun update(deltaTime: Float) {

		//render Box2D bodies
		debugRenderer.render(world, gameViewport.camera.combined)
	}
}