package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView

class BuffDaDTarget(

    private val buffSlot: BuffSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

) : Target(buffSlot.buffSlotKeyView)
{
    override fun drag(source: Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: Source, payload: Payload, x: Float, y: Float, pointer: Int) {

        buffSlot.buffSlotKeyView.setLockOpenable()
        val template = payload.`object` as BuffSkillTemplate
        val subSkill = template.toBuffSkill()

        controller.addBuffSkill(subSkill)
    }
}
