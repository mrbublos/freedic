package a.freedic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class DictionaryPageViewModel : ViewModel() {

    companion object {
        private val empty = VerbConjugation(Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word(), Word())
    }

    val translations = MutableLiveData<List<Translation>>()
    val verb = MutableLiveData<List<VerbConjugation>>()
    val search = MutableLiveData<String>()
    val autocomplete = MutableLiveData<List<String>>()

    private var autocompleteJob : Job? = null
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
            launch(Dispatchers.Main) { verb.value = result }
        }
    }

    fun autocomplete(query: String) {
        if (query.isEmpty() || query.length < 3) { return }

        autocompleteJob?.cancel()
        autocompleteJob = GlobalScope.launch(Dispatchers.IO) {
            delay(500)
            val result = TranslationService.getBritannicaAutocomplete(query)
            launch(Dispatchers.Main) { autocomplete.value = result }
        }
    }
}

