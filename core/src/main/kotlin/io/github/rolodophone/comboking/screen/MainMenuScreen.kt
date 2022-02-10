package io.github.rolodophone.comboking.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.ButtonComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.InfoComp
import io.github.rolodophone.comboking.comp.TransformComp
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing when the user is in the main menu.
 */
class MainMenuScreen(game: ComboKing): ComboKingScreen(game) {
	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1/4f
			position.set(viewport.worldWidth * 1/8f, viewport.worldHeight * 1/8f, 0f)
			update()
		}

		// background
		engine.entity {
			with<InfoComp> {
				name = "MainMenuBackground"
			}
			with<TransformComp> {
				x = 0f
				y = 0f
				setSizeFromTexture(textures.main_menu_bg)
			}
			with<GraphicsComp> {
				textureRegion = textures.main_menu_bg
			}
		}

		//buttons
		engine.entity {
			with<InfoComp> {
				name = "PlayButton"
			}
			with<TransformComp> {
				x = 34f
				y = 29f
				width = textures.btn_play.regionWidth/2f
				height = textures.btn_play.regionHeight/2f
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_play
			}
			with<ButtonComp> {
				onPress = { game.setScreen<GameScreen>() }
			}
		}
		engine.entity {
			with<InfoComp> {
				name = "OptionsButton"
			}
			with<TransformComp> {
				x = 34f
				y = 18f
				setSizeFromTexture(textures.btn_options)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_options
			}
			with<ButtonComp> {
				onPress = { }
			}
		}
		engine.entity {
			with<InfoComp> {
				name = "CreditsButton"
			}
			with<TransformComp> {
				x = 34f
				y = 7f
				setSizeFromTexture(textures.btn_credits)
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_credits
			}
			with<ButtonComp> {
				onPress = { }
			}
		}
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}