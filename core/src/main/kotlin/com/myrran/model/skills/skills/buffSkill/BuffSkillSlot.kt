package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.BuffSkillTemplate
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockTypes

class BuffSkillSlot(

    val id: BuffSkillSlotId,
    val name: BuffSkillSlotName,
    val lock: Lock,
    var content: BuffSkillSlotContent
)
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun openedBy(keys: Collection<LockTypes>): Boolean =

        lock.isOpenedBy(keys)

    fun setBuffSkill(template: BuffSkillTemplate) =

        when (lock.isOpenedBy(template.keys)) {

            true -> content = template.toBuffSkill()
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
