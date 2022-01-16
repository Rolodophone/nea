package io.github.rolodophone.comboking.event

/**
 * An event that can be triggered and responded to. Each event type can store its own data.
 */
sealed class GameEvent {

	abstract override fun toString(): String

	/**
	 * Triggered by the input processors to communicate an input to the [PlayerInputSys][io.github.rolodophone.comboking
	 * .sys.PlayerInputSys].
	 */
	object PlayerInputEvent: GameEvent() {
		var input = PlayerInput.STOP

		override fun toString() = "PlayerInputEvent(${input.name})"
	}
}

enum class PlayerInput {
	STOP, LEFT, RIGHT, PUNCH_LEFT, PUNCH_RIGHT
}