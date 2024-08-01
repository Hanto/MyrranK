package com.myrran.view.ui.skill

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.view.ui.skill.assets.SkillAssets
import com.myrran.view.ui.skill.controller.SubSkillController
import kotlin.reflect.KClass

class SubSkillSlotView(

    private val subSkillSlot: SubSkillSlot,
    private val assets: SkillAssets,
    private val controller: SubSkillController

): Table()
{
    private val slotKeyView = SlotKeyView(subSkillSlot, assets)

    init {

        add(slotKeyView)
    }

    fun update() {

        slotKeyView.update()
    }

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
