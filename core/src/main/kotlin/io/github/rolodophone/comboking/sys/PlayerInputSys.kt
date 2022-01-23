package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.event.GameEventManager
import io.github.rolodophone.comboking.event.PlayerInput
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.get

/**
 * Resolves any keyboard or touch input events relating to controlling the player.
 */
class PlayerInputSys(
	private val player: Entity,
	private val gameEventManager: GameEventManager,
	private val timeSys: TimeSys
) : EntitySystem(0) {

	private val eventCallback = { event: GameEvent.PlayerInputEvent ->
		val actionComp = player.getNotNull(ActionComp.mapper)

		when (event.input) {
			PlayerInput.STOP -> {
				when (actionComp.action) {
					Action.RUN -> {
						actionComp.startAction(Action.IDLE, timeSys.appUptime)
					}
					Action.PUNCH, Action.SPIN_PUNCH, Action.HIT_KB, Action.PUSH -> {
						actionComp.returnToAction = Action.IDLE
					}
					else -> {}
				}
			}
			PlayerInput.LEFT, PlayerInput.RIGHT -> {
				actionComp.startAction(Action.RUN, timeSys.appUptime)
			}
			PlayerInput.PUNCH_LEFT -> {
				when (actionComp.action) {
					Action.IDLE -> {
						actionComp.returnToAction = Action.IDLE

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.PUNCH
							Facing.RIGHT -> Action.SPIN_PUNCH
						}, timeSys.appUptime)
					}
					Action.RUN -> {
						actionComp.returnToAction = Action.RUN

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.PUNCH
							Facing.RIGHT -> Action.SPIN_PUNCH
						}, timeSys.appUptime)
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
						}, timeSys.appUptime)
					}
					Action.RUN -> {
						actionComp.returnToAction = Action.RUN

						actionComp.startAction(when (actionComp.facing) {
							Facing.LEFT -> Action.SPIN_PUNCH
							Facing.RIGHT -> Action.PUNCH
						}, timeSys.appUptime)
					}
					else -> {}
				}
			}
			PlayerInput.UP_STAIRS -> {
				if (nearDoor(player)) {
					actionComp.startAction(Action.UP_STAIRS, timeSys.appUptime)
				}
			}
			PlayerInput.DOWN_STAIRS -> {
				if (nearDoor(player)) {
					actionComp.startAction(Action.DOWN_STAIRS, timeSys.appUptime)
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

	private fun nearDoor(entity: Entity): Boolean {
		val thisHitboxComp = entity.getNotNull(HitboxComp.mapper)

		for (possibleDoor in engine.entities) {

			//ignore entities that are not doors
			if (possibleDoor[InfoComp.mapper]?.name != "Door") continue

			val possibleDoorTransformComp = possibleDoor[TransformComp.mapper] ?: continue

			if (possibleDoorTransformComp.overlaps(thisHitboxComp)) return true
		}

		return false
	}
}