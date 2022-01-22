package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.entity
import ktx.ashley.with

class GameOverScreen(
	game: ComboKing
): ComboKingScreen(game) {

	private lateinit var scoreEntity: Entity
	private var scoreComp: ScoreComp? = null

	override fun show() {
		//set camera
		with(viewport.camera as OrthographicCamera) {
			zoom = 1f
			position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
			update()
		}

		//add entities
		scoreEntity = engine.entity {
			with<InfoComp> {
				name = "GameOverScoreText"
			}
			with<TransformComp> {
				x = 80f
				y = 140f
			}
			with<TextComp>() //text set soon after when receive
		}
		engine.entity {
			with<InfoComp> {
				name = "PlayAgainButton"
			}
			with<TransformComp> {
				width = textures.btn_play_again.regionWidth * 4f
				height = textures.btn_play_again.regionHeight * 4f
				x = (viewport.worldWidth - width) / 2f
				y = 60f
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
				y = 15f
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_main_menu
			}
			with<ButtonComp> {
				onPress = { game.setScreen<MainMenuScreen>() }
			}
		}

		gameEventManager.listen(GameEvent.GameOverEvent) { event ->
			//score score info
			scoreComp = ScoreComp().apply {
				distScore = event.scoreComp.distScore
				killsScore = event.scoreComp.killsScore
				totalScore = event.scoreComp.totalScore
			}
		}
	}

	override fun render(delta: Float) {
		super.render(delta)

		//set score display text from game event
		scoreComp?.let { scoreComp ->
			val scoreTextComp = scoreEntity.getNotNull(TextComp.mapper)
			scoreTextComp.text = "DISTANCE: ${scoreComp.distScore}\n" +
					"KILLS: ${scoreComp.killsScore}\n" +
					"TOTAL SCORE: ${scoreComp.totalScore}"
		}
	}

	override fun hide() {
		engine.removeAllEntities()
	}
}