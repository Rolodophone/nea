package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.WORLD_WIDTH
import io.github.rolodophone.comboking.asset.ComboKingTextures
import io.github.rolodophone.comboking.comp.*
import io.github.rolodophone.comboking.util.getNotNull
import io.github.rolodophone.comboking.util.nextFloat
import ktx.ashley.entity
import ktx.ashley.with
import kotlin.math.abs
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

/**
 * Spawns the enemy entities automatically.
 */
class SpawningSys(
	private val player: Entity,
	private val textures: ComboKingTextures,
	private val timeSys: TimeSys
): EntitySystem(15) {

	private lateinit var playerTransformComp: TransformComp

	private var officeWorkerSpawnInterval = 1f
	private var lastOfficeWorkerSpawnTime = 0f

	override fun addedToEngine(engine: Engine) {
		playerTransformComp = player.getNotNull(TransformComp.mapper)
	}

	override fun update(deltaTime: Float) {
		//office worker (both with and without keyboard)
		if (timeSys.appUptime > lastOfficeWorkerSpawnTime + officeWorkerSpawnInterval) {

			if (nextBoolean()) spawnOfficeWorker() else spawnOfficeWorkerKB()

			lastOfficeWorkerSpawnTime = timeSys.appUptime

			officeWorkerSpawnInterval = when {
				timeSys.gameUptime < 20 -> nextFloat(3f, 5f)
				timeSys.gameUptime < 40 -> nextFloat(2f, 4f)
				timeSys.gameUptime < 60 -> nextFloat(1f, 3f)
				timeSys.gameUptime < 80 -> nextFloat(0.5f, 2.5f)
				timeSys.gameUptime < 100 -> nextFloat(0.4f, 1.5f)
				else -> nextFloat(0.3f, 1f)
			}
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
					AnimationComp.AnimationLoop(0.154f, listOf(textures.office_worker_run0, textures.office_worker_run1,
						textures.office_worker_run2, textures.office_worker_run3, textures.office_worker_run4,
						textures.office_worker_run5, textures.office_worker_run6, textures.office_worker_run7)),
					AnimationComp.AnimationLoop(0.077f, listOf(textures.office_worker_run0, textures.office_worker_run1,
						textures.office_worker_run2, textures.office_worker_run3, textures.office_worker_run4,
						textures.office_worker_run5, textures.office_worker_run6, textures.office_worker_run7)),
					AnimationComp.AnimationLoop(-1f, listOf(textures.office_worker_push))
				)
				determineAnimationLoop = { _, action ->
					when (action) {
						Action.IDLE -> 0
						Action.RUN -> 1
						Action.PUSH -> 2
						else -> 0
					}
				}
				animationLoop = 2 //set animation loop so it changes, triggering the ActionSys to record the time
			}
			with<ActionComp> {
				runSpeed = 80f
				walkSpeed = 40f
				facing = if (nextBoolean()) Facing.LEFT else Facing.RIGHT
			}
			with<AIComp> {
				//States: 0 Idle
				//        1 Run towards player
				//        2 Attack player
				determineState = { enemy, player ->
					val enemyHitbox = enemy.getNotNull(HitboxComp.mapper)
					val playerHitbox = player.getNotNull(HitboxComp.mapper)

					when {
						//if on different level do not attack player
						abs(enemyHitbox.y - playerHitbox.y) > 50 -> 0

						enemyHitbox.overlaps(playerHitbox) -> 2

						else -> 1
					}
				}
				determineAction = { _, _, state ->
					when (state) {
						0 -> Action.WALK
						1 -> Action.RUN
						2 -> Action.PUSH
						else -> Action.IDLE
					}
				}
				determineFacing = { enemy, player, state ->
					val playerTransformComp = player.getNotNull(TransformComp.mapper)
					val enemyTransformComp = enemy.getNotNull(TransformComp.mapper)
					val enemyActionComp = enemy.getNotNull(ActionComp.mapper)

					when {
						state == 0 -> enemyActionComp.facing
						playerTransformComp.x < enemyTransformComp.x -> Facing.LEFT
						else -> Facing.RIGHT
					}
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

	private fun spawnOfficeWorkerKB() {
		val transformCompX = if (nextBoolean()) playerTransformComp.x + WORLD_WIDTH
		else playerTransformComp.x - WORLD_WIDTH
		val transformCompY = (if (nextBoolean()) nextInt(0, 11)
		else nextInt(90, 101)).toFloat()

		engine.entity {
			with<InfoComp> {
				name = "OfficeWorkerKB"
			}
			with<TransformComp> {
				x = transformCompX
				y = transformCompY
				setSizeFromTexture(textures.office_worker_idle_kb)
			}
			with<GraphicsComp>()
			with<AnimationComp> {
				animationLoops = listOf(
					AnimationComp.AnimationLoop(0.154f, listOf(textures.office_worker_run_kb0,
						textures.office_worker_run_kb1, textures.office_worker_run_kb2, textures.office_worker_run_kb3,
						textures.office_worker_run_kb4, textures.office_worker_run_kb5, textures.office_worker_run_kb6,
						textures.office_worker_run_kb7)),
					AnimationComp.AnimationLoop(0.077f, listOf(textures.office_worker_run_kb0,
						textures.office_worker_run_kb1, textures.office_worker_run_kb2, textures.office_worker_run_kb3,
						textures.office_worker_run_kb4, textures.office_worker_run_kb5, textures.office_worker_run_kb6,
						textures.office_worker_run_kb7)),
					AnimationComp.AnimationLoop(0.462f, listOf(textures.office_worker_hit_kb0,
						textures.office_worker_hit_kb1))
				)
				determineAnimationLoop = { _, action ->
					when (action) {
						Action.IDLE -> 0
						Action.RUN -> 1
						Action.HIT_KB -> 2
						else -> 0
					}
				}
				animationLoop = 2 //set animation loop so it changes, triggering the ActionSys to record the time
			}
			with<ActionComp> {
				runSpeed = 80f
				walkSpeed = 40f
				facing = if (nextBoolean()) Facing.LEFT else Facing.RIGHT
			}
			with<AIComp> {
				//States: 0 Idle
				//        1 Run towards player
				//        2 Attack player
				determineState = { enemy, player ->
					val enemyHitbox = enemy.getNotNull(HitboxComp.mapper)
					val playerHitbox = player.getNotNull(HitboxComp.mapper)

					when {
						//if on different level do not attack player
						abs(enemyHitbox.y - playerHitbox.y) > 50 -> 0

						enemyHitbox.overlaps(playerHitbox) -> 2

						else -> 1
					}
				}
				determineAction = { _, _, state ->
					when (state) {
						0 -> Action.WALK
						1 -> Action.RUN
						2 -> Action.HIT_KB
						else -> Action.IDLE
					}
				}
				determineFacing = { enemy, player, state ->
					val playerTransformComp = player.getNotNull(TransformComp.mapper)
					val enemyTransformComp = enemy.getNotNull(TransformComp.mapper)
					val enemyActionComp = enemy.getNotNull(ActionComp.mapper)

					when {
						state == 0 -> enemyActionComp.facing
						playerTransformComp.x < enemyTransformComp.x -> Facing.LEFT
						else -> Facing.RIGHT
					}
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