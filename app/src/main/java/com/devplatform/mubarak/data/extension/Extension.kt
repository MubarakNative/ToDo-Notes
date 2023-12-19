package com.devplatform.mubarak.data.extension

import android.content.Context
import android.view.ViewGroup
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.presenter.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

fun Context.undoNote(view:ViewGroup,viewModel: MainViewModel,
                     note:Note){
    Snackbar.make(
        view,
        R.string.note_deleted,
        Snackbar.LENGTH_SHORT
    )
        .setAction(
            R.string.undo
        ) {
            viewModel.upsertNote(note)
        }.show()
}