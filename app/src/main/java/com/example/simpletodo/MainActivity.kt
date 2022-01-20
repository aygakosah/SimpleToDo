package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks =  mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object  : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        loadItems()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

         adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputField = findViewById<EditText>(R.id.addTaskField)
        //Create a reference to the button and set its onclick listener
        findViewById<Button>(R.id.button).setOnClickListener{
            // Get the inputtext field by id
            val userInputtedTask = inputField.text.toString()
            //Add the text to the list of tasks
            listOfTasks.add(userInputtedTask)
            //Notify adapter of new task
            adapter.notifyItemInserted(listOfTasks.size-1)
            // Reset the input text field.
            inputField.setText("")
            saveItems()
        }

    }

    //Get the file we need
    fun getDataFile() : File{
        return File(filesDir, "data.txt")
    }

    //Load items on the file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset() )
        }catch(ioException : IOException){
            ioException.printStackTrace()
        }
    }

    //Save items on the file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }


}