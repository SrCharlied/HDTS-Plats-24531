package com.example.lab8_24531.data.repository

import android.content.Context
import com.example.lab8_24531.data.local.AppDatabase
import com.example.lab8_24531.data.local.CharacterEntity
import com.example.lab8_24531.data.remote.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository(private val context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val api = NetworkModule.api

    suspend fun getCharacters(): List<CharacterEntity> = withContext(Dispatchers.IO) {
        val local = db.characterDao().getAllCharacters()
        if (local.isNotEmpty()) {
            return@withContext local
        }

        // Si no hay data local, obtener desde internet
        val response = api.getCharacters().results
        val entities = response.map {
            CharacterEntity(it.id, it.name, it.status, it.species, it.gender, it.image)
        }
        db.characterDao().insertAll(entities)
        entities
    }

    suspend fun getCharacterById(id: Int): CharacterEntity? {
        return db.characterDao().getCharacterById(id)
    }
}
