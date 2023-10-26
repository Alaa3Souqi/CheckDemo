package com.alaa.checkindemo.feature_check_in.domain.use_case

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCurrentDate @Inject constructor() {

    operator fun invoke(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }

}