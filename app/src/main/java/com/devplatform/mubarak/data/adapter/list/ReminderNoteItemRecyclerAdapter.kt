package com.devplatform.mubarak.data.adapter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devplatform.mubarak.notes.databinding.HomenoteItemRowBinding
import com.devplatform.mubarak.presenter.ui.fragments.navigation.home.HomeNoteFragmentDirections
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.devplatform.mubarak.notes.databinding.ReminderNoteItemRowBinding
import com.google.android.material.chip.Chip

class ReminderNoteItemRecyclerAdapter :
    RecyclerView.Adapter<ReminderNoteItemRecyclerAdapter.NoteViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<ReminderNoteItem>() {
        override fun areItemsTheSame(oldItem: ReminderNoteItem, newItem: ReminderNoteItem) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ReminderNoteItem, newItem: ReminderNoteItem) =
            oldItem == newItem

    }

    val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class NoteViewHolder(binding: ReminderNoteItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvReminderTitle: TextView = binding.tvReminderTitle
        val tvReminderDesc: TextView = binding.tvReminderDesc
        val cpAlarmTime:Chip = binding.cpReminderTime
        val parentReminderLayout = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            ReminderNoteItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val list = asyncDiffer.currentList[position]
        holder.tvReminderTitle.text = list.reminderTitle
        holder.tvReminderDesc.text = list.reminderDescription
        holder.cpAlarmTime.text = list.cpTime
        holder.parentReminderLayout.setOnClickListener {

            /*val action = HomeNoteFragmentDirections.actionHomeNoteFragmentToUpdateFragment(
                ReminderNoteItem(list.id, list.title, list.description)
            )
            findNavController(it).navigate(action)*/
        }
    }
}