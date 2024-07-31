package com.myrran.view.ui.skill

import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import kotlin.reflect.KClass

class SubSkillSlotView(

    val subSkillSlot: SubSkillSlot,
    val controller: SubSkillController

) {
    init {

        subSkillSlot.content.ifIs(SubSkill::class)?.getBuffSkillSlots()?.map {

            BuffSkillSlotView(it, controller.toBuffSkillController(it))
        }
    }

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
