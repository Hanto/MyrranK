package com.myrran.infraestructure.view.ui.skills.templates.stat

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.templates.stat.StatTemplate
import com.myrran.infraestructure.assets.SkillViewAssets

class StatsTemplateView(

    private val stats: Collection<StatTemplate>,
    private val assets: SkillViewAssets

): Table()
{
    private var statList = stats.map { StatTemplateView(it, assets) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padLeft(5f).padRight(5f).padBottom(7f)
        setBackground(assets.tableBackgroundLight)

        add(StatTemplateHeaderView(assets)).row()
        statList
            .sortedBy { it.stat.id.value }
            .forEach { add(it).left().bottom().row() }
        padBottom(9f)
    }
}
