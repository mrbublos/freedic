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
        searchJob = GlobalScope.launch(Dispatchers.IO) {
            val britannica = async { TranslationService.getBritannicaTranslation(query) }
            val reverso = async { TranslationService.getReversoTranslation(query) }
            val result = britannica.await() + reverso.await()
            launch(Dispatchers.Main) { translations.value = result }
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

