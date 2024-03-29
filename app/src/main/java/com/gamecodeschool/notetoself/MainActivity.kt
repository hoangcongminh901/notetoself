package com.gamecodeschool.notetoself

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSerializer: JSONSerializer? = null
    private var noteList: ArrayList<Note>? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null

    private var dividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "123")
        }

        mSerializer = JSONSerializer("NoteToSelf.json", applicationContext)

        try {
            noteList = mSerializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = NoteAdapter(this, this.noteList!!)

        val layoutManager = LinearLayoutManager(applicationContext)

        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        dividers = prefs.getBoolean("dividers", true)
        if (dividers) {
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        } else {
            if (recyclerView!!.itemDecorationCount > 0) recyclerView!!.removeItemDecorationAt(0)
        }

        recyclerView!!.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        dividers = prefs.getBoolean("dividers", true)
    }

    override fun onPause() {
        super.onPause()
        saveNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun createNewNote(newNote: Note) {
        noteList!!.add(newNote)
        adapter!!.notifyDataSetChanged()
    }

    fun showNote(shownNoteIndex: Int) {
        val dialog = DialogShowNote()
        dialog.sendSelectedNote(this.noteList!![shownNoteIndex])
        dialog.show(supportFragmentManager, "")
    }

    private fun saveNotes() {
        try {
            mSerializer!!.save(this.noteList!!)
        } catch (e: Exception) {
            Log.e("Error Saving Notes", ", e")
        }
    }
}
