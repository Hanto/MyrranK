package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSlotKeyView

class BuffDaDTarget(

    private val buffSlot: BuffSlotKeyView,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

) : Target(buffSlot)
{
    override fun drag(source: Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean = true

    override fun drop(source: Source, payload: Payload, x: Float, y: Float, pointer: Int) {


        println("DROPED: $buffSlot")
        buffSlot.setLockOpenable()
    }
}
