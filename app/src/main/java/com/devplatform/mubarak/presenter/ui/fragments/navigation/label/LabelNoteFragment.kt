package com.devplatform.mubarak.presenter.ui.fragments.navigation.label

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.notes.databinding.FragmentLabelNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LabelNoteFragment : Fragment() {

    private lateinit var binding: FragmentLabelNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLabelNoteBinding.inflate(
            layoutInflater,
            container,
            false
        )

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        requireActivity().window.setStatusBarColor(Color.TRANSPARENT);

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBarLabel.setNavigationOnClickListener {
            requireActivity().findViewById<DrawerLayout>(R.id.main_drawerLayout).apply {
                openDrawer(
                    GravityCompat.START
                )
            }
        }
    }


}