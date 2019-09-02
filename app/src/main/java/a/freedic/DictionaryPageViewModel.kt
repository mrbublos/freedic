package a.freedic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class DictionaryPageViewModel : ViewModel() {

    companion object {
        private val empty = VerbConjugation(Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word())
    }

    val translations = MutableLiveData<List<Translation>>()
    val verbs = MutableLiveData<List<VerbConjugation>>()
    val search = MutableLiveData<String>()

    private var searchJob : Job? = null

    fun searchTranslation(query: String) {
        if (query.isEmpty()) { return }

        searchJob?.cancel()
        searchJob = GlobalScope.launch(Dispatchers.IO) {
            val britannica = async {
                try {
                    TranslationService.getBritannicaTranslation(query)
                } catch (e: Exception) {
                    listOf<Translation>()
                }
            }
            val reverso = async {
                try {
                    TranslationService.getReversoTranslation(query)
                } catch (e: Exception) {
                    listOf<Translation>()
                }
            }
            val result = britannica.await() + reverso.await()
            launch(Dispatchers.Main) { translations.value = result }
        }
    }

    fun searchVerb(query: String) {
        if (query.isEmpty()) { return }

        searchJob?.cancel()
        searchJob = GlobalScope.launch(Dispatchers.IO) {
            val result = try {
                VerbService.queryData(query)
            } catch (e: Exception) {
                listOf<VerbConjugation>()
            }
            launch(Dispatchers.Main) { verbs.value = result }
        }
    }
}

