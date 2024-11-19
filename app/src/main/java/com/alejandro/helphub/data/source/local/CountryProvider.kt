package com.alejandro.helphub.data.source.local

import com.alejandro.helphub.R
import com.alejandro.helphub.domain.models.CountriesModel

object CountryProvider {
    val countries= listOf(
        CountriesModel("España","+34", R.drawable.flag_spain),
        CountriesModel("Reino Unido", "+44",R.drawable.flag_uk),
        CountriesModel("Francia","+33",R.drawable.flag_france),
        CountriesModel("Alemania","+49",R.drawable.flag_germany)
    )
}