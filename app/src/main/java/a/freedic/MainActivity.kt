package a.freedic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

//        search.setOnKeyListener { _, _, _ ->
//            model.autocomplete(search.text.toString())
//            true
//        }

        clear.setOnClickListener {
            search.setText("")
        }

        pager.adapter = PagerAdapter(supportFragmentManager)
        pager.currentItem = 0

        progress.visibility = View.GONE
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