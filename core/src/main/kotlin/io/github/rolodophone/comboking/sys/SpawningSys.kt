package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.comp.Action
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.WORLD_WIDTH
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.nextFloat
import ktx.ashley.entity
import ktx.ashley.with
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

/**
 * Spawns the enemy entities automatically.
 */
class EnemySpawningSystem(
	private val player: Entity,
	private val textures: ComboKingTextures
): EntitySystem(15) {

	lateinit var playerTransformComp: TransformComp

	var gameUptime = 0f
	var officeWorkerSpawnInterval = 1f
	var lastOfficeWorkerSpawnTime = 0f

	override fun addedToEngine(engine: Engine) {
		playerTransformComp = player.getNotNull(TransformComp.mapper)
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
			with<TransformComp> {
				x = if (nextBoolean()) playerTransformComp.x + WORLD_WIDTH
					else playerTransformComp.x - WORLD_WIDTH
				y = (if (nextBoolean()) nextInt(0, 11)
					 else nextInt(90, 101)).toFloat()
				setSizeFromTexture(textures.office_worker_idle)
			}
			with<GraphicsComp>()
			with<AnimationComp> {
				animationLoops = listOf(
					AnimationComp.AnimationLoop(Int.MAX_VALUE, listOf(textures.office_worker_idle)),
					AnimationComp.AnimationLoop(77, listOf(textures.office_worker_run0, textures.office_worker_run1,
						textures.office_worker_run2, textures.office_worker_run3, textures.office_worker_run4,
						textures.office_worker_run5, textures.office_worker_run6, textures.office_worker_run7))
				)
				determineAnimationLoop = { _, action ->
					when (action) {
						Action.IDLE -> 0
						Action.RUN -> 1
						else -> 0
					}
				}
			}
			with<ActionComp> {
				runSpeed = 80f
			}
			with<AIComp> {
				//States: 0 Idle
				//        1 Attack player
				determineState = { _, _ -> 1 }
				determineAction = { _, _, state ->
					when (state) {
						1 -> Action.RUN
						else -> Action.IDLE
					}
				}
				determineFacing = { enemy, player, _ ->
					val playerTransformComp = player.getNotNull(TransformComp.mapper)
					val enemyTransformComp = enemy.getNotNull(TransformComp.mapper)

					if (playerTransformComp.x < enemyTransformComp.x) Facing.LEFT
					else Facing.RIGHT
				}
			}
		}
	}
}