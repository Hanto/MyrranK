package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.stat.BonusPerUpgrade
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

data class TemplateStatUpgradeable(

    override val id: StatId,
    override val name: StatName,
    override val baseBonus: StatBonus,
    val maximum: Upgrades,
    val upgradeCost: UpgradeCost,
    val bonusPerUpgrade: BonusPerUpgrade

): TemplateStat
