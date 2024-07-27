package com.myrran.domain.skills.skills.buffskill

import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.stat.StatId
import com.myrran.domain.skills.stat.UpgradeCost
import com.myrran.domain.skills.stat.Upgrades
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockI

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

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        when (val buffSkill = content) {

            is BuffSkill -> buffSkill.upgrade(statId, upgradeBy)
            NoBuffSkill -> Unit
        }

    fun totalCost(): UpgradeCost =

        when (val buffSkill = content) {

            is BuffSkill -> buffSkill.totalCost()
            NoBuffSkill -> UpgradeCost.ZERO
        }
}
