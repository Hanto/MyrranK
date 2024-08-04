package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.myrran.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val buffDaDs: DragAndDrop,
    private val sources: MutableMap<SkillViewId, BuffDaDSource> = mutableMapOf(),
    private val targets: MutableMap<SkillViewId, BuffDaDTarget> = mutableMapOf()
)
{
    fun addSource(buffSource: BuffDaDSource) {

        buffDaDs.addSource(buffSource)
        sources[buffSource.id] = buffSource
    }

    fun removeSource(id: SkillViewId) {

        val source = sources.remove(id)
        buffDaDs.removeSource(source)
    }

    fun addTarget(buffTarget: BuffDaDTarget) {

        targets[buffTarget.id] = buffTarget
        buffDaDs.addTarget(buffTarget)
    }

    fun removeTarget(id: SkillViewId) {

        val target = targets.remove(id)
        buffDaDs.removeTarget(target)
    }
}
