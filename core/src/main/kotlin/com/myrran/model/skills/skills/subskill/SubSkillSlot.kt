package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.UpgradeCost.Companion.ZERO
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.skills.SubSkillTemplate

class SubSkillSlot(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock,
    var content: SubSkillSlotContent

)
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun openedBy(keys: Collection<LockTypes>): Boolean =

        lock.isOpenedBy(keys)

    fun setBuffSkill(template: SubSkillTemplate) =

        when (lock.isOpenedBy(template.keys)) {

            true -> content = template.toSubSkill()
            false -> Unit
        }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        when (val subSkill = content) {

            is SubSkill -> subSkill.upgrade(statId, upgradeBy)
            NoSubSkill -> Unit
        }

    fun upgrade(slotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        when (val subSkill = content) {

            is SubSkill -> subSkill.upgrade(slotId, statId, upgradeBy)
            NoSubSkill -> Unit
        }

    fun totalCost(): UpgradeCost =

        when (val subSkill = content) {

            is SubSkill -> subSkill.totalCost()
            NoSubSkill -> ZERO
        }
}
