package io.github.rolodophone.comboking.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import io.github.rolodophone.comboking.ComboKing

/** Launches the desktop (LWJGL3) application.  */
fun main() {
	Lwjgl3Application(
		ComboKing { gameEventManager ->
			KeyboardControlsInputProcessor(gameEventManager)
		},
		Lwjgl3ApplicationConfiguration().apply {
			setTitle("Combo King")
			//using 16x9 aspect ratio as although newer phones are often longer, there are many more older phones with
			//exactly 16x9. Also, most tablets are fatter than 16x9 so 16x9 is like a compromise between phones and tablets.
			setWindowedMode(16 * 60, 9 * 60)
			setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
		}
	)
}
