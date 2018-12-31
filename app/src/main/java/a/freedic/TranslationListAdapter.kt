package a.freedic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.translation.view.*

class TranslationListAdapter(val items : MutableList<Translation>) : RecyclerView.Adapter<TranslationView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.translation, parent,false)
        return TranslationView(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TranslationView, position: Int) {
        holder.view.translations.setText(items[position].translation)
        holder.view.word.text = items[position].displayText
        holder.view.sound.visibility = if (items[position].soundUrl.isEmpty()) View.INVISIBLE else View.VISIBLE
        // TODO implement sound onclick
    }

}

class TranslationView(val view: View) : RecyclerView.ViewHolder(view)