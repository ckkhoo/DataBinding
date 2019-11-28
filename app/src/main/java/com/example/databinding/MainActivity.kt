package com.example.databinding

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myContact = Contact("Abu", "0123456789")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Display UI
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        //Assign attributes of local variable to UI variable
        binding.contact = myContact

        //Create an event handler for buttonSend
        buttonSend.setOnClickListener {
            sendMessage()
        };

        buttonUpdate.setOnClickListener {
            binding.apply {
                contact?.name = "My new name"
                contact?.phone = "111111"
                invalidateAll() //refresh the UI
            }
        }
    }

    private fun sendMessage() {
        //Craete an Explicit Intent for the SecondActivity
        val intent = Intent(this, SecondActivity::class.java)

        //Prepare extra
        val message = editTextMessage.text.toString()

        //Implicit - setData
        //Explicit - putExtra / getExtra, eg. getStringExtra
        intent.putExtra(EXTRA_MESSAGE, message)

        //Start an activity with no return value
        //startActivity(intent)

        //Start an activity with return value(s)/result(s)
        startActivityForResult(intent, REQUEST_REPLY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_REPLY) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data?.getStringExtra(EXTRA_REPLY)
                textViewReply.text = String.format("%s : %s", getString(R.string.reply), reply)
            } else {
                textViewReply.text = String.format("%s : %s", getString(R.string.reply), "No reply")
            }
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "com.example.databinding.MESSAGE" //Unique constant value = package name + VALUE
        const val EXTRA_REPLY = "com.example.databinding.REPLY"
        const val REQUEST_REPLY = 1
    }
}
