package com.example.lab8_24531.data.repository

import android.content.Context
import com.example.lab8_24531.data.local.AppDatabase
import com.example.lab8_24531.data.local.LocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class LocationRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val dao = db.locationDao()

    suspend fun getAllLocations(): List<LocationEntity> = withContext(Dispatchers.IO) {
        val localData = dao.getAllLocations()
        if (localData.isNotEmpty()) {
            return@withContext localData
        }

        val response = URL("https://rickandmortyapi.com/api/location").readText()
        val json = JSONObject(response)
        val results = json.getJSONArray("results")

        val locations = (0 until results.length()).map { i ->
            val item = results.getJSONObject(i)
            LocationEntity(
                id = item.getInt("id"),
                name = item.getString("name"),
                type = item.getString("type"),
                dimension = item.getString("dimension")
            )
        }

        dao.insertAll(locations)
        locations
    }

    suspend fun getLocationById(id: Int): LocationEntity? = withContext(Dispatchers.IO) {
        dao.getLocationById(id)
    }
}
