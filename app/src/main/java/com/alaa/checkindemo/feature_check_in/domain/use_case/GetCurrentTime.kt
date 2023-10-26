package com.alaa.checkindemo.feature_check_in.domain.use_case

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCurrentTime @Inject constructor() {

    operator fun invoke(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date())
    }

}