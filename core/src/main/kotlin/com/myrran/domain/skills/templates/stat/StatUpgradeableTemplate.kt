package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.created.stat.BonusPerUpgrade
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatBonus
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.StatName
import com.myrran.domain.skills.created.stat.StatUpgradeable
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.created.stat.Upgrades

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
