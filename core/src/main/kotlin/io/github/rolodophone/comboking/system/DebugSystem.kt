package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.event.GameEventManager

/**
 * Controls debugging features. This System will probably be disabled in the release.
 */
@Suppress("unused")
class DebugSystem(
	private val gameEventManager: GameEventManager,
	private val viewport: Viewport
): EntitySystem() {

	override fun addedToEngine(engine: Engine) {
	}

	override fun removedFromEngine(engine: Engine) {
	}

	override fun update(deltaTime: Float) {
	}
}