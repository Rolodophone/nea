package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.entity
import ktx.ashley.with

/**
 * Draws the repeating background.
 */
class BackgroundSys(
	private val textures: ComboKingTextures,
	private val player: Entity
): EntitySystem() {

	private lateinit var walls: List<Entity>
	private var leftmostWallIndex = 0

	override fun addedToEngine(engine: Engine) {
		// create 3 background wall entities, one directly in line with the screen and one on either side
		walls = List(3) { i ->
			engine.entity {
				with<TransformComp> {
					x = (textures.game_bg.regionWidth * (i-1)).toFloat()
					y = 0f
					z = -10f
					setSizeFromTexture(textures.game_bg)
				}
				with<GraphicsComp> {
					textureRegion = textures.game_bg
				}
			}
		}
	}
	override fun update(deltaTime: Float) {
		// see diagram for explanation

		val playerTransform = player.getNotNull(TransformComp.mapper)

		val middleWallIndex = (leftmostWallIndex + 1) % 3
		val rightmostWallIndex = (leftmostWallIndex + 2) % 3

		val leftmostWallTransform = walls[leftmostWallIndex].getNotNull(TransformComp.mapper)
		val middleWallTransform = walls[middleWallIndex].getNotNull(TransformComp.mapper)
		val rightmostWallTransform = walls[rightmostWallIndex].getNotNull(TransformComp.mapper)

		if (playerTransform.x < middleWallTransform.x) {
			// move rightmost wall to the left-hand side
			rightmostWallTransform.x -= textures.game_bg.regionWidth * 3
			leftmostWallIndex = rightmostWallIndex
		}
		else if (playerTransform.x > rightmostWallTransform.x) {
			// move leftmost wall to the right-hand side
			leftmostWallTransform.x += textures.game_bg.regionWidth * 3
			leftmostWallIndex = middleWallIndex
		}
	}
}