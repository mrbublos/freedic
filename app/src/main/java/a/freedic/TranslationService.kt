package a.freedic

import org.json.JSONObject
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

object TranslationService {

    private const val britannicaUrl = "http://services.britannicaenglish.com"
    private const val reversoUrl = "https://context.reverso.net"

    suspend fun getBritannicaTranslation(query: String): List<Translation> {
        val body = "{\"Query\":\"$query\"}"
        val result = HttpUtils.call4Json(HttpMethod.POST, "$britannicaUrl/translationhebrew/TranslationService/GetTranslation/", body)
        return result?.obj()
                ?.getJSONArray("Words")
                ?.map { word -> processWord(word) }
                ?: listOf()
    }

    suspend fun getBritannicaAutocomplete(query: String): List<String> {
        val encodedQuery = URLEncoder.encode(query, UTF_8.name())
        val result = HttpUtils.call4Json(HttpMethod.POST, "$britannicaUrl/AutoComplete/AutoComplete/GetHebrewAutoComplete/$encodedQuery")
        return result?.obj()?.getJSONArray("ResultsL2").toStringList()
    }

    suspend fun getReversoTranslation(query: String): List<Translation> {
        val encodedQuery = URLEncoder.encode(query, UTF_8.name())
        val result = HttpUtils.call4Json(HttpMethod.GET, "$reversoUrl/bst-query-service?source_text=$encodedQuery&source_lang=he&target_lang=en&npage=1&nrows=4&expr_sug=1&json=1&dym_apply=true&pos_reorder=5")
        return result?.obj()
                ?.getJSONArray("dictionary_entry_list")
                ?.map { entry ->
                    Translation(query, entry.getString("term"), "")
                } ?: listOf()
    }

    private fun processWord(word: JSONObject): Translation {
        val meanings = word.getJSONArray("InputLanguageMeanings")
        var soundUrl = ""
        val header = meanings.mapArr { arr ->
            arr.map { meaning ->
                if (!meaning.getString("SoundURL").isNullOrEmpty()) { soundUrl = meaning.getString("SoundURL") }
                meaning.getString("DisplayText")
            }.joinToString(",")
        }.joinToString(",")
        val translation = word.getString("OutputLanguageMeaningsString").replace(";", "\n")
        return Translation(header, translation, soundUrl)
    }
}