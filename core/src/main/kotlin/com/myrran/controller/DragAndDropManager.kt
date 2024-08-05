package com.myrran.controller

import com.myrran.badlogic.DaD
import com.myrran.domain.utils.MutableMapOfLists
import com.myrran.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val buffDaDs: DaD,
    private val subDaDs: DaD,
    private val buffSources: MutableMapOfLists<SkillViewId, BuffDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val buffTargets: MutableMapOfLists<SkillViewId, BuffDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val subSources: MutableMapOfLists<SkillViewId, SubDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val subTargets: MutableMapOfLists<SkillViewId, SubDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
)
{
    fun addSource(dadSource: BuffDaDSource) {

        buffDaDs.addSource(dadSource.getSource())
        buffSources[dadSource.id] = dadSource
    }

    fun addSource(dadSource: SubDaDSource) {

        subDaDs.addSource(dadSource.getSource())
        subSources[dadSource.id] = dadSource
    }

    fun removeSource(id: SkillViewId) {

        buffSources.remove(id)
            ?.forEach { buffDaDs.removeSource(it.getSource()) }

        subSources.remove(id)
            ?.forEach { subDaDs.removeSource(it.getSource()) }
    }

    fun addTarget(dadTarget: BuffDaDTarget) {

        buffDaDs.addTarget(dadTarget.getTarget())
        buffTargets[dadTarget.id] = dadTarget
    }

    fun addTarget(dadTarget: SubDaDTarget) {

        subDaDs.addTarget(dadTarget.getTarget())
        subTargets[dadTarget.id] = dadTarget
    }

    fun removeTarget(id: SkillViewId) {

        buffTargets.remove(id)
            ?.forEach { buffDaDs.removeTarget(it.getTarget()) }

        subTargets.remove(id)
            ?.forEach { subDaDs.removeTarget(it.getTarget()) }

    }
}
