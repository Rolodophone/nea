package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.comp.InfoComp
import io.github.rolodophone.comboking.comp.GraphicsComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.entity
import ktx.ashley.with

/**
 * Controls the background and door entities
 */
class BackgroundSys(
	private val textures: ComboKingTextures,
	private val player: Entity
): EntitySystem(20) {

	private lateinit var walls: List<Entity>
	private lateinit var doors: List<Pair<Entity, Entity>>
	private lateinit var railings: List<Entity>
	private lateinit var allEntities: List<List<Entity>>
	private var leftmostIndex = 0

	override fun addedToEngine(engine: Engine) {
		// create 3 background wall entities, one directly in line with the screen and one on either side
		walls = List(3) { i ->
			engine.entity {
				with<InfoComp> {
					name = "GameBackground"
				}
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

		doors = List(3) { i ->
			Pair(
				spawnDoor(textures.game_bg.regionWidth * (i-1) + 200f, 20f),
				spawnDoor(textures.game_bg.regionWidth * (i-1) + 200f, 110f),
			)
		}

		railings = List(3) { i ->
			engine.entity {
				with<InfoComp> {
					name = "Railing"
				}
				with<TransformComp> {
					x = (textures.game_bg.regionWidth * (i-1)).toFloat()
					y = 91f
					z = 10f
					setSizeFromTexture(textures.railing)
				}
				with<GraphicsComp> {
					textureRegion = textures.railing
				}
			}
		}

		allEntities = List(3) { i ->
			listOf(walls[i], doors[i].first, doors[i].second, railings[i])
		}
	}

	private fun spawnDoor(x: Float, y: Float): Entity {
		return engine.entity {
			with<InfoComp> {
				name = "Door"
			}
			with<TransformComp> {
				this.x = x
				this.y = y
				this.z = -5f
				setSizeFromTexture(textures.door)
			}
			with<GraphicsComp> {
				textureRegion = textures.door
			}
		}
	}

	override fun update(deltaTime: Float) {
		val playerTransform = player.getNotNull(TransformComp.mapper)

		val middleIndex = (leftmostIndex + 1) % 3
		val rightmostIndex = (leftmostIndex + 2) % 3

		val middleWallTransform = walls[middleIndex].getNotNull(TransformComp.mapper)
		val rightmostWallTransform = walls[rightmostIndex].getNotNull(TransformComp.mapper)

		if (playerTransform.x < middleWallTransform.x) {
			// move rightmost entities to the left-hand side
			for (entity in allEntities[rightmostIndex]) {
				entity.getNotNull(TransformComp.mapper).x -= textures.game_bg.regionWidth * 3
			}
			leftmostIndex = rightmostIndex
		}
		else if (playerTransform.x > rightmostWallTransform.x) {
			// move leftmost entities to the right-hand side
			for (entity in allEntities[leftmostIndex]) {
				entity.getNotNull(TransformComp.mapper).x += textures.game_bg.regionWidth * 3
			}
			leftmostIndex = middleIndex
		}
	}
}