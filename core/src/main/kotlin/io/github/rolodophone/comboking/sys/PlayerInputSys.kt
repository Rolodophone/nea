package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.comp.MoveComp
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.util.getNotNull

/**
 * Keeps the player's [MoveComp] up-to-date based on user input.
 */
class PlayerInputSys(
	private val player: Entity,
	private val gameEventManager: GameEventManager
) : EntitySystem(0) {

	private val eventCallback = { event: GameEvent.PlayerMoveInput ->
		val moveComp = player.getNotNull(MoveComp.mapper)
		moveComp.moveAction = event.moveAction
	}
	
	override fun addedToEngine(engine: Engine) {
		gameEventManager.listen(GameEvent.PlayerMoveInput, eventCallback)
	}

	override fun removedFromEngine(engine: Engine) {
		gameEventManager.stopListening(GameEvent.PlayerMoveInput, eventCallback)
	}
}