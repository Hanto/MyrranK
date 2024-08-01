package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class SkillHeader(

    val skill: Skill,
    val assets: SkillAssets,
): Table()
{
    private val name = TextView(skill.name.value, assets.font20, Color.ORANGE, 2f)

    init {

        left().padLeft(8f)

        setBackground(assets.tableBackgroundLight.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))

        add(name)
    }
}
