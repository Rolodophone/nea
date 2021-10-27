package io.github.rolodophone.comboking.event

import io.github.rolodophone.comboking.MoveAction

sealed class GameEvent {
	object PlayerMoveInput: GameEvent() {
		var moveAction = MoveAction.STOP
	}
}