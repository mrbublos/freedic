package a.freedic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.verb.view.*

class VerbListAdapter(val items : MutableList<VerbConjugation>) : RecyclerView.Adapter<VerbView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerbView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.verb, parent,false)
        return VerbView(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VerbView, position: Int) {
        val item = items[position]
        holder.view.prfp.text = item.pluralF.displayString()
        holder.view.prmp.text = item.pluralM.displayString()
        holder.view.prms.text = item.singularM.displayString()
        holder.view.prfs.text = item.singularF.displayString()
        holder.view.pa1s.text = item.past1Singular.displayString()
        holder.view.pa1p.text = item.past1Plural.displayString()
        holder.view.pa2ms.text = item.past2SingularM.displayString()
        holder.view.pa2fs.text = item.past2SingularF.displayString()
        holder.view.pa2mp.text = item.past2PluralM.displayString()
        holder.view.pa2fp.text = item.past2PluralF.displayString()
        holder.view.pa3ms.text = item.past3SingularM.displayString()
        holder.view.pa3fs.text = item.past3SingularF.displayString()
        holder.view.pa3p.text = item.past3Plural.displayString()
        holder.view.fu1s.text = item.future1Singular.displayString()
        holder.view.fu1p.text = item.future1Plural.displayString()
        holder.view.fu2ms.text = item.future2SingularM.displayString()
        holder.view.fu2fs.text = item.future2SingularF.displayString()
        holder.view.fu2mp.text = item.future2PluralM.displayString()
        holder.view.fu2fp.text = item.future2PluralF.displayString()
        holder.view.fu3ms.text = item.future3SingularM.displayString()
        holder.view.fu3fs.text = item.future3SingularF.displayString()
        holder.view.fu3mp.text = item.future3PluralM.displayString()
        holder.view.fu3fp.text = item.future3PluralF.displayString()
        holder.view.imms.text = item.imperativeSingularM.displayString()
        holder.view.imfs.text = item.imperativeSingularF.displayString()
        holder.view.immp.text = item.imperativePluralM.displayString()
        holder.view.imfp.text = item.imperativePluralF.displayString()
        holder.view.infinitive.text = item.infinitive.displayString(true)
    }
}

class VerbView(val view: View) : RecyclerView.ViewHolder(view)