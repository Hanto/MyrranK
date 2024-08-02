package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.custom.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockI
import kotlin.reflect.KClass

data class SubSkillSlot(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock,
    var content: SubSkillSlotContent

): LockI by lock
{
    // SUBSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun removeSubSkill(): SubSkillSlotContent =

        content.also { content = NoSubSkill }

    fun setSubSkill(subSkill: SubSkill) =

        when (lock.isOpenedBy(subSkill.keys)) {

            true -> content = subSkill
            false -> Unit
        }

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        content.ifIs(SubSkill::class)?.getBuffSkill(buffSkillSlotId) ?: NoBuffSkill

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        content.ifIs(SubSkill::class)?.removeBuffSkill(buffSkillSlotId) ?: NoBuffSkill

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        content.ifIs(SubSkill::class)?.setBuffSkill(buffSkillSlotId, buffSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(SubSkill::class)?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        content.ifIs(SubSkill::class)?.upgrade(slotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        content.ifIs(SubSkill::class)?.totalCost() ?: ZERO

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T> ): T? =

        if (this is T) this else null
}
