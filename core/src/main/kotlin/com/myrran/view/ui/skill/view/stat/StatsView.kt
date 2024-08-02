package com.myrran.view.ui.skill.view.stat

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.utils.observer.Observable
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.skill.assets.SkillViewAssets
import com.myrran.view.ui.skill.controller.StatController

class StatsView(

    private val observable: Observable,
    private val statsRetriever: () -> Collection<Stat>,
    private val assets: SkillViewAssets,
    private val controller: StatController,

): Table(), Observer
{
    private var statList = statsRetriever.invoke().map { StatView(observable, it, assets, controller) }

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        observable.addObserver(this)
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

    override fun update(event: Event) {

        if (event is BuffSkillChangedEvent || event is SubSkillChangedEvent) {

            statList = statsRetriever.invoke().map { StatView(observable, it, assets, controller) }
            reBuildTable()
        }
    }
}
