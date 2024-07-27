package com.myrran.model.skills.templates.stat

import com.myrran.model.skills.stat.Stat
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName

sealed interface TemplateStat {

    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun toStat(): Stat

}
