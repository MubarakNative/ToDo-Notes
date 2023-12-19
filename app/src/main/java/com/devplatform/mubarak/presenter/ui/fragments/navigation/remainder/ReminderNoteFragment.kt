package com.devplatform.mubarak.presenter.ui.fragments.navigation.remainder

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devplatform.mubarak.data.adapter.list.ReminderNoteItemRecyclerAdapter
import com.devplatform.mubarak.data.extension.undoNote
import com.devplatform.mubarak.data.reminder.AlarmSchedulerImpl
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentReminderNoteBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ReminderNoteFragment : Fragment() {

    private lateinit var binding: FragmentReminderNoteBinding

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReminderNoteBinding.inflate(
            layoutInflater,
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


        val adapter by lazy {
            ReminderNoteItemRecyclerAdapter()
        }

        var isDefaultIcon = true
        binding.toolBarRemainder.setOnMenuItemClickListener { menuItem ->

            when(menuItem.itemId){
                R.id.opt_viewMode -> {
                    if (isDefaultIcon){

                            menuItem.setIcon(R.drawable.list_view_icon24px)

                            binding.listReminderNote.layoutManager =
                                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

                            isDefaultIcon = false


                    }else{

                        if (adapter.asyncDiffer.currentList.isNotEmpty()){
                            menuItem.setIcon(R.drawable.grid_view_icon24px)
                            binding.listReminderNote.layoutManager = LinearLayoutManager(requireContext())
                            isDefaultIcon = true
                        }

                    }
                }
            }
            true
        }

        binding.toolBarRemainder.setNavigationOnClickListener {
            requireActivity().findViewById<DrawerLayout>(R.id.main_drawerLayout).apply {
                openDrawer(
                    GravityCompat.START
                )
            }
        }

        val mainViewModel: MainViewModel by viewModels()

        setUpReminderNoteRecyclerView(binding.listReminderNote)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mainViewModel.getAllReminderNote().collect {

                    if (it.isEmpty()){
                        binding.clEmptyNoteRemainder.visibility = View.VISIBLE
                    }else {
                        adapter.asyncDiffer.submitList(it)
                        binding.listReminderNote.adapter = adapter
                        binding.clEmptyNoteRemainder.visibility = View.GONE

                    }
                }
            }
        }


        binding.btnCreateReminder.setOnClickListener {
            findNavController().navigate(R.id.action_remainderNoteFragment_to_createReminderNoteFragment)
        }

        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                mainViewModel.deleteNote(viewHolder.adapterPosition)
                val note = adapter.asyncDiffer.currentList[viewHolder.adapterPosition]

                // Delete the note from the ViewModel or repository
                mainViewModel.deleteReminderNote(note)

                Snackbar.make(
                    binding.root,
                    R.string.reminder_note_deleted,
                    Snackbar.LENGTH_SHORT
                )
                    .setAction(
                        R.string.undo
                    ) {
                        mainViewModel.insertReminderNote(note)
                    }.show()

            }
        })

        touchHelper.attachToRecyclerView(binding.listReminderNote)

    }

    private fun setUpReminderNoteRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }
}