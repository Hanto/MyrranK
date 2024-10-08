package com.myrran.infraestructure.view.skills.templates.form

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.DraggableIconView
import com.myrran.infraestructure.view.ui.TextView
import com.myrran.infraestructure.view.ui.TooltipView

class FormTemplateHeaderView(

    private val model: Quantity<FormTemplate>,
    private val assets: SkillViewAssets,

    ): Table()
{
    private val tooltip = TooltipView("Drag to add this Form to a Spell slot", assets.font14, assets.tooltipBackground)
    val icon = DraggableIconView(assets.getIcon("FormIcon"))
    private val name = TextView(model.value.name, assets.font20, Color.ORANGE, 2f) { it.value }
    private val available = TextView(model, assets.font14, model.toColor(), 2f) { "${it.available}/${it.total}" }
    private val description = TextView("FORM", assets.font14, Color.WHITE, 1f)
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

    fun setAvailable(quantity: Quantity<FormTemplate>) {

        available.setText(quantity)
        available.setTextColor(quantity.toColor())
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun Quantity<FormTemplate>.toColor() =

        when (this.isAvailable()) {
            true -> Color.GREEN
            false ->  Color.RED
        }
}
