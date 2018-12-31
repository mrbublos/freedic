package a.freedic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var model : DictionaryPageViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: TranslationListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)

        layoutManager = LinearLayoutManager(this)
        adapter = TranslationListAdapter(mutableListOf())

        translations.layoutManager = layoutManager
        translations.adapter = adapter

        val translationObserver = Observer<List<Translation>> {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
            progress.visibility = View.GONE
            translations.visibility = View.VISIBLE
        }

        val autocompleteObserver = Observer<List<String>> {
            // TODO impl
        }

        model.translations.observe(this, translationObserver)
        model.autocomplete.observe(this, autocompleteObserver)

        search.setOnClickListener {
            model.search(search.text.toString())
            progress.visibility = View.VISIBLE
            translations.visibility = View.GONE
        }

        word.setOnKeyListener { _, _, _ ->
            model.autocomplete(search.text.toString())
            true
        }
    }
}
