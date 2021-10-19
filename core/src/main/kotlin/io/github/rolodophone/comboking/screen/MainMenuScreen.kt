package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class MainMenuScreen(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
		//set camera
		with(gameViewport.camera as OrthographicCamera) {
			zoom = 0.5f
			position.set(gameViewport.worldWidth / 4f, gameViewport.worldHeight / 4f, 0f)
			update()
		}

		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.main_menu_bg)
				rect.setPosition(0f, 0f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.main_menu_bg)
			}
		}
	}
}