package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager

class PlayerMoveSystem(
	private val player: Entity,
	private val gameEventManager: GameEventManager
) : EntitySystem() {
	override fun addedToEngine(engine: Engine) {
	}

	override fun update(deltaTime: Float) {
	}
}