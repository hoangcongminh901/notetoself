package com.gamecodeschool.notetoself

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogShowNote : DialogFragment() {

    private var note: Note? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity!!)

        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_note, null)

        val textViewImportant = dialogView.findViewById(R.id.txtViewImportant) as TextView
        val textViewTodo = dialogView.findViewById(R.id.txtViewTodo) as TextView
        val textViewIdea = dialogView.findViewById(R.id.txtViewIdea) as TextView
        val txtTitle = dialogView.findViewById(R.id.txtTitle) as TextView
        val txtDescription = dialogView.findViewById(R.id.txtDescription) as TextView

        txtTitle.text = note!!.title
        txtDescription.text = note!!.description
        if (!note!!.important) textViewImportant.visibility = View.GONE
        if (!note!!.todo) textViewTodo.visibility = View.GONE
        if (!note!!.idea) textViewIdea.visibility = View.GONE

        val btnOK = dialogView.findViewById(R.id.btnOk) as Button

        btnOK.setOnClickListener {
            dismiss()
        }

        builder.setView(dialogView).setMessage("Your note")

        return builder.create()
    }

    fun sendSelectedNote(selectedNote: Note?) {
        note = selectedNote
    }

}