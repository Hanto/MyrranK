package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController

class StatsView(

    stats: Collection<Stat>,
    assets: SkillAssets,
    controller: StatController,

): Table()
{
    private val statMap: Map<StatId, StatView> =
        stats.associate { it.id to StatView(it as StatUpgradeable, assets, controller) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padLeft(5f).padRight(5f).padBottom(7f)

        add(StatHeaderView(assets)).row()

        statMap.values
            .sortedBy { it.stat.id.value }
            .forEach { add(it).left().bottom().row() }

        setBackground(assets.tableBackgroundLight)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        statMap.forEach { it.value.update() }
    }
}
