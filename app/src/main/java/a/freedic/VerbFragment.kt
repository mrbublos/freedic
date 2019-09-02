package a.freedic

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.translations_list.*

class VerbFragment : PagerFragment(R.layout.translations_list) {

    private lateinit var model : DictionaryPageViewModel


    override fun init() {
        model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)

        val verbObserver = Observer<List<VerbConjugation>> {
            progress.visibility = View.GONE
            translations.visibility = View.VISIBLE
        }

        model.verb.observe(this, verbObserver)
    }

    override fun search(text: String) {
        super.search(text)
        model.searchVerb(text)
    }
}
