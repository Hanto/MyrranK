package com.myrran.view.ui.skills.templates.buff

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.Identifiable
import com.myrran.domain.Quantity
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.stat.StatsTemplateView

class BuffTemplateView(

    override val id: SkillViewId,
    val model: Quantity<BuffSkillTemplate>,
    private val assets: SkillViewAssets,

): Container<Table>(), Identifiable<SkillViewId>
{
    val header = BuffTemplateHeaderView(model, assets)

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

    fun setAvailable(quantity: Quantity<BuffSkillTemplate>) {

        header.setAvailable(quantity)
    }
}
