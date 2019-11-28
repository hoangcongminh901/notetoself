package com.gamecodeschool.notetoself

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val mainActivity: MainActivity,
    private val noteList: List<Note>
) : RecyclerView.Adapter<NoteAdapter.ListItemHolder>() {

    inner class ListItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        internal var titleTxtView = view.findViewById<View>(R.id.textViewTitle) as TextView
        internal var descriptionTxtView =
            view.findViewById<View>(R.id.textViewDescription) as TextView
        internal var statusTxtView = view.findViewById<View>(R.id.textViewStatus) as TextView

        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mainActivity.showNote(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val note = noteList[position]
        holder.titleTxtView.text = note.title
        holder.descriptionTxtView.text = if (note.description!!.length > 15) {
            note.description!!.substring(0, 15)
        } else {
            note.description!!
        }

        when {
            note.idea -> holder.statusTxtView.text =
                mainActivity.resources.getString(R.string.idea_text)
            note.important -> holder.statusTxtView.text =
                mainActivity.resources.getString(R.string.important_text)
            note.todo -> holder.statusTxtView.text =
                mainActivity.resources.getString(R.string.todo_text)
        }
    }
}