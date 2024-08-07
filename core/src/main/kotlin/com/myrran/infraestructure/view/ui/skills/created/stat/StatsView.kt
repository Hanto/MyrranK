package com.myrran.infraestructure.view.ui.skills.created.stat

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.StatController
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.assets.SkillViewAssets

class StatsView(

    private val modelsRetriever: () -> Collection<Stat>,
    private val assets: SkillViewAssets,
    private val controller: StatController,

): Table()
{
    private var statsViews: Map<StatId, StatView> = getStatViews()

    init {

        top().left().padLeft(5f).padRight(5f).padBottom(7f)
        setBackground(assets.tableBackgroundLight)
        reBuildTable()
    }

    private fun reBuildTable() {

        clear()
        add(StatHeaderView(assets)).row()
        statsViews.values
            .sortedBy { it.model.id.value }
            .forEach { add(it).left().bottom().row() }
        padBottom(9f)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        statsViews[statId]?.update()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getStatViews(): Map<StatId, StatView> =

        modelsRetriever.invoke()
            .associate { it.id to StatView(it, assets, controller) }
}
