package a.freedic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.translation.view.*

class VerbListAdapter(val items : MutableList<VerbConjugation>) : RecyclerView.Adapter<VerbView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerbView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.verb, parent,false)
        return VerbView(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VerbView, position: Int) {
    }

}

class VerbView(val view: View) : RecyclerView.ViewHolder(view)