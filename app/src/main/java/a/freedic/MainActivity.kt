package a.freedic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            (pager.adapter as PagerAdapter).getItem(pager.currentItem).search(search.text.toString())
            progress.visibility = View.VISIBLE
        }

        clear.setOnClickListener { search.setText("") }

        pager.adapter = PagerAdapter(supportFragmentManager)
        pager.currentItem = 0
        progress.visibility = View.GONE

        val model = ViewModelProviders.of(this).get(DictionaryPageViewModel::class.java)
        val observer = Observer<Any> { progress.visibility = View.GONE }

        model.translations.observe(this, observer)
        model.verbs.observe(this, observer)
    }
}

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = listOf(TranslationsFragment(), VerbFragment())

    override fun getItem(position: Int): PagerFragment {
        return fragments[position % fragments.size]
    }

    override fun getCount(): Int = 2
}