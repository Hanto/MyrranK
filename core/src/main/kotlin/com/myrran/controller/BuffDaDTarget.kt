package com.myrran.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.badlogic.DaDSource
import com.myrran.badlogic.DaDTarget
import com.myrran.badlogic.Payload
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.created.buff.BuffSkillSlotView

class BuffDaDTarget(

    private val view: BuffSkillSlotView,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController,

): DaDTarget, Identifiable<SkillViewId>
{
    override val id: SkillViewId = view.id
    override fun getActor(): Actor = view.buffSlotKeyView

    override fun drag(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int): Boolean =

        true

    override fun drop(source: DaDSource, payload: Payload, x: Float, y: Float, pointer: Int) {

        val template = payload.`object` as Quantity<BuffSkillTemplate>

        controller.setBuffSkill(template.value.id)
    }

    override fun notifyNewPayload(payload: Payload) =

        when (controller.isOpenedBy((payload.`object` as Quantity<BuffSkillTemplate>).value.id)) {
            true -> view.buffSlotKeyView.highlightWithColor(Color.GREEN)
            false -> view.buffSlotKeyView.highlightWithColor(Color.RED)
        }

    override fun notifyNoPayload() {

        view.buffSlotKeyView.dontHighlight()
    }
}
