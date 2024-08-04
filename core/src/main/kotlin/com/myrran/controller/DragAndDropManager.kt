package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.myrran.view.ui.skills.SkillViewId

class DragAndDropManager(

    private val buffDaDs: DragAndDrop,
    private val mapBuffDaDs: MutableMap<SkillViewId, BuffDaDSource> = mutableMapOf()
)
{
    fun addSource(buffSource: BuffDaDSource) {

        buffDaDs.addSource(buffSource)
        mapBuffDaDs[buffSource.id] = buffSource
    }

    fun addTarget(buffTarget: BuffDaDTarget) {

        buffDaDs.addTarget(buffTarget)
    }

    fun removeSource(id: SkillViewId) {

        val source = mapBuffDaDs.remove(id)
        buffDaDs.removeSource(source)
    }

    fun removeTarget(buffTarget: BuffDaDTarget) {

        buffDaDs.removeTarget(buffTarget)
    }
}
