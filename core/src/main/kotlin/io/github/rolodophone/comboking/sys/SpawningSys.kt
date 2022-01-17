package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.utils.TimeUtils
import io.github.rolodophone.comboking.comp.Action
import io.github.rolodophone.comboking.ComboKingTextures
import io.github.rolodophone.comboking.comp.InfoComp
import io.github.rolodophone.comboking.WORLD_WIDTH
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.nextFloat
import ktx.ashley.entity
import ktx.ashley.with
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

/**
 * Spawns the enemy entities automatically.
 */
class SpawningSys(
	private val player: Entity,
	private val textures: ComboKingTextures
): EntitySystem(15) {

	private lateinit var playerTransformComp: TransformComp

	private var officeWorkerSpawnInterval = 1000L
	private var lastOfficeWorkerSpawnTime = 0L

	override fun addedToEngine(engine: Engine) {
		playerTransformComp = player.getNotNull(TransformComp.mapper)
	}

	override fun update(deltaTime: Float) {
		val currentTime = TimeUtils.millis()

		//office worker
		if (currentTime > lastOfficeWorkerSpawnTime + officeWorkerSpawnInterval) {
			spawnOfficeWorker()
			lastOfficeWorkerSpawnTime = currentTime
			officeWorkerSpawnInterval = nextLong(1000, 8000)
		}
	}

	private fun spawnOfficeWorker() {
		val transformCompX = if (nextBoolean()) playerTransformComp.x + WORLD_WIDTH
							 else playerTransformComp.x - WORLD_WIDTH
		val transformCompY = (if (nextBoolean()) nextInt(0, 11)
							 else nextInt(90, 101)).toFloat()

		engine.entity {
			with<InfoComp> {
				name = "OfficeWorker"
			}
			with<TransformComp> {
				x = transformCompX
				y = transformCompY
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
				determineState = { enemy, player ->
					val enemyHitbox = enemy.getNotNull(HitboxComp.mapper)
					val playerHitbox = player.getNotNull(HitboxComp.mapper)

					if (enemyHitbox.overlaps(playerHitbox)) 0
					else 1
				}
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
			with<HitboxComp> {
				x = transformCompX + 9f
				y = transformCompY
				width = 18f
				height = 56f
			}
			with<HPComp> {
				deleteWhenHP0 = true
				hp = 50f
			}
		}
	}
}