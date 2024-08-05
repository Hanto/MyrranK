package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.SubSkillSlotContent
import com.myrran.domain.skills.custom.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.stat.UpgradeCost.Companion.ZERO
import com.myrran.domain.skills.lock.Lock
import com.myrran.domain.skills.lock.LockI
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
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

    fun getSubSkill(): SubSkill? =

        content.ifIs(SubSkill::class)

    fun removeSubSkill(): SubSkill? =

        content.ifIs(SubSkill::class).also { content = NoSubSkill }

    fun setSubSkill(subSkill: SubSkill) =

        when (lock.isOpenedBy(subSkill.keys)) {

            true -> content = subSkill
            false -> Unit
        }

    fun isSubSkillOpenedBy(subSkillTemplate: SubSkillTemplate): Boolean =

        lock.isOpenedBy(subSkillTemplate.keys)

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        content.ifIs(SubSkill::class)?.getBuffSkill(buffSkillSlotId)

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        content.ifIs(SubSkill::class)?.removeBuffSkill(buffSkillSlotId)

    fun removeAllBuffSkills(): Collection<BuffSkill> =

        content.ifIs(SubSkill::class)?.removeAllBuffSkills() ?: emptyList()

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        content.ifIs(SubSkill::class)?.setBuffSkill(buffSkillSlotId, buffSkill)

    fun isBuffSkillOpenedBy(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        content.ifIs(SubSkill::class)?.isOpenedBy(buffSkillSlotId, buffSkillTemplate) ?: false

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
