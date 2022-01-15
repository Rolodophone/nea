package io.github.rolodophone.comboking

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * Launches the game on Android.
 */
class AndroidLauncher : AndroidApplication() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initialize(
			ComboKing { gameEventManager, screenWidth, screenHeight ->
				TouchControlsGestureListener(gameEventManager, screenWidth, screenHeight).createGestureDetector() },
			AndroidApplicationConfiguration().apply { useImmersiveMode = true }
		)
	}
}