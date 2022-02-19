package io.github.rolodophone.comboking.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import io.github.rolodophone.comboking.ComboKing
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.event.GameEvent
import io.github.rolodophone.comboking.sys.TextRenderSys
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.entity
import ktx.ashley.with

/**
 * The screen that's showing just after the player has run out of HP.
 */
class GameOverScreen(
	game: ComboKing
): ComboKingScreen(game) {

	private lateinit var scoreEntity: Entity
	private var scoreComp: ScoreComp? = null

	private lateinit var textRenderSys: TextRenderSys

	private val gameOverEventCallback = { event: GameEvent.GameOverEvent ->
		//score score info
		scoreComp = ScoreComp().apply {
			distance = event.scoreComp.distance
			kills = event.scoreComp.kills
			time = event.scoreComp.time
			score = event.scoreComp.score
		}
	}

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
				y = 160f
			}
			with<TextComp> {
				colour = Color(95/255f, 205/255f, 228/255f, 1f)
				//text is set soon after when I receive the GameOverEvent
			}
		}
		engine.entity {
			with<InfoComp> {
				name = "PlayAgainButton"
			}
			with<TransformComp> {
				width = textures.btn_play_again.regionWidth * 2f
				height = textures.btn_play_again.regionHeight * 2f
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
				width = textures.btn_main_menu.regionWidth * 2f
				height = textures.btn_main_menu.regionHeight * 2f
				x = (viewport.worldWidth - width) / 2f
				y = 15f
			}
			with<GraphicsComp> {
				textureRegion = textures.btn_main_menu
			}
			with<ButtonComp> {
				onPress = { game.setScreen<MenuScreen>() }
			}
		}

		textRenderSys = TextRenderSys(batch, viewport, fonts)
		engine.addSystem(textRenderSys)

		gameEventManager.listen(GameEvent.GameOverEvent, gameOverEventCallback)
	}

	override fun render(delta: Float) {
		super.render(delta)

		//set score display text from game event
		scoreComp?.let { scoreComp ->
			val scoreTextComp = scoreEntity.getNotNull(TextComp.mapper)
			val highscore = game.ckPrefs.getHighscore()

			scoreTextComp.text = "DISTANCE: ${scoreComp.distance.toInt()} (X50)\n" +
					"KILLS: ${scoreComp.kills} (X1000)\n" +
					"TIME: ${scoreComp.time.toInt()} (X10)\n" +
					"TOTAL SCORE: ${scoreComp.score}\n" +
					"HIGHSCORE: $highscore\n"

			if (scoreComp.score > highscore) {
				scoreTextComp.text += "NEW HIGHSCORE!\n"
				game.ckPrefs.putHighscore(scoreComp.score)
			}

			this.scoreComp = null
		}
	}

	override fun hide() {
		engine.removeAllEntities()
		engine.removeSystem(textRenderSys)
		gameEventManager.stopListening(GameEvent.GameOverEvent, gameOverEventCallback)
	}
}