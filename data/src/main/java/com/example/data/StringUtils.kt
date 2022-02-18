package com.example.data

import android.app.Application
import com.example.remote.R
import javax.inject.Inject

class StringUtils @Inject constructor(val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
}