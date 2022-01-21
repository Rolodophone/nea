package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.ButtonComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.InfoComp
import io.github.rolodophone.comboking.comp.TransformComp
import ktx.ashley.entity
import ktx.ashley.with

class GameOverScreen(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1f
			position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
			update()
		}

		//add entities
		engine.entity {
			with<InfoComp> {
				name = "PlayAgainButton"
			}
			with<TransformComp> {
				width = textures.btn_play_again.regionWidth * 4f
				height = textures.btn_play_again.regionHeight * 4f
				x = (viewport.worldWidth - width) / 2f
				y = 90f
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_play_again
			}
			with<ButtonComp> {
				onPress = { game.setScreen<GameScreen>() }
			}
		}
		engine.entity {
			with<InfoComp> {
				name = "MainMenuButton"
			}
			with<TransformComp> {
				width = textures.btn_main_menu.regionWidth * 4f
				height = textures.btn_main_menu.regionHeight * 4f
				x = (viewport.worldWidth - width) / 2f
				y = 45f
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_main_menu
			}
			with<ButtonComp> {
				onPress = { game.setScreen<MainMenuScreen>() }
			}
		}
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}