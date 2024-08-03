package com.myrran.view.ui.skills.custom.stat

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.controller.StatController
import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.utils.observer.Observable
import com.myrran.domain.utils.observer.Observer
import com.myrran.view.ui.skills.assets.SkillViewAssets

class StatsView(

    private val observable: Observable,
    private val statsRetriever: () -> Collection<Stat>,
    private val assets: SkillViewAssets,
    private val controller: StatController,

): Table(), Observer
{
    private var statList = statsRetriever.invoke().map { StatView(observable, it, assets, controller) }

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

    override fun propertyChange(event: Event) {

        if (event is BuffSkillChangedEvent || event is SubSkillChangedEvent) {

            statList = statsRetriever.invoke().map { StatView(observable, it, assets, controller) }
            reBuildTable()
        }
    }
}
