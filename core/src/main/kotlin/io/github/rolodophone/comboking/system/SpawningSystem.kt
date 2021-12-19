package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.WORLD_WIDTH
import io.github.rolodophone.comboking.component.AIComponent
import io.github.rolodophone.comboking.component.GraphicsComponent
import io.github.rolodophone.comboking.component.MoveComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.nextFloat
import ktx.ashley.entity
import ktx.ashley.with
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt
import io.github.rolodophone.comboking.component.MoveComponent.MoveAction

/**
 * Spawns the enemy entities automatically.
 */
class EnemySpawningSystem(
	private val player: Entity,
	private val textures: ComboKingTextures
): EntitySystem() {

	lateinit var playerTransformComp: TransformComponent

	var gameUptime = 0f
	var officeWorkerSpawnInterval = 1f
	var lastOfficeWorkerSpawnTime = 0f

	override fun addedToEngine(engine: Engine) {
		playerTransformComp = player.getNotNull(TransformComponent.mapper)
	}

	override fun update(deltaTime: Float) {
		gameUptime += deltaTime

		//office worker
		if (gameUptime > lastOfficeWorkerSpawnTime + officeWorkerSpawnInterval) {
			spawnOfficeWorker()
			lastOfficeWorkerSpawnTime = gameUptime
			officeWorkerSpawnInterval = nextFloat(1f, 8f)
		}
	}

	private fun spawnOfficeWorker() {
		engine.entity {
			with<TransformComponent> {
				x = if (nextBoolean()) playerTransformComp.x + WORLD_WIDTH
					else playerTransformComp.x - WORLD_WIDTH
				y = (if (nextBoolean()) nextInt(0, 11)
					 else nextInt(90, 101)).toFloat()
				setSizeFromTexture(textures.prototype_enemy)
			}
			with<GraphicsComponent> {
				textureRegion = textures.prototype_enemy
			}
			with<MoveComponent> {
				runSpeed = 80f
			}
			with<AIComponent> {
				//States: 0 Idle
				//        1 Attack player
				determineState = { _, _ -> 1 }

				determineMoveAction = { enemy, player, state ->
					val playerTransformComp = player.getNotNull(TransformComponent.mapper)
					val enemyTransformComp = enemy.getNotNull(TransformComponent.mapper)

					when (state) {
						1 -> {
							if (playerTransformComp.x < enemyTransformComp.x) MoveAction.RUN_LEFT
							else MoveAction.RUN_RIGHT
						}
						else -> MoveAction.STOP
					}
				}
			}
		}
	}
}