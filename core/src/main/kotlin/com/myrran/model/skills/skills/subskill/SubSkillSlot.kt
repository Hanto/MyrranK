package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.bdebuff.BuffSkillSlotId
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.UpgradeCost.Companion.ZERO
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockTypes

class SubSkillSlot(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock,
    val content: SubSkill?

)
{
    fun openedBy(keys: Collection<LockTypes>): Boolean =

        lock.isOpenedBy(keys)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        content?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        content?.upgrade(slotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost = content?.totalCost() ?: ZERO
}
