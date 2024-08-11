package com.myrran.domain.mob.metricunits

import org.junit.jupiter.api.Test

class DegreeTest {

    @Test
    fun pimt()
    {
        val degree = Degree(180f + 360f);

        println(degree.toRadians().toFloat())
        println(degree.toRadians().toDegrees().toFloat())
    }
}
