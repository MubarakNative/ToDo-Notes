package com.devplatform.mubarak.presenter.ui.fragments.createnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentCreateNoteBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeModel: MainViewModel by viewModels()

        binding.toolbarCreateNote.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        binding.fabSaveNote.setOnClickListener {
            val tvTitle = binding.etAddTitle.text.toString().trim()
            val tvDescription = binding.etAddDesc.text.toString()

            if (tvTitle.isEmpty() && tvDescription.isEmpty()) {
                findNavController().popBackStack()
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    resources.getString(R.string.empty_note), Snackbar.LENGTH_SHORT
                ).show()
            } else {

                val note = Note(0, tvTitle, tvDescription)
                homeModel.upsertNote(note)
                findNavController().popBackStack()
            }
        }
    }
}