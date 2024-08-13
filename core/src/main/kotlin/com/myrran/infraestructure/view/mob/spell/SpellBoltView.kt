package com.myrran.infraestructure.view.mob.spell

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.domain.spells.SpellConstants.Companion.RANGE
import com.myrran.domain.spells.SpellConstants.Companion.SIZE
import com.myrran.domain.spells.spell.SpellBolt
import com.myrran.infraestructure.view.mob.Pixie
import ktx.actors.alpha
import ktx.math.minus

class SpellBoltView(

    private val model: SpellBolt,
    animations: Map<SpellAnimation, Animation<TextureRegion>>,
    size: SizePixels

): Pixie<SpellAnimation>(animations, SpellAnimation.GLOW, size), SpellView
{
    override val id: MobId = model.id

    init {

        val sizeMultiplier = (model.skill.getStat(SIZE)!!.totalBonus().value / 100 - 1)
        sizeBy(sizeMultiplier)

        val range = model.skill.getStat(RANGE)!!.totalBonus().value
        alpha = 0f
        addAction(Actions.sequence(
            Actions.fadeIn(0.3f, Interpolation.circleOut),
            Actions.delay(range - 0.6f),
            Actions.fadeOut(0.3f, Interpolation.circleOut)))
    }

    override fun update(fractionOfTimestep: Float) {

        val lastPosition = model.getLastPosition()
        val offset = model.position.minus(lastPosition)

        setPosition(
            lastPosition.x + offset.x * fractionOfTimestep,
            lastPosition.y + offset.y * fractionOfTimestep,
            Align.center)
    }
}
