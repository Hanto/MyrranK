package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.entities.mob.spells.effect.Effect
import com.myrran.infraestructure.view.mobs.spells.SpellViewAssets
import com.myrran.infraestructure.view.ui.TextView

data class EffectView(

    val model: Effect,
    val assets: SpellViewAssets

): Table()
{
    //private val rootTable: Table = Table().top().left()

    private val stacks = TextView(model.numberOfStacks().toString(), assets.effectFont, Color.WHITE, 1f)
    private val name = TextView(model.effectName(), assets.effectFont, Color.ORANGE, 1f) { it.value }
    private val duration = TextView(model.remainingDuration(), assets.effectFont, Color.GRAY, 1f) { it.toBox2DUnits().toInt().toString() }

    init {

        top().left().padTop(-3f).padBottom(-3f)

        rebuildTable()

        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        clearChildren()
        add(stacks).minWidth(10f)
        add(name)
        add(duration)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        stacks.setText(model.numberOfStacks().toString())
        duration.setText(model.remainingDuration())
    }
}
