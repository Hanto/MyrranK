package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.custom.stat.BonusPerUpgrade
import com.myrran.model.skills.custom.stat.StatBonus
import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.StatName
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.Upgrades

data class TemplateStatUpgradeable(

    val id: StatId,
    val name: StatName,
    val baseBonus: StatBonus,
    val maximum: Upgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): TemplateStat
