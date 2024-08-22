package com.myrran.infraestructure.view.mobs.spells.form

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.mob.spells.form.FormCircle
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.infraestructure.view.mobs.common.MobView
import com.myrran.infraestructure.view.mobs.common.SpellView
import com.myrran.infraestructure.view.mobs.common.SpriteAnimated
import com.myrran.infraestructure.view.mobs.spells.spell.SpellAnimation
import ktx.actors.alpha

class FormCircleView(

    private val model: FormCircle,
    private val sprite: SpriteAnimated<SpellAnimation>

): Group(), SpellView, MobView, Identifiable<EntityId>, Disposable
{
    override val id: EntityId = model.id

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        val radius = model.radius.toPixel().value()
        sprite.setSize((radius * 2 * 1.6).toFloat(), (radius * 2 * 1.6).toFloat())

        addActor(sprite)
        setSize(sprite.width, sprite.height)
        setOrigin(sprite.width/2, sprite.height/2)

        model.position
            .let { PositionMeters(it.x, it.y).toPixels() }
            .also { setPosition(it.x.toFloat(), it.y.toFloat(), Align.center) }

        sprite.alpha = 0.6f
        addAction(Actions.sequence(
            Actions.delay(0.1f),
            Actions.fadeOut(0.1f, Interpolation.circleOut),
            Actions.removeActor()))
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun updatePosition(fractionOfTimestep: Float) {}

    override fun dispose() {}
}
