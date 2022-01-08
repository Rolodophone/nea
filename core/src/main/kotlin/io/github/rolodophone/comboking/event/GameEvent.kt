package io.github.rolodophone.comboking.event

import io.github.rolodophone.comboking.comp.MoveComp.MoveAction

/**
 * An event that can be triggered and responded to. Each event type can store its own data.
 */
sealed class GameEvent {
	/**
	 * Triggered whenever the user input suggests that the player should change their [MoveAction].
	 */
	object PlayerMoveInput: GameEvent() {
		var moveAction = MoveAction.STOP
	}
}