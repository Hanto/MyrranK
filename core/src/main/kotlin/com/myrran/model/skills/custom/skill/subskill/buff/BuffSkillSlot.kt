package com.myrran.model.skills.custom.skill.subskill.buff

import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.Upgrades
import com.myrran.model.skills.lock.Lock
import com.myrran.model.skills.lock.LockTypes
import com.myrran.model.skills.templates.BuffSkillTemplate

class BuffSkillSlot(

    val id: BuffSkillSlotId,
    val name: BuffSkillSlotName,
    val lock: Lock,
    var content: BuffSkill?
)
{
    fun totalCost(): UpgradeCost =

        content?.totalCost() ?: UpgradeCost.ZERO

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        content?.upgrade(statId, upgradeBy)

    fun openedBy(keys: Collection<LockTypes>): Boolean =

        lock.isOpenedBy(keys)

    fun setBuffSkill(template: BuffSkillTemplate) =

        when (lock.isOpenedBy(template.keys)) {

            true -> content = template.toBuffSkill()
            false -> Unit
        }
}
