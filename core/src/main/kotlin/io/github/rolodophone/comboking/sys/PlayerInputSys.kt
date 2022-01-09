package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.comp.Action
import io.github.rolodophone.comboking.comp.ActionComp
import io.github.rolodophone.comboking.comp.Facing
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.event.PlayerInput
import io.github.rolodophone.comboking.util.getNotNull

/**
 * Resolves any keyboard or touch input events relating to controlling the player.
 */
class PlayerInputSys(
	private val player: Entity,
	private val gameEventManager: GameEventManager
) : EntitySystem(0) {

	private val eventCallback = { event: GameEvent.PlayerInputEvent ->
		val moveComp = player.getNotNull(ActionComp.mapper)

		moveComp.action = when (event.input) {
			PlayerInput.STOP -> Action.IDLE
			PlayerInput.LEFT, PlayerInput.RIGHT -> Action.RUN
		}

		moveComp.facing = when (event.input) {
			PlayerInput.LEFT -> Facing.LEFT
			PlayerInput.RIGHT -> Facing.RIGHT
			else -> moveComp.facing // otherwise keep it the same
		}
	}
	
	override fun addedToEngine(engine: Engine) {
		gameEventManager.listen(GameEvent.PlayerInputEvent, eventCallback)
	}

	override fun removedFromEngine(engine: Engine) {
		gameEventManager.stopListening(GameEvent.PlayerInputEvent, eventCallback)
	}
}