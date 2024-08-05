package com.myrran.controller

import com.myrran.badlogic.DaD
import com.myrran.domain.utils.MutableMapOfLists
import com.myrran.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val buffDaDs: DaD<BuffDaDSource, BuffDaDTarget>,
    private val subDaDs: DaD<SubDaDSource, SubDaDTarget>,
    private val buffSources: MutableMapOfLists<SkillViewId, BuffDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val buffTargets: MutableMapOfLists<SkillViewId, BuffDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val subSources: MutableMapOfLists<SkillViewId, SubDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val subTargets: MutableMapOfLists<SkillViewId, SubDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
)
{
    fun addSource(dadSource: BuffDaDSource) {

        buffDaDs.addSource(dadSource)
        buffSources[dadSource.id] = dadSource
    }

    fun addSource(dadSource: SubDaDSource) {

        subDaDs.addSource(dadSource)
        subSources[dadSource.id] = dadSource
    }

    fun removeSource(id: SkillViewId) {

        buffSources.remove(id)
            ?.forEach { buffDaDs.removeSource(it) }

        subSources.remove(id)
            ?.forEach { subDaDs.removeSource(it) }
    }

    fun addTarget(dadTarget: BuffDaDTarget) {

        buffDaDs.addTarget(dadTarget)
        buffTargets[dadTarget.id] = dadTarget
    }

    fun addTarget(dadTarget: SubDaDTarget) {

        subDaDs.addTarget(dadTarget)
        subTargets[dadTarget.id] = dadTarget
    }

    fun removeTarget(id: SkillViewId) {

        buffTargets.remove(id)
            ?.forEach { buffDaDs.removeTarget(it) }

        subTargets.remove(id)
            ?.forEach { subDaDs.removeTarget(it) }

    }
}
