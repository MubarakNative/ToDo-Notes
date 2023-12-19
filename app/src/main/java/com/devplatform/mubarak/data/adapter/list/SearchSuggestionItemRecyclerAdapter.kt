package com.devplatform.mubarak.data.adapter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.notes.databinding.HomenoteItemRowBinding
import com.devplatform.mubarak.presenter.ui.fragments.navigation.home.HomeNoteFragmentDirections

class SearchSuggestionItemRecyclerAdapter :
    RecyclerView.Adapter<SearchSuggestionItemRecyclerAdapter.NoteViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem

    }

    val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class NoteViewHolder(binding: HomenoteItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvDesc: TextView = binding.tvDesc
        val parentLayout = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            HomenoteItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val list = asyncDiffer.currentList[position]
        holder.tvTitle.text = list.title
        holder.tvDesc.text = list.description
        holder.parentLayout.setOnClickListener {

            val action = HomeNoteFragmentDirections.actionHomeNoteFragmentToUpdateFragment(
                Note(list.id, list.title, list.description)
            )
            findNavController(it).navigate(action)
        }
    }


    /*  private var onClick:(() ->Unit)?= null


       fun setOnClickListener(note:(() ->Unit)){
              onClick = note

      }*/

}