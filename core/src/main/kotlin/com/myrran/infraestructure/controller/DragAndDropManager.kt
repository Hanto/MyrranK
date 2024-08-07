package com.myrran.infraestructure.controller

import com.myrran.badlogic.DaD
import com.myrran.domain.misc.MutableMapOfLists
import com.myrran.infraestructure.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val effectDaDs: DaD<EffectDaDSource, EffectDaDTarget>,
    private val formDaDs: DaD<FormDaDSource, FormDaDTarget>,
    private val effectSources: MutableMapOfLists<SkillViewId, EffectDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val effectTargets: MutableMapOfLists<SkillViewId, EffectDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val formSources: MutableMapOfLists<SkillViewId, FormDaDSource> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
    private val formTargets: MutableMapOfLists<SkillViewId, FormDaDTarget> = MutableMapOfLists({ mutableMapOf() }, { mutableListOf() }),
)
{
    fun addSource(dadSource: EffectDaDSource) {

        effectDaDs.addSource(dadSource)
        effectSources[dadSource.id] = dadSource
    }

    fun addSource(dadSource: FormDaDSource) {

        formDaDs.addSource(dadSource)
        formSources[dadSource.id] = dadSource
    }

    fun removeSource(id: SkillViewId) {

        effectSources.remove(id)
            ?.forEach { effectDaDs.removeSource(it) }

        formSources.remove(id)
            ?.forEach { formDaDs.removeSource(it) }
    }

    fun addTarget(dadTarget: EffectDaDTarget) {

        effectDaDs.addTarget(dadTarget)
        effectTargets[dadTarget.id] = dadTarget
    }

    fun addTarget(dadTarget: FormDaDTarget) {

        formDaDs.addTarget(dadTarget)
        formTargets[dadTarget.id] = dadTarget
    }

    fun removeTarget(id: SkillViewId) {

        effectTargets.remove(id)
            ?.forEach { effectDaDs.removeTarget(it) }

        formTargets.remove(id)
            ?.forEach { formDaDs.removeTarget(it) }

    }
}
