package a.freedic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            (pager.adapter as PagerAdapter).current.search(search.text.toString())
            progress.visibility = View.VISIBLE
            pager.visibility = View.GONE
        }

        clear.setOnClickListener {
            search.setText("")
        }

        pager.adapter = PagerAdapter(supportFragmentManager)
        pager.currentItem = 0

        progress.visibility = View.GONE

        val model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)
        val observer = Observer<List<Translation>> {
            progress.visibility = View.GONE
            pager.visibility = View.VISIBLE
        }

        model.translations.observe(this, observer)
//        model.verbs.observe(this, observer)
    }
}

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    lateinit var current: PagerFragment

    override fun getItem(position: Int): PagerFragment {
        current = when (position % count) {
            1 -> TranslationsFragment()
            else -> VerbFragment()
        }
        return current
    }

    override fun getCount(): Int = 2
}