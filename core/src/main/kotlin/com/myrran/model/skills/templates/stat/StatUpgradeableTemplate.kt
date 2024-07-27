package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.stat.BonusPerUpgrade
import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName
import com.myrran.model.skills.stat.StatUpgradeable
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

data class StatUpgradeableTemplate(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,
    val maximum: Upgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): StatTemplate
{
    override fun toStat(): Stat =

        StatUpgradeable(
            id = id,
            name = name,
            baseBonus = baseBonus,
            actual = Upgrades(0),
            maximum = maximum,
            upgradeCost = upgradeCost,
            bonusPerUpgrade = bonusPerUpgrade)

}
