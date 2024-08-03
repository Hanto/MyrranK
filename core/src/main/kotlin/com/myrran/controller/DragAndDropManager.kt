package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop

class DragAndDropManager(

    private val buffDaDs: DragAndDrop,
)
{
    fun addSource(buffSource: BuffDaDSource) {

        buffDaDs.addSource(buffSource)
    }

    fun addTarget(buffTarget: BuffDaDTarget) {

        buffDaDs.addTarget(buffTarget)
    }

    fun removeSource(buffSource: BuffDaDSource) {

        buffDaDs.removeSource(buffSource)
    }

    fun removeTarget(buffTarget: BuffDaDTarget) {

        buffDaDs.removeTarget(buffTarget)
    }
}
