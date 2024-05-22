package com.example.stopwatch

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import java.lang.Runnable as Runnable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startButton = view.findViewById<ImageButton>(R.id.play_button)
        val pauseButton = view.findViewById<ImageButton>(R.id.pause_button)
        val minuteInput = view.findViewById<NumberPicker>(R.id.minuteInput)
        minuteInput.minValue = 0
        minuteInput.maxValue = 60

        val secondInput = view.findViewById<NumberPicker>(R.id.secondInput)
        secondInput.minValue = 1
        secondInput.maxValue = 59

        val counter = view.findViewById<TextView>(R.id.timer)

        var remainingMili = 0
        var handler = Handler(Looper.getMainLooper())

        var runnable = object : Runnable {
            override fun run() {
                if (remainingMili > 0)
                {
                    val rMin = (remainingMili / 60000).toInt()
                    val rSec = ((remainingMili - (rMin * 60000)) / 1000).toInt()
                    remainingMili -= 1000

                    counter.text = String.format("%02d", rMin) + ":" + String.format("%02d", rSec)
                    handler.postDelayed(this, 1000)
                } else
                {
                    counter.text = "00:00"
                }
            }
        }

        startButton.setOnClickListener {
            val minutes = String.format("%02d", minuteInput.value)
            val seconds = String.format("%02d", secondInput.value)

            remainingMili = minuteInput.value * 60 * 1000 + secondInput.value * 1000
            counter.text = "$minutes:$seconds"

            handler.post(runnable)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}