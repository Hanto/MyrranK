package com.myrran.view.ui.skill

import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.view.ui.skill.controller.BuffSKillController
import kotlin.reflect.KClass

class BuffSkillSlotView(

    val buffSkillSlot: BuffSkillSlot,
    val controller: BuffSKillController
) {

    init {

        buffSkillSlot.content.ifIs(BuffSkill::class)
            ?.also { controller.buffSkillUpgrade(StatId("DAMAGE"), NumUpgrades(7)) }

    }

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
