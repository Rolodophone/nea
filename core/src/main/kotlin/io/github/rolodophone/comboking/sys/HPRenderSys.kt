package io.github.rolodophone.comboking.sys

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.comboking.comp.HPComp
import io.github.rolodophone.comboking.comp.HitboxComp
import io.github.rolodophone.comboking.comp.TextComp
import io.github.rolodophone.comboking.comp.TransformComp
import io.github.rolodophone.comboking.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import kotlin.math.roundToInt

class HPRenderSys(
	private val batch: Batch,
	private val viewport: Viewport
) : IteratingSystem(
	allOf(TransformComp::class, HPComp::class).get(), 35
) {
	private lateinit var font: BitmapFont

	override fun addedToEngine(engine: Engine) {
		// load font
		font = BitmapFont(Gdx.files.internal("font/visitor.fnt"), Gdx.files.internal("font/visitor.png"), false)

		super.addedToEngine(engine)
	}

	override fun update(deltaTime: Float) {
		font.setUseIntegerPositions(false)
		font.color = Color.BLACK

		batch.use(viewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComp.mapper)
		val hpComp = entity.getNotNull(HPComp.mapper)
		val hitboxComp = entity[HitboxComp.mapper]

		if (hitboxComp == null) {
			font.draw(batch, hpComp.hp.roundToInt().toString(),
				transformComp.x, transformComp.y + transformComp.height + TextComp.FONT_HEIGHT + 1f)
		}
		else {
			font.draw(batch, hpComp.hp.roundToInt().toString(),
				hitboxComp.x, hitboxComp.y + hitboxComp.height + TextComp.FONT_HEIGHT + 1f)
		}
	}
}