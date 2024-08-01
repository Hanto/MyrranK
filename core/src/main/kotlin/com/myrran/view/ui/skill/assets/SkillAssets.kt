package com.myrran.view.ui.skill.assets

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

data class SkillAssets(

    val spellIconBackground: NinePatchDrawable,
    val tableBackgroundDark: NinePatchDrawable,
    val tableBackgroundLight: NinePatchDrawable,
    val font20: BitmapFont,
    val font14: BitmapFont,
    val font12: BitmapFont,
    val font10: BitmapFont,
    val statBarBack: TextureRegion,
    val statBarFront: TextureRegion
)
