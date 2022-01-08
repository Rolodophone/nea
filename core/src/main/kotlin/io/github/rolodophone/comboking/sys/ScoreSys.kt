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
	private val scoreEntity: Entity
) : EntitySystem() {
	override fun update(deltaTime: Float) {
		val scoreComp = scoreEntity.getNotNull(ScoreComp.mapper)
		val scoreTextComp = scoreEntity.getNotNull(TextComp.mapper)
		val playerTransformComp = player.getNotNull(TransformComp.mapper)

		//calculate score
		scoreComp.score =
			playerTransformComp.x.toInt() - 120

		scoreTextComp.text = "SCORE: ${scoreComp.score}"
	}
}