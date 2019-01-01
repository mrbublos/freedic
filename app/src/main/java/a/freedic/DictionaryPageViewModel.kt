package a.freedic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class DictionaryPageViewModel : ViewModel() {

    val translations = MutableLiveData<List<Translation>>()
    val search = MutableLiveData<String>()
    val autocomplete = MutableLiveData<List<String>>()

    private var autocompleteJob : Job? = null
    private var searchJob : Job? = null

    fun search(query: String) {
        if (query.isEmpty()) { return }

        searchJob?.cancel()
        searchJob = GlobalScope.launch {
            val britannica = async { TranslationService.getBritannicaTranslation(query) }
            val reverso = async { TranslationService.getReversoTranslation(query) }
            translations.value = britannica.await() + reverso.await()
        }
    }

    fun autocomplete(query: String) {
        if (query.isEmpty() || query.length < 3) { return }

        autocompleteJob?.cancel()
        autocompleteJob = GlobalScope.launch {
            delay(500)
            autocomplete.value = TranslationService.getBritannicaAutocomplete(query)
        }
    }
}

