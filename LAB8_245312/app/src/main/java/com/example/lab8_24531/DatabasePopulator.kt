package com.example.lab8_24531.data.local

import android.content.Context
import com.example.lab8_24531.data.CharacterDb
import com.example.lab8_24531.LocationDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabasePopulator {

    fun populateDatabase(context: Context) {
        val db = AppDatabase.getDatabase(context)
        val characterDb = CharacterDb()
        val locationDb = LocationDb()

        CoroutineScope(Dispatchers.IO).launch {
            val characterEntities = characterDb.getAllCharacters().map {
                CharacterEntity(it.id, it.name, it.status, it.species, it.gender, it.image)
            }
            val locationEntities = locationDb.getAllLocations().map {
                LocationEntity(it.id, it.name, it.type, it.dimension)
            }

            db.characterDao().insertAll(characterEntities)
            db.locationDao().insertAll(locationEntities)
        }
    }
}
