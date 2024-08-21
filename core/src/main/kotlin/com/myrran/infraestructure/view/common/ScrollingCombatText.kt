package com.myrran.infraestructure.view.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.infraestructure.view.ui.TextView

class ScrollingCombatText(

    val assets: ScrollingCombatTextAssets
)
{
    fun addSCT(actor: Group, damage: HP) {

        val text = TextView(damage, assets.font, Color.ORANGE, 2f) { it.value.toInt().toString() }
        actor.addActor(text)

        val randomX = Math.random() * 30 - 15
        text.setPosition(actor.originX + randomX.toFloat() - text.width/2, actor.height)

        text.addAction(Actions.sequence(
            Actions.delay(2f),
            Actions.fadeOut(0.5f, Interpolation.sine)) )

        text.addAction(Actions.sequence(
            Actions.moveBy(0f, 40f, 2.5f, Interpolation.sine),
            Actions.removeActor()) )
    }
}
