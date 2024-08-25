package com.myrran.infraestructure.view.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.domain.entities.common.collisioner.Collision
import com.myrran.domain.entities.common.collisioner.NoLocation
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.infraestructure.view.mobs.enemy.EnemyView
import com.myrran.infraestructure.view.mobs.player.PlayerView
import com.myrran.infraestructure.view.ui.TextView

class ScrollingCombatText(

    val assets: ScrollingCombatTextAssets
)
{
    fun addSCT(actor: Group, damage: Damage) {

        val randomX = Math.random() * 30 - 15

        val text = TextView(damage, damage.getFont(), actor.colorByType(), 2f) { it.amount.value.toInt().toString() }
            .also { actor.addActor(it) }
            .also { it.setPosition(actor.originX + randomX.toFloat() - it.width/2, actor.height) }

        text.addAction(Actions.sequence(
            Actions.delay(1.5f),
            Actions.fadeOut(0.5f, Interpolation.sine)) )

        text.addAction(Actions.sequence(
            Actions.moveBy(0f, 45f, 2.0f, Interpolation.sine),
            Actions.removeActor()) )
    }

    private fun Damage.getFont(): BitmapFont =

        when (this.location) {
            is Collision -> assets.fontDirectDamage
            NoLocation -> assets.fontEffectDamage
        }

    private fun Group.colorByType(): Color =

        when (this) {

            is PlayerView -> Color.RED
            is EnemyView -> Color.ORANGE
            else -> Color.ORANGE
        }
}
