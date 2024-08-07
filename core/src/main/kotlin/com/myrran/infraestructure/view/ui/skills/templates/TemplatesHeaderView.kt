package com.myrran.infraestructure.view.ui.skills.templates

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.view.ui.misc.TextView

class TemplatesHeaderView(

    private val name: String,
    private val assets: SkillViewAssets,

): Table()
{
    private val headerName: TextView<String> = TextView(name, assets.font20, Color.WHITE, 2f)

    init {

        touchable = Touchable.enabled
        top().left()

        setBackground(assets.tableBackgroundLightToDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))
        add(headerName.align(Align.left)).minHeight(33f).padLeft(8f).left()
    }
}
