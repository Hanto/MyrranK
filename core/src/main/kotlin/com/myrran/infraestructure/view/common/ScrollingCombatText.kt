package com.myrran.infraestructure.view.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.infraestructure.view.mobs.enemy.EnemyView
import com.myrran.infraestructure.view.mobs.player.PlayerView
import com.myrran.infraestructure.view.ui.TextView

class ScrollingCombatText(

    val assets: ScrollingCombatTextAssets
)
{
    fun addSCT(actor: Group, damage: HP) {

        val randomX = Math.random() * 30 - 15

        val text = TextView(damage, assets.font, actor.colorByType(), 2f) { it.value.toInt().toString() }
            .also { actor.addActor(it) }
            .also { it.setPosition(actor.originX + randomX.toFloat() - it.width/2, actor.height) }

        text.addAction(Actions.sequence(
            Actions.delay(2f),
            Actions.fadeOut(0.5f, Interpolation.sine)) )

        text.addAction(Actions.sequence(
            Actions.moveBy(0f, 40f, 2.5f, Interpolation.sine),
            Actions.removeActor()) )
    }

    private fun Group.colorByType(): Color =

        when (this) {

            is PlayerView -> Color.RED
            is EnemyView -> Color.ORANGE
            else -> Color.ORANGE
        }
}
