package com.myrran.infraestructure

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillBookAdapter
import com.myrran.infraestructure.entities.WorldSkillBookEntity
import com.myrran.view.atlas.AssetsCollection

class Repository(

    private val skillBookAdapter: SkillBookAdapter,
    private val deSerializer: DeSerializer
)
{
    companion object {
        const val CONFIG_FOLDER = "config/"
    }

    fun loadSkillBook(): WorldSkillBook {

        val json = Gdx.files.internal("${CONFIG_FOLDER}WorldSkillBook.json").readString()
        val entity = deSerializer.deserialize(json, WorldSkillBookEntity::class.java)
        return skillBookAdapter.toDomain(entity)
    }

    fun loadAssetCollection(assetsName: String): AssetsCollection {

        val json = Gdx.files.internal("$CONFIG_FOLDER$assetsName").readString()
        return deSerializer.deserialize(json, AssetsCollection::class.java)
    }

    fun saveSkillBook(worldSkillBook: WorldSkillBook) {

        val entity = skillBookAdapter.fromDomain(worldSkillBook)
        val json = deSerializer.serialize(entity)
        Gdx.files.local("WorldSkillBook2.json").writeString(json, false)
    }
}
