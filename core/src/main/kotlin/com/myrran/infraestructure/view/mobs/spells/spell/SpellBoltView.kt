package com.myrran.infraestructure.view.mobs.spells.spell

import box2dLight.PointLight
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.spells.spell.SpellBolt
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.RANGE
import com.myrran.domain.mobs.spells.spell.SpellConstants.Companion.SIZE
import com.myrran.infraestructure.view.mobs.common.MobView
import com.myrran.infraestructure.view.mobs.common.SpriteAnimated
import ktx.actors.alpha

class SpellBoltView(

    private val model: SpellBolt,
    private val light: PointLight,
    animations: Map<SpellAnimation, Animation<TextureRegion>>,

): SpriteAnimated<SpellAnimation>(animations, SpellAnimation.GLOW), MobView, Disposable
{
    override val id: MobId = model.id

    init {

        val sizeMultiplier = (model.skill.getStat(SIZE)!!.totalBonus().value / 100 - 1)
        val increasedSize = Pixel(width) * sizeMultiplier
        sizeBy(increasedSize.toFloat())

        setOrigin(width/2, height/2)

        rotateBy(model.linearVelocity.angleDeg() + 90)

        val range = model.skill.getStat(RANGE)!!.totalBonus().value
        alpha = 0f
        addAction(Actions.sequence(
            Actions.fadeIn(0.3f, Interpolation.circleOut),
            Actions.delay(range - 0.6f),
            Actions.fadeOut(0.3f, Interpolation.circleOut)))
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    override fun update(fractionOfTimestep: Float) {

        model.getInterpolatedPosition(fractionOfTimestep)
            .let { PositionMeters(it.x, it.y).toPixels() }
            .also { setPosition(it.x.toFloat(), it.y.toFloat(), Align.center) }
    }

    override fun dispose() =

        light.remove()
}
