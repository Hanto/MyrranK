package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.custom.stat.BonusPerUpgrade
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.stat.StatBonus
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.StatName
import com.myrran.domain.skills.custom.stat.StatUpgradeable
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.custom.stat.Upgrades

data class StatUpgradeableTemplate(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,
    val maximum: NumUpgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): StatTemplate
{
    override fun toStat(): Stat =

        StatUpgradeable(
            id = id,
            name = name,
            baseBonus = baseBonus,
            upgrades = Upgrades(
                actual = NumUpgrades(0),
                maximum = maximum),
            upgradeCost = upgradeCost,
            bonusPerUpgrade = bonusPerUpgrade)
}
