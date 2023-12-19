package com.devplatform.mubarak.presenter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentSearchNoteBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.devplatform.mubarak.data.adapter.list.HomeNoteItemRecyclerAdapter
import com.devplatform.mubarak.data.adapter.list.SearchSuggestionItemRecyclerAdapter
import kotlinx.coroutines.launch


class SearchNoteFragment : Fragment() {

    private lateinit var binding: FragmentSearchNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchNoteBinding.inflate(
            inflater,
            container,
            false
        )





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeModel: MainViewModel by viewModels()

        val noteItemRecyclerAdapter by lazy {
            HomeNoteItemRecyclerAdapter()
        }
        val searchSuggestionItemRecyclerAdapter by lazy {
            SearchSuggestionItemRecyclerAdapter()
        }


        setUpSearchSuggestionRecyclerView(binding.searchSuggestionNoteList)

        // Toolbar
        val toolbar = binding.toolBarSearch
        toolbar.apply {
            inflateMenu(R.menu.opt_menu_search)
            setNavigationIcon(R.drawable.back_arrow24px)
            navigationContentDescription = ""
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }


        // Search view
      /*  val searchView = toolbar.menu.findItem(R.id.opt_searchNote).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        homeModel.searchNote(query.toString()).collect { note ->

                            if (note.isEmpty()) {
                                // binding.tvNoNotes.visibility = View.VISIBLE
                                // noteItemRecyclerAdapter.asyncDiffer.submitList(note)

                                // binding.searchSuggestionNoteList.adapter = noteItemRecyclerAdapter
                            } else {
                                //   binding.tvNoNotes.visibility = View.GONE

                                searchSuggestionItemRecyclerAdapter.asyncDiffer.submitList(note)

                                binding.searchSuggestionNoteList.adapter =
                                    searchSuggestionItemRecyclerAdapter


                            }

                        }

                    }
                }
                return true
            }
        })*/

    }

    private fun setUpSearchSuggestionRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }
}