package com.devplatform.mubarak.presenter.ui.fragments.navigation.home

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
import com.devplatform.mubarak.data.adapter.list.HomeNoteItemRecyclerAdapter
import com.devplatform.mubarak.data.adapter.list.SearchSuggestionItemRecyclerAdapter
import com.devplatform.mubarak.data.extension.undoNote
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentHomeNoteBinding
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeNoteFragment : Fragment() {

    private lateinit var binding: FragmentHomeNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
     /*   requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        requireActivity().window.setStatusBarColor(Color.TRANSPARENT);

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window,false)*/
        binding = FragmentHomeNoteBinding.inflate(
            inflater, container, false
        )



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteItemRecyclerAdapter by lazy {
            HomeNoteItemRecyclerAdapter()
        }
        val searchSuggestionItemRecyclerAdapter by lazy {
            SearchSuggestionItemRecyclerAdapter()
        }

        val homeModel: MainViewModel by viewModels()

        setUpHomeNoteRecyclerView(binding.homeNoteList)
        // setUpSearchSuggestionRecyclerView(binding.searchSuggestionNoteList)


        binding.toolBarHome.setNavigationOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.main_drawerLayout)
            drawer.openDrawer(GravityCompat.START)
        }

        var isDefaultIcon = true
        binding.toolBarHome.setOnMenuItemClickListener {
            menuItem ->

            when(menuItem.itemId){
                R.id.opt_viewMode -> {

                    if (isDefaultIcon){
                        menuItem.setIcon(R.drawable.list_view_icon24px)

                        binding.homeNoteList.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

                        isDefaultIcon = false
                    }else{

                        menuItem.setIcon(R.drawable.grid_view_icon24px)
                        binding.homeNoteList.layoutManager = LinearLayoutManager(requireContext())
                        isDefaultIcon = true

                    }
//                    menuItem.setIcon(R.drawable.menu_hamburger_icon_24px)
//                    binding.homeNoteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                }

            }
            true




        }

        /* binding.searchBar.setNavigationOnClickListener {
             val drawer = requireActivity().findViewById<DrawerLayout>(R.id.main_drawerLayout)
             drawer.openDrawer(GravityCompat.START)
         }*/

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val position = viewHolder.adapterPosition

                    homeModel.deleteNote(noteItemRecyclerAdapter.asyncDiffer.currentList[position])

                    requireContext().undoNote(
                        binding.root,
                        homeModel,
                        noteItemRecyclerAdapter.asyncDiffer.currentList[position]
                    )

                }


            })

        itemTouchHelper.attachToRecyclerView(binding.homeNoteList)

   /*     val searchView =
            binding.toolBarHome.menu.findItem(R.id.opt_searchNote).actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                Toast.makeText(requireContext(), newText, Toast.LENGTH_SHORT).show()

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        homeModel.searchNote(newText.toString()).collect { note ->

                            searchSuggestionItemRecyclerAdapter.asyncDiffer.submitList(note)

                            binding.homeNoteList.adapter =
                                searchSuggestionItemRecyclerAdapter
                            /*if (note.isEmpty()) {
                                // binding.tvNoNotes.visibility = View.VISIBLE
                                // noteItemRecyclerAdapter.asyncDiffer.submitList(note)

                                // binding.searchSuggestionNoteList.adapter = noteItemRecyclerAdapter
                            } else {
                                //   binding.tvNoNotes.visibility = View.GONE

                                searchSuggestionItemRecyclerAdapter.asyncDiffer.submitList(note)

                                binding.homeNoteList.adapter =
                                    searchSuggestionItemRecyclerAdapter


                            }*/

                        }


                    }
                }
                return true
            }

        })*/

        /* val searchView = binding.searchViewNotes.toolbar.menu.findItem(R.id.opt_searchNote).actionView as SearchView
         searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String): Boolean {

                 return true
             }

             override fun onQueryTextChange(query: String): Boolean {
                 return false
             }
         })*/


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getAllInsertedNote(
                    homeModel, noteItemRecyclerAdapter,
                    binding.homeNoteList
                )
            }
        }
        /*    binding.searchViewNotes.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?, start: Int, count: Int, after: Int
                ) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                    if (p0.toString().length > 1) {
                        lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                homeModel.searchNote(p0.toString()).collect { note ->

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
                    } else {

                    }


                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })*/

        // Toolbar
        /*  val toolbar = binding.toolBarHome
          toolbar.apply {
              inflateMenu(R.menu.opt_menu_search)
              setNavigationIcon(R.drawable.back_arrow24px)
              navigationContentDescription = ""
              setNavigationOnClickListener {
                  findNavController().popBackStack()
              }
          }*/


        // Search view
        /* val searchView = toolbar.menu.findItem(R.id.opt_searchNote).actionView as SearchView
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

                                 searchSuggestionItemRecyclerAdapter .asyncDiffer.submitList(note)

                                 binding.searchSuggestionNoteList.adapter =
                                     searchSuggestionItemRecyclerAdapter


                             }

                         }

                     }
                 }
                 return true
             }
         })*/


        navigateToCreateNoteFragment(binding.fabCreateNote)

    }


    private fun setUpHomeNoteRecyclerView(recyclerView: RecyclerView) {
        //   val lm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val lm = LinearLayoutManager(requireContext())
        recyclerView.layoutManager =
                //StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                // LinearLayoutManager(requireContext())
            lm
        recyclerView.setHasFixedSize(true)

    }

    private fun setUpSearchSuggestionRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private suspend fun getAllInsertedNote(
        viewModel: MainViewModel, adapter: HomeNoteItemRecyclerAdapter,
        homeMainRecyclerView: RecyclerView
    ) {

        viewModel.getNotes().collect { notes ->
            if (notes.isEmpty()) {
                binding.constraintLayout.visibility = View.VISIBLE



            } else {
                adapter.asyncDiffer.submitList(notes)
                homeMainRecyclerView.adapter = adapter
                binding.constraintLayout.visibility = View.GONE
            }

        }
    }

    private fun navigateToCreateNoteFragment(fab: FloatingActionButton) {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeNoteFragment_to_createNoteFragment)
        }
    }


}


