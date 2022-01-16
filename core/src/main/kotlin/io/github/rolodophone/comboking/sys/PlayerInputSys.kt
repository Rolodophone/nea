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
		val actionComp = player.getNotNull(ActionComp.mapper)

		when (event.input) {
			PlayerInput.STOP -> {
				when (actionComp.action) {
					Action.IDLE -> {}
					Action.RUN -> {
						actionComp.startAction(Action.IDLE)
					}
					Action.PUNCH, Action.SPIN_PUNCH, Action.HIT_KB, Action.PUSH -> {
						actionComp.returnToAction = Action.IDLE
					}
				}
			}
			PlayerInput.LEFT, PlayerInput.RIGHT -> {
				actionComp.startAction(Action.RUN)
			}
			PlayerInput.PUNCH_LEFT -> {
				when (actionComp.action) {
					Action.IDLE -> {
						actionComp.returnToAction = Action.IDLE

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.PUNCH
							Facing.RIGHT -> Action.SPIN_PUNCH
						})
					}
					Action.RUN -> {
						actionComp.returnToAction = Action.RUN

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.PUNCH
							Facing.RIGHT -> Action.SPIN_PUNCH
						})
					}
					else -> {}
				}
			}
			PlayerInput.PUNCH_RIGHT -> {
				when (actionComp.action) {
					Action.IDLE -> {
						actionComp.returnToAction = Action.IDLE

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.SPIN_PUNCH
							Facing.RIGHT -> Action.PUNCH
						})
					}
					Action.RUN -> {
						actionComp.returnToAction = Action.RUN

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.SPIN_PUNCH
							Facing.RIGHT -> Action.PUNCH
						})
					}
					else -> {}
				}
			}
		}

		when (event.input) {
			PlayerInput.LEFT -> actionComp.facing = Facing.LEFT
			PlayerInput.RIGHT -> actionComp.facing = Facing.RIGHT
			else -> {} // otherwise keep it the same
		}
	}
	
	override fun addedToEngine(engine: Engine) {
		gameEventManager.listen(GameEvent.PlayerInputEvent, eventCallback)
	}

	override fun removedFromEngine(engine: Engine) {
		gameEventManager.stopListening(GameEvent.PlayerInputEvent, eventCallback)
	}
}