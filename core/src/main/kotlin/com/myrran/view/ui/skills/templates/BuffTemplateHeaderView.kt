package com.myrran.view.ui.skills.templates

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.assets.PURPLE_LIGHT
import com.myrran.view.ui.skills.assets.SkillViewAssets

class BuffTemplateHeaderView(

    private val buff: BuffSkillTemplate,
    private val assets: SkillViewAssets,

    ): Table()
{
    val icon = Image(assets.skillIcon)
    private val name = TextView(buff.name, assets.font20, Color.ORANGE, 2f) { it.value }
    private val description = TextView("DEBUFF", assets.font14, Color.WHITE, 1f)
    private val keys = TextView(buff.keys.map { it.value }, assets.font14, PURPLE_LIGHT, 2f) { it.joinToString( " ") }

    init {

        left()
        setBackground(assets.tableBackgroundLightToDark.tint(Color(0.6f, 0.6f, 0.6f, 0.90f)))

        val nameKeys = Table().bottom().left()
        nameKeys.add(name.align(Align.left)).left()
        nameKeys.add(keys.align(Align.right)).bottom().right().expandX().fillX().padLeft(3f)

        val descriptionNameKeysTable = Table().bottom().left()
        descriptionNameKeysTable.padLeft(3f)
        descriptionNameKeysTable.add(description.align(Align.left)).left().padBottom(-4f).padBottom(-2f).row()
        descriptionNameKeysTable.add(nameKeys).expandX().fillX().padTop(-4f)

        add(icon).left()
        add(descriptionNameKeysTable).right().expandX().fillX().padTop(-2f).padBottom(-2f).padRight(3f).row()
    }
}
