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

    init {

        top().left().padLeft(3f).padRight(3f).padTop(2f).padBottom(2f)

        add(StatHeaderView(assets)).row()

        statMap.values
            .sortedBy { it.stat.id.value }
            .forEach { add(it).left().bottom().row() }
    }

    fun update() {

        statMap.forEach { it.value.update() }
    }
}
