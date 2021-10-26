package io.github.rolodophone.comboking.event

sealed class GameEvent {
	object PlayerMoveInput {
		object Stop: GameEvent()
		object RunLeft: GameEvent()
		object RunRight: GameEvent()
	}
}