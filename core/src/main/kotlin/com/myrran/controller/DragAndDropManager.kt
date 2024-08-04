package com.myrran.controller

import com.myrran.badlogic.DaD
import com.myrran.domain.utils.MutableMapOfLists
import com.myrran.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val buffDaDs: DaD,
    private val sources: MutableMapOfLists<SkillViewId, DaDSource<SkillViewId>> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val targets: MutableMapOfLists<SkillViewId, DaDTarget<SkillViewId>> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
)
{
    fun addSource(dadSource: DaDSource<SkillViewId>) {

        buffDaDs.addSource(dadSource.getSource())
        sources[dadSource.id] = dadSource
    }

    fun removeSource(id: SkillViewId) {

        sources.remove(id)
            ?.forEach { buffDaDs.removeSource(it.getSource()) }
    }

    fun addTarget(dadTarget: DaDTarget<SkillViewId>) {

        buffDaDs.addTarget(dadTarget.getTarget())
        targets[dadTarget.id] = dadTarget
    }

    fun removeTarget(id: SkillViewId) {

        targets.remove(id)
            ?.forEach { buffDaDs.removeTarget(it.getTarget()) }
    }
}
