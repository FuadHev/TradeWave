package com.fuadhev.tradewave.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class OfferUiModel(
    var id: Int = 1,
    var title: String = "",
    var discount: Double = 0.0,
    var thumbnailUrl: String = "",
    var expirationDate: String? = "",
) : Parcelable {
    fun discount() = "${discount.toInt()}% Off"
}