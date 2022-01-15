package io.github.rolodophone.comboking.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.WORLD_HEIGHT
import io.github.rolodophone.comboking.WORLD_WIDTH

/**
 * Launches the game on desktop (LWJGL3).
 */
fun main() {
	Lwjgl3Application(
		ComboKing { gameEventManager, _, _ ->
			KeyboardControlsInputProcessor(gameEventManager)
		},
		Lwjgl3ApplicationConfiguration().apply {
			setTitle("Combo King")

			//using 16x9 aspect ratio as although newer phones are often longer, there are many more older phones with
			//exactly 16x9. Also, most tablets are fatter than 16x9 so 16x9 is like a compromise between phones and
			//tablets. Plus, most monitors are 16x9 so if you play the game in fullscreen it will look perfect.

			//default 3 pixels per world unit

			setWindowedMode(WORLD_WIDTH * 3, WORLD_HEIGHT * 3)

			setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
		}
	)
}
