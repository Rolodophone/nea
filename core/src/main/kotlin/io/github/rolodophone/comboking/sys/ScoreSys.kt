package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.comp.ScoreComp
import io.github.rolodophone.comboking.comp.TextComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull

/**
 * Increases the score when certain game events are triggered.
 */
class ScoreSys(
	private val player: Entity,
	private val scoreEntity: Entity,
	private val timeSys: TimeSys
) : EntitySystem(10) {
	override fun update(deltaTime: Float) {
		val scoreComp = scoreEntity.getNotNull(ScoreComp.mapper)
		val scoreTextComp = scoreEntity.getNotNull(TextComp.mapper)
		val playerTransformComp = player.getNotNull(TransformComp.mapper)

		//calculate score
		val newDist = (playerTransformComp.x - 120) / 50f
		if (newDist > scoreComp.distance) scoreComp.distance = newDist
		scoreComp.time = timeSys.gameUptime
		scoreComp.score = (scoreComp.distance*50).toInt() +
				scoreComp.kills*1000 +
				(scoreComp.time*10).toInt()

		scoreTextComp.text = "SCORE: ${scoreComp.score}"
	}
}