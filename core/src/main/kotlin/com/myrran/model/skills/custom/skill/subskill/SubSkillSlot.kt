package com.myrran.model.skills.custom.skill.subskill

import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillSlotId
import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.UpgradeCost.Companion.ZERO
import com.myrran.model.skills.custom.stat.Upgrades
import com.myrran.model.skills.lock.Lock
import com.myrran.model.skills.lock.LockTypes

class SubSkillSlot(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock,
    val content: SubSkill?

)
{
    fun totalCost(): UpgradeCost = content?.totalCost() ?: ZERO

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        content?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        content?.upgrade(slotId, statId, upgradeBy)

    fun openedBy(keys: Collection<LockTypes>): Boolean =

        lock.isOpenedBy(keys)
}
