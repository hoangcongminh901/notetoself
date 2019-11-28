package com.gamecodeschool.notetoself

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogNewNote : DialogFragment() {

    private lateinit var txtViewNewNote: TextView
    private lateinit var addTitle: EditText
    private lateinit var addDescription: EditText
    private lateinit var checkboxIdea: CheckBox
    private lateinit var checkboxTodo: CheckBox
    private lateinit var checkboxImportant: CheckBox
    private lateinit var btnCancel: Button
    private lateinit var btnOK: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity!!)

        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_new_note, null)

        findViews(dialogView)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOK.setOnClickListener {
            val note = Note()
            note.title = addTitle.text.toString()
            note.description = addDescription.text.toString()
            note.idea = checkboxIdea.isChecked
            note.todo = checkboxTodo.isChecked
            note.important = checkboxImportant.isChecked

            val callingActivity = activity as MainActivity?

            callingActivity!!.createNewNote(note)

            dismiss()
        }

        builder.setView(dialogView).setMessage("Add a new note")

        return builder.create()
    }

    private fun findViews(view: View?) {
        addTitle = view!!.findViewById(R.id.addTitle)
        addDescription = view.findViewById(R.id.addDescription)
        checkboxIdea = view.findViewById(R.id.checkboxIdea)
        checkboxTodo = view.findViewById(R.id.checkboxTodo)
        checkboxImportant = view.findViewById(R.id.checkboxImportant)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnOK = view.findViewById(R.id.btnOk)
    }

}