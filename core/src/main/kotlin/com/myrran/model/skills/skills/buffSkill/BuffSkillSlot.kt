package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockI

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
