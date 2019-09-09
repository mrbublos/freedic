package a.freedic

import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object VerbService {

    private var lang = ""
    private const val search = "/search/?q="
    private const val base = "https://www.pealim.com"

    suspend fun queryData(query: String): List<VerbConjugation> {
        if (query.isEmpty()) { return listOf() }
        if ("абвгдеёжзийклмнопрстуфхцчшщъыьэюя".contains(query.toLowerCase()[0])) { lang = "/ru" }
        if ("qwertyuiopasdfghjklzxcvbnm".contains(query.toLowerCase()[0])) { lang = "/en" }

        return suspendCoroutine { continuation ->
            try {
                "Calling $base$lang$search$query".log()
                Jsoup.connect("$base$lang$search$query").get().run {
                    "Received ${this.toString()}".log()
                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            val result = getElementsByAttributeValueContaining("onclick", "/dict/").map {
                                GlobalScope.async(Dispatchers.IO) {
                                    queryVerb(it.attr("onclick").replace(Regex(".*\"(.*)\".*"), "$1"))
                                }
                            }.awaitAll()
                            continuation.resume(result)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    private suspend fun queryVerb(verb: String): VerbConjugation {
        return suspendCoroutine { continuation ->
            try {
                "Calling $base$verb".log()
                Jsoup.connect("$base$verb").get().run {
                    "Received ${this.toString()}".log()
                    val result = VerbConjugation(
                            singularM = getForm(this, "AP-ms"),
                            singularF = getForm(this, "AP-fs"),
                            pluralM = getForm(this, "AP-mp"),
                            pluralF = getForm(this, "AP-fp"),
                            past1Singular = getForm(this, "PERF-1s"),
                            past1Plural = getForm(this, "PERF-1p"),
                            past2SingularM = getForm(this, "PERF-2ms"),
                            past2SingularF = getForm(this, "PERF-2fs"),
                            past2PluralM = getForm(this, "PERF-2mp"),
                            past2PluralF = getForm(this, "PERF-2fp"),
                            past3SingularM = getForm(this, "PERF-3ms"),
                            past3SingularF = getForm(this, "PERF-3fs"),
                            past3Plural = getForm(this, "PERF-3p"),
                            future1Singular = getForm(this, "IMPF-1s"),
                            future1Plural = getForm(this, "IMPF-1p"),
                            future2SingularM = getForm(this, "IMPF-2ms"),
                            future2SingularF = getForm(this, "IMPF-2fs"),
                            future2PluralM = getForm(this, "IMPF-2mp"),
                            future2PluralF = getForm(this, "IMPF-2fp"),
                            future3SingularM = getForm(this, "IMPF-3ms"),
                            future3SingularF = getForm(this, "IMPF-3fs"),
                            future3PluralM = getForm(this, "IMPF-3mp"),
                            future3PluralF = getForm(this, "IMPF-3fp"),
                            imperativeSingularM = getForm(this, "IMP-2ms"),
                            imperativeSingularF = getForm(this, "IMP-2fs"),
                            imperativePluralM = getForm(this, "IMP-2mp"),
                            imperativePluralF = getForm(this, "IMP-2fp"),
                            infinitive = getForm(this, "INF-L")
                    )
                    continuation.resume(result)
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    // TODO see how fast is that
    private fun getForm(document: Document, id: String): Word {
        val translation = document.select("#$id>div.meaning>strong").text()
        return Word(hebrew = document.select("#$id>div>div>span.menukad").text(),
                transcription = document.select("#$id>div>div.transcription").text().replace(Regex("</?b>"), ""),
                translation = if (translation.isEmpty()) document.select("div.container>div.lead").text() else translation)
    }
}