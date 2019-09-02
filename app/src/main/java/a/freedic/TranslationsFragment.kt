package a.freedic

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.translations_list.*

class TranslationsFragment : PagerFragment(R.layout.translations_list) {

    private lateinit var model : DictionaryPageViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: TranslationListAdapter


    override fun init() {
        model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)

        layoutManager = LinearLayoutManager(context)
        adapter = TranslationListAdapter(mutableListOf())

        translations.layoutManager = layoutManager
        translations.adapter = adapter

        val translationObserver = Observer<List<Translation>> {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        }

        model.translations.observe(this, translationObserver)
    }

    override fun search(text: String) {
        super.search(text)
        model.searchTranslation(text)
    }
}
