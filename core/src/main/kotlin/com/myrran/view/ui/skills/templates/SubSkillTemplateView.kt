package com.myrran.view.ui.skills.templates

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.Identifiable
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.misc.UIMoveListener
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.templates.stat.StatsTemplateView
import com.myrran.view.ui.skills.templates.subskill.SubSkillTemplateHeaderView

class SubSkillTemplateView(

    override val id: SkillViewId,
    private val model: SubSkillTemplate,
    private val assets: SkillViewAssets,

): Container<Table>(), Identifiable<SkillViewId>
{
    val header = SubSkillTemplateHeaderView(model, assets)

    init {

        header.touchable = Touchable.enabled
        header.addListener(UIMoveListener(this))
        addListener(UIClickListener { toFront() } )

        val table = Table()
        table.touchable = Touchable.enabled
        table.add(header).expandX().fillX().row()
        table.add(StatsTemplateView(model.stats, assets))

        actor = table
        setSize(prefWidth, prefHeight)
    }
}
