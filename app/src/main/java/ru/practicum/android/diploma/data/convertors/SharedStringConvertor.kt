package ru.practicum.android.diploma.data.convertors



import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.entity.FilterShared

class SharedStringConvertor(
    private val gson: Gson,
) {

    fun getFilterShared(sharedString: String?): FilterShared? {
        return safeFromJson(sharedString, object : TypeToken<FilterShared>() {})
    }

    fun getStringForShared(filterShared: FilterShared?): String? {
        return gson.toJson(filterShared)
    }

    private fun <T> safeFromJson(json: String?, typeOfT: TypeToken<T>): T? {
        return try {
            gson.fromJson(json, typeOfT.type)
        } catch (e: JsonSyntaxException) {
            Log.e("SharedStringConvertor", "JsonSyntaxException, $e")
            null
        }
    }
}
