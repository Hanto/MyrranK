package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.UpgradeCost.Companion.ZERO
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockI
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.skills.templates.skills.SubSkillTemplate

data class SubSkillSlot(

    val id: SubSkillSlotId,
    val name: SubSkillSlotName,
    val lock: Lock,
    var content: SubSkillSlotContent

): LockI by lock
{
    // CONTENT:
    //--------------------------------------------------------------------------------------------------------

    fun removeSubSkill(): SubSkillSlotContent =

        content.also { content = NoSubSkill }

    fun setSubSkill(template: SubSkillTemplate) =

        when (lock.isOpenedBy(template.keys)) {

            true -> content = template.toSubSkill()
            false -> Unit
        }

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        when (val subSkill = content) {

            is SubSkill -> subSkill.getBuffSkill(buffSkillSlotId)
            NoSubSkill -> NoBuffSkill
        }

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        when (val subSkill = content) {

            is SubSkill -> subSkill.removeBuffSkill(buffSkillSlotId)
            NoSubSkill -> NoBuffSkill
        }

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, template: BuffSkillTemplate) =

        when (val subSkill = content) {

            is SubSkill -> subSkill.setBuffSkill(buffSkillSlotId, template)
            NoSubSkill -> Unit
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
