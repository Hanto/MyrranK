package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades

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
