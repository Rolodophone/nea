package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.component.MoveComponent
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.util.getNotNull

/**
 * Keeps the player's [MoveComponent] up-to-date based on user input.
 */
class PlayerMoveSystem(
	private val player: Entity,
	private val gameEventManager: GameEventManager
) : EntitySystem() {

	private val eventCallback = { event: GameEvent.PlayerMoveInput ->
		val moveComp = player.getNotNull(MoveComponent.mapper)
		moveComp.moveAction = event.moveAction
	}
	
	override fun addedToEngine(engine: Engine) {
		gameEventManager.listen(GameEvent.PlayerMoveInput, eventCallback)
	}

	override fun removedFromEngine(engine: Engine) {
		gameEventManager.stopListening(GameEvent.PlayerMoveInput, eventCallback)
	}
}