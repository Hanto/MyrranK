package com.myrran.controller

import com.badlogic.gdx.graphics.Color
import com.myrran.badlogic.Payload
import com.myrran.badlogic.Source
import com.myrran.badlogic.Target
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.custom.buff.BuffSkillSlotView

class BuffDaDTarget(

    private val view: BuffSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController,

) : Target(view.buffSlotKeyView), DaDTarget<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getTarget(): Target = this

    override fun drag(source: Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: Source, payload: Payload, x: Float, y: Float, pointer: Int) {

        val template = payload.`object` as BuffSkillTemplate

        controller.addBuffSkill(template)
    }

    override fun notifyNewPayload(payload: Payload) =

        when (controller.isOpenedBy(payload.`object` as BuffSkillTemplate)) {
            true -> view.buffSlotKeyView.highlightWithColor(Color.GREEN)
            false -> view.buffSlotKeyView.highlightWithColor(Color.RED)
        }

    override fun notifyNoPayload() {

        view.buffSlotKeyView.dontHighlight()
    }
}
