package com.egorovfond.dictionary.entities.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class World (
    @Expose val text: String,
    @Expose val imageUrl: String,
    @Expose val submission: String
) : Parcelable