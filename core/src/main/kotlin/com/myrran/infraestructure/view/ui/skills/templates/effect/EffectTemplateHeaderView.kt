package com.myrran.infraestructure.view.ui.skills.templates.effect

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.view.ui.misc.DraggableIconView
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.misc.TooltipView
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets

class EffectTemplateHeaderView(

    private val model: Quantity<EffectTemplate>,
    private val assets: SkillViewAssets,

    ): Table()
{
    private val tooltip = TooltipView("Drag to add this Effect to a Spell slot", assets.font14, assets.tooltipBackground)
    val icon = DraggableIconView(assets.getIcon("EffectIcon"))
    private val name = TextView(model.value.name, assets.font20, ORANGE, 2f) { it.value }
    private val available = TextView(model, assets.font14, model.toColor(), 2f) { "${it.available}/${it.total}" }
    private val description = TextView("EFFECT", assets.font12, WHITE, 1f)
    private val keys = TextView(model.value.keys.map { it.value }, assets.font14, PURPLE_LIGHT, 2f) { it.joinToString( " ") }

    init {

        left()
        setBackground(assets.templateHeader)
        icon.addListener(tooltip)
        tooltip.setInstant(true)

        val lowerTable = Table().bottom().left()
        lowerTable.add(name.align(Align.left)).left()
        lowerTable.add(available.align(Align.left)).left().padLeft(2f).padBottom(-4f)
        lowerTable.add(keys.align(Align.right)).bottom().right().expandX().fillX().padLeft(3f)

        val mainTable = Table().bottom().left()
        mainTable.padLeft(3f)
        mainTable.add(description.align(Align.left)).left().padBottom(-4f).padBottom(-2f).row()
        mainTable.add(lowerTable).expandX().fillX().padTop(-4f)

        add(icon).left()
        add(mainTable).right().expandX().fillX().padTop(-2f).padBottom(-2f).padRight(3f).row()
    }

    fun setAvailable(quantity: Quantity<EffectTemplate>) {

        available.setText(quantity)
        available.setTextColor(quantity.toColor())
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun Quantity<EffectTemplate>.toColor() =

        when (this.isAvailable()) {
            true -> Color.GREEN
            false ->  Color.RED
        }
}
