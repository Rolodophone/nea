package io.github.rolodophone.comboking.event

/**
 * An event that can be triggered and responded to. Each event type can store its own data.
 */
sealed class GameEvent {
	/**
	 * Triggered by the input processors to communicate an input to the [PlayerInputSys][io.github.rolodophone.comboking
	 * .sys.PlayerInputSys].
	 */
	object PlayerInputEvent: GameEvent() {
		var input = PlayerInput.STOP
	}
}

enum class PlayerInput {
	STOP, LEFT, RIGHT
}