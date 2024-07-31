package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatUpgradeable

class StatsView(

    stats: Collection<Stat>,
    assets: SkillAssets,
    controller: ( StatId, NumUpgrades) -> Unit,

): Table()
{
    private val statMap: Map<StatId, StatView> =
        stats.associate { it.id to StatView(it as StatUpgradeable, assets, controller) }

    init {

        top().left().padTop(7f)
        setBackground(assets.background)

        statMap.values
            .sortedBy { it.stat.id.value }
            .forEach { add(it).left().bottom().row() }
    }

    fun update() {

        statMap.forEach { it.value.update() }
    }
}
