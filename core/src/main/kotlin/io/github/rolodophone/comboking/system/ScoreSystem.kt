package io.github.rolodophone.comboking.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import io.github.rolodophone.comboking.component.ScoreComponent
import io.github.rolodophone.comboking.component.TextComponent
import io.github.rolodophone.comboking.component.TransformComponent
import io.github.rolodophone.comboking.util.getNotNull

/**
 * Increases the score when certain game events are triggered.
 */
class ScoreSystem(
	private val player: Entity,
	private val scoreEntity: Entity
) : EntitySystem() {
	override fun update(deltaTime: Float) {
		val scoreComp = scoreEntity.getNotNull(ScoreComponent.mapper)
		val scoreTextComp = scoreEntity.getNotNull(TextComponent.mapper)
		val playerTransformComp = player.getNotNull(TransformComponent.mapper)

		//calculate score
		scoreComp.score =
			playerTransformComp.x.toInt() - 120

		scoreTextComp.text = "SCORE: ${scoreComp.score}"
	}
}