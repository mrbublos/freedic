package a.freedic

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

fun String?.log() {
    this?.takeIf { isNotEmpty() }?.let {
        Log.v("afreeDic", this)
    }
}

fun String?.logE(e: Throwable? = null) {
    this?.takeIf { isNotEmpty() }?.let {
        Log.e("afreeDic", this, e)
    }
}

fun JSONArray?.toStringList(): MutableList<String> {
    val length = this?.length() ?: 0
    val result = mutableListOf<String>()
    for (i in 0..length) {
        this?.get(i)?.toString()?.let {
            result.add(it)
        }
    }
    return result
}

fun <R> JSONArray.map(action: (obj: JSONObject) -> R): MutableList<R>  {
    return (0 until this.length()).map {
        action(this.get(it) as JSONObject)
    }.toMutableList()
}

fun <R> JSONArray.mapArr(action: (obj: JSONArray) -> R): MutableList<R>  {
    return (0 until this.length()).map {
        action(this.get(it) as JSONArray)
    }.toMutableList()
}

fun Word.displayString(addTranslation: Boolean = false): String {
    return "$hebrew\n$transcription${if (addTranslation) "\n" + translation else ""}"
}