package com.myrran.view.ui.skill

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.view.ui.TextView
import com.myrran.view.ui.skill.assets.SkillAssets

class SkillHeader(

    val skill: Skill,
    val assets: SkillAssets,

): Table()
{
    private val icon = Image(assets.skillIcon)
    private val name = TextView(skill.name.value, assets.font20, Color.ORANGE, 2f)

    init {

        left()

        setBackground(assets.tableBackgroundLight.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))

        add(icon).left()
        add(name).left().padLeft(4f).padBottom(-6f)
    }
}
