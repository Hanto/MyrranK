package com.myrran.domain.skills.templates.stat

import com.myrran.domain.skills.skills.stat.Stat
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName

sealed interface StatTemplate {

    val id: StatId
    val name: StatName
    val baseBonus: StatBonus
    fun toStat(): Stat

}
