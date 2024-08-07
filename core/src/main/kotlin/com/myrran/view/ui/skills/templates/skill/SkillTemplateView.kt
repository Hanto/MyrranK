package com.myrran.view.ui.skills.templates.skill

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.SkillTemplateController
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.stat.StatsTemplateView

class SkillTemplateView(

    override val id: SkillViewId,
    val model: Quantity<SkillTemplate>,
    private val assets: SkillViewAssets,
    private val controller: SkillTemplateController,

): Container<Table>(), Identifiable<SkillViewId>
{
    val header = SkillTemplateHeaderView(model, assets, controller)

    init {

        header.touchable = Touchable.enabled
        addListener(UIClickListener { toFront() } )

        val table = Table()
        table.touchable = Touchable.enabled
        table.add(header).expandX().fillX().row()
        table.add(StatsTemplateView(model.value.stats, assets))

        actor = table
        setSize(prefWidth, prefHeight)
    }

    fun setAvailable(quantity: Quantity<SkillTemplate>) {

        header.setAvailable(quantity)
    }
}
