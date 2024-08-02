package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.custom.stat.Stat
import com.myrran.domain.skills.custom.stat.StatBonus
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.StatName

sealed interface StatTemplate {

    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun toStat(): Stat

}
