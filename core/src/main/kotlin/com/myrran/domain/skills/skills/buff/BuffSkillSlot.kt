package com.myrran.domain.skills.skills.buff

import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockI
import kotlin.reflect.KClass

data class BuffSkillSlot(

    val id: BuffSkillSlotId,
    val name: BuffSkillSlotName,
    val lock: Lock,
    var content: BuffSkillSlotContent

): LockI by lock
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun removeBuffSkill(): BuffSkillSlotContent =

        content.also { content = NoBuffSkill }

    fun setBuffSkill(buffSkill: BuffSkill) =

        when (lock.isOpenedBy(buffSkill.keys)) {

            true -> content = buffSkill
            false -> Unit
        }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(BuffSkill::class)?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        content.ifIs(BuffSkill::class)?.statCost() ?: ZERO

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
