package com.myrran.infraestructure.repositories.assetsconfig

import com.badlogic.gdx.Gdx
import com.myrran.domain.misc.DeSerializer
import com.myrran.infraestructure.assets.AssetsCollection

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
