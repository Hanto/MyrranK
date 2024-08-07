package com.myrran.view.ui.skills.templates.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.controller.SkillTemplateController
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.skills.assets.SkillViewAssets

class SkillTemplateHeaderView(

    private val model: Quantity<SkillTemplate>,
    private val assets: SkillViewAssets,
    private val controller: SkillTemplateController

): Table()
{
    private val icon = Image(assets.skillIcon)
    private val name = TextView(model.value.name, assets.font20, ORANGE, 2f) { it.value }
    private val available = TextView(model, assets.font14, model.toColor(), 2f) { "${it.available}/${it.total}" }
    private val description = TextView("SUBFORM", assets.font14, WHITE, 1f)
    private val createButton = TextView("Create", assets.font14, WHITE, 2f)

    init {

        createButton.addListener(UIClickListener { controller.addSkill(model.value.id) })

        left()
        setBackground(assets.tableBackgroundLightToDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))

        val lowerTable = Table().bottom().left()
        lowerTable.add(name.align(Align.left)).left()
        lowerTable.add(available.align(Align.left)).left().padLeft(2f).padBottom(-4f)
        lowerTable.add(createButton.align(Align.right)).bottom().right().expandX().fillX().padLeft(3f)

        val mainTable = Table().bottom().left()
        mainTable.padLeft(3f)
        mainTable.add(description.align(Align.left)).left().padBottom(-4f).padBottom(-2f).row()
        mainTable.add(lowerTable).expandX().fillX().padTop(-4f)

        add(icon).left()
        add(mainTable).right().expandX().fillX().padTop(-2f).padBottom(-2f).padRight(3f).row()
    }

    fun setAvailable(quantity: Quantity<SkillTemplate>) {

        available.setText(quantity)
        available.setTextColor(quantity.toColor())
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun Quantity<SkillTemplate>.toColor() =

        when (this.isAvailable()) {
            true -> Color.GREEN
            false ->  Color.RED
        }
}
