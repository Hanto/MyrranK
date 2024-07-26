package com.myrran.model.skills.custom.skill

import com.myrran.model.skills.custom.skill.subskill.SubSkill
import com.myrran.model.skills.custom.skill.subskill.SubSkillSlotId
import com.myrran.model.skills.custom.skill.subskill.SubSkillSlots
import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillSlotId
import com.myrran.model.skills.custom.stat.Stat
import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.Stats
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.Upgrades
import com.myrran.model.spells.spell.SkillType
import com.myrran.model.spells.spell.Spell

data class Skill(

    val id: SkillId,
    val type: SkillType,
    val name: SkillName,
    val stats: Stats,
    val slots: SubSkillSlots

)
{
    fun createSpell(): Spell =

        type.builder.invoke(this)

    fun getStat(statId: StatId): Stat =

        stats.getStat(statId)!!

    fun getSlot(subSkillSlotId: SubSkillSlotId): SubSkill? =

        slots.getSlot(subSkillSlotId)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        stats.upgrade(statId, upgradeBy)

    fun upgrade(subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, statId, upgradeBy)

    fun upgrade(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()

    fun totalCost(): UpgradeCost {

        val baseCost = stats.totalCost()
        val slotCost = slots.totalCost()

        return baseCost.sum(slotCost)
    }
}
