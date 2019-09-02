package a.freedic

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.translations_list.*

class VerbFragment : PagerFragment(R.layout.translations_list) {

    private lateinit var model : DictionaryPageViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: VerbListAdapter

    override fun init() {
        model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)

        layoutManager = LinearLayoutManager(context)
        adapter = VerbListAdapter(mutableListOf())

        translations.layoutManager = layoutManager
        translations.adapter = adapter

        val verbObserver = Observer<List<VerbConjugation>> {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        }

        model.verbs.observe(this, verbObserver)
    }

    override fun search(text: String) {
        super.search(text)
        model.searchVerb(text)
    }
}
