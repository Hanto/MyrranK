package com.myrran.view.ui.skill.stats

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.StatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.StatController

class StatsView(

    private val statsRetriever: () -> Collection<Stat>,
    private val assets: SkillAssets,
    private val controller: StatController,

): Table()
{
    private var statList = statsRetriever.invoke().map { StatView(it, assets, controller) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        top().left().padLeft(5f).padRight(5f).padBottom(7f)
        setBackground(assets.tableBackgroundLight)
        reBuildTable()
    }

    private fun reBuildTable() {

        clear()
        add(StatHeaderView(assets)).row()
        statList
            .sortedBy { it.stat.id.value }
            .forEach { add(it).left().bottom().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(event: SkillEvent) {

        when (event) {

            is StatUpgradedEvent -> statList.forEach { it.update() }
            is BuffSkillChangedEvent, is SubSkillChangedEvent -> {

                statList = statsRetriever.invoke().map { StatView(it, assets, controller) }
                reBuildTable()
            }
        }
    }
}
