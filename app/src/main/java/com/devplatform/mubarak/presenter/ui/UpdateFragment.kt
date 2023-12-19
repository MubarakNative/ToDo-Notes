package com.devplatform.mubarak.presenter.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentUpdateBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private lateinit var _binding: FragmentUpdateBinding
    private val binding get() = _binding

     private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentUpdateBinding.inflate(inflater,container,false)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        requireActivity().window.setStatusBarColor(Color.TRANSPARENT);

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window,false)

        val mainViewModel : MainViewModel by viewModels()


        binding.etUpdateTitle.setText(args.currentItem.title)
        binding.etUpdateDesc.setText(args.currentItem.description)

        val currentId = args.currentItem.id

        binding.toolBarUpdate.setOnMenuItemClickListener { menuItem ->

            when(menuItem.itemId){
                R.id.opt_sendNote -> {
                    Intent().also {
                        it.action = Intent.ACTION_SEND
                        it.type = "text/plain"
                        it.putExtra(Intent.EXTRA_TEXT,args.currentItem.description)
                        startActivity(Intent.createChooser(it,"Send text to"))
                    }
                }

                R.id.opt_deleteNote -> {
                        Toast.makeText(requireContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show()
                         mainViewModel.deleteNote(Note(currentId,"",""))
                        findNavController().popBackStack()

                }
            }

            true
        }

        binding.toolBarUpdate.setNavigationOnClickListener {

            findNavController().popBackStack()
        }

        /*binding.bottomAppBar.setOnMenuItemClickListener{ it ->
            when(it.itemId){
                R.id.opt_deleteNote -> {
                    Toast.makeText(requireContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show()
                    mainViewModel.deleteNote(Note(currentId,"",""))
                    findNavController().popBackStack()
                }

                R.id.opt_sendNote -> {
                    Toast.makeText(requireContext(), "Note Sent!", Toast.LENGTH_SHORT).show()
                    Intent().also {
                        it.action = Intent.ACTION_SEND
                        it.type = "text/plain"
                        it.putExtra(Intent.EXTRA_TEXT,args.currentItem.description)
                            startActivity(Intent.createChooser(it,"Send text to"))
                    }
                }
            }
            true
        }*/

        binding.fabUpdateNote.setOnClickListener {
            val title = binding.etUpdateTitle.text.toString()
            val desc = binding.etUpdateDesc.text.toString()

            if (title.isEmpty() && desc.isEmpty()) {
                findNavController().popBackStack()
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    resources.getString(R.string.empty_note), Snackbar.LENGTH_SHORT
                ).show()
            } else {
                mainViewModel.upsertNote(Note(currentId,title,desc))
                findNavController().popBackStack()
            }

        }
      /*  val toolbar = binding.toolBarUpdate

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        toolbar.setOnMenuItemClickListener {
            menuItem ->
            when(menuItem.itemId){
                R.id.opt_delete -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete note?")
                    .setMessage("Are you sure to delete this note?")
                    .setIcon(R.drawable.trash_icon24px)
                    .setPositiveButton(
                        "Yes"
                    ){ dialog,which ->
                        mainViewModel.deleteNote(Note(currentId,"",""))
                        dialog.dismiss()
                        findNavController().popBackStack()
                    }
                    .setNegativeButton(
                        "No"
                    ){ dialog,which ->
                        dialog.dismiss()
                    }.show()
                }
                R.id.opt_ -> {
                    Toast.makeText(requireContext(), "In Development", Toast.LENGTH_SHORT).show()
                }

            }

            true
        }

        binding.fabUpdateNote.setOnClickListener {
            val title = binding.etUpdateTitle.text.toString()
            val desc = binding.etUpdateDesc.text.toString()
            mainViewModel.upsertNote(Note(currentId,title,desc))
            findNavController().popBackStack()
        }*/

        binding.fabUpdateNote.setOnClickListener {
            val title = binding.etUpdateTitle.text.toString()
            val desc = binding.etUpdateDesc.text.toString()
            mainViewModel.upsertNote(Note(currentId,title,desc))
            findNavController().popBackStack()
        }
        return binding.root
    }

}
