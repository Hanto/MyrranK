package com.myrran.infraestructure.assetsconfig

import com.badlogic.gdx.Gdx
import com.myrran.domain.utils.DeSerializer
import com.myrran.view.atlas.AssetsCollection

class AssetsConfigRepository(

    private val deSerializer: DeSerializer
)
{
    companion object {
        const val CONFIG_FOLDER = "config/"
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun loadAssetCollection(assetsName: String): AssetsCollection {

        val json = Gdx.files.internal("$CONFIG_FOLDER$assetsName").readString()
        return deSerializer.deserialize(json, AssetsCollection::class.java)
    }
}
