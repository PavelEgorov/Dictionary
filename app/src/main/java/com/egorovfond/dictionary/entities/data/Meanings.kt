package com.egorovfond.dictionary.entities.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meanings(
    @Expose val translation: Translation?,
    @Expose val imageUrl: String?
) : Parcelable