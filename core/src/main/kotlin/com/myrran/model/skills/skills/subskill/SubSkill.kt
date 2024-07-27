package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlot
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlots
import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.spells.subspells.SubSkillType
import com.myrran.model.spells.subspells.SubSpell

data class SubSkill(

    val id: SubSkillId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Stats,
    val slots: BuffSkillSlots

): SubSkillSlotContent
{
    fun createSpell(): SubSpell =

        type.builder.invoke(this)

    fun getStat(statId: StatId): Stat =

        stats.getStat(statId)!!

    // BDEBUFFSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slots.getBuffSkillSlots()

    fun getBuffSkills(): Collection<BuffSkill> =

        slots.getBuffSkills()

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
