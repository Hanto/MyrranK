package com.myrran.model.skills.custom.skill.subskill

import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkill
import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillSlotId
import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkillSlots
import com.myrran.model.skills.custom.stat.Stat
import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.Stats
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.Upgrades
import com.myrran.model.spells.subspells.SubSkillType
import com.myrran.model.spells.subspells.SubSpell

data class SubSkill(

    val id: SubSkillId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Stats,
    val slots: BuffSkillSlots

)
{
    fun createSpell(): SubSpell =

        type.builder.invoke(this)

    fun getStat(statId: StatId): Stat =

        stats.getStat(statId)!!

    fun getSlots(): Collection<BuffSkill> =

        slots.getSlots()

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        stats.upgrade(statId, upgradeBy)

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()

    fun totalCost(): UpgradeCost {

        val baseCost = stats.totalCost()
        val slotCost = slots.totalCost()

        return baseCost.sum(slotCost)
    }
}
