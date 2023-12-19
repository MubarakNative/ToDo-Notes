package com.devplatform.mubarak.presenter.ui.fragments.remindernote

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.devplatform.mubarak.data.reminder.AlarmItem
import com.devplatform.mubarak.data.reminder.AlarmSchedulerImpl
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentCreateReminderNoteBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CreateReminderNoteFragment : Fragment() {


    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentCreateReminderNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReminderNoteBinding.inflate(
            inflater,
            container,
            false
        )

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        requireActivity().window.statusBarColor = Color.TRANSPARENT;

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarReminderNote.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabSaveReminderNote.setOnClickListener {
            openTimePicker()
        }

    }

    private fun openTimePicker() {


        val sd = Calendar.getInstance()
        val hour = sd.get(Calendar.HOUR_OF_DAY)
        val min = sd.get(Calendar.MINUTE)
        val picker = MaterialTimePicker.Builder()
            .setTitleText("Pick a time")
            .setTimeFormat(is24HourFormat())
            .setHour(hour)
            .setMinute(min)
            .build()

        picker.show(childFragmentManager, "")

        picker.addOnPositiveButtonClickListener {

            binding.cpAlarmLabel.visibility = View.VISIBLE
            val inMillis = getAlarmTimeInMillis(
                picker.hour,
                picker.minute
            )
            val time = convertMillisToTimeString(inMillis)
            val reminderTitle = binding.etAddReminderTitle.text.toString().ifEmpty { resources.getString(R.string.title)}
            val reminderDescription = binding.etAddReminderDesc.text.toString().ifEmpty { time }
            mainViewModel.insertReminderNote(


                ReminderNoteItem(
                    reminderTitle,
                    reminderDescription,
                    time
                )
            )


            // user picked some time
            createAlarm(
                binding.etAddReminderTitle.text.toString().ifEmpty { "Alarm" },
                binding.etAddReminderDesc.text.toString().ifEmpty { "Description is not set" },
                inMillis
            )

            binding.etAddReminderTitle.text.clear()
            binding.etAddReminderDesc.text.clear()
        }
        picker.addOnNegativeButtonClickListener {
            // user cancelled the time picking
        }
        picker.addOnDismissListener {
            // dialog is closed
        }


    }

    private fun is24HourFormat(): Int {
        val format = DateFormat.is24HourFormat(requireContext())
        return if (format) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
    }

    private fun createAlarm(
        alarmTitle: String,
        alarmDescription: String,
        timeInMillis: Long
    ) {

        val alarmScheduler = AlarmSchedulerImpl(
            requireContext()
        )
        var alarmItem: AlarmItem? = null

        alarmItem = AlarmItem(
            timeInMillis,
            alarmTitle,
            alarmDescription
        )

        alarmItem.let { alarm ->
            alarmScheduler.schedule(alarm)
        }
    }

    private fun getAlarmTimeInMillis(hour: Int, minute: Int): Long {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        return calendar.timeInMillis
    }

    private fun convertMillisToTimeString(millis: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date = Date(millis)

        return sdf.format(date)
    }

}