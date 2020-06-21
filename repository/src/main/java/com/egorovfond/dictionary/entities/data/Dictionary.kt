package com.egorovfond.dictionary.entities.data

import android.os.Parcelable
import com.google.gson.annotations.Expose

data class Dictionary(
    @Expose val listWorld : MutableList<World>
)