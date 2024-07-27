package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.spells.buffs.Buff
import com.myrran.model.spells.buffs.BuffType

data class BuffSkill(

    val id: BuffSkillId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Stats

)
{
    fun createDebuff(): Buff =

        type.builder.invoke(this)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(statId: StatId, upgradeBy: Upgrades) =

        stats.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost = stats.totalCost()
}
