/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.muviz.common.util

import androidx.datastore.preferences.core.intPreferencesKey

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val STARTING_PAGE_INDEX = 0
    const val IMAGE_BASE_UR = "https://image.tmdb.org/t/p/w500/"
    const val DATABASE_NAME = "favorites_database"
    const val TABLE_NAME = "favorites_table"

    const val MUVIZ_PREFERENCES = "MEALTIME_PREFERENCES"
    val THEME_OPTIONS = intPreferencesKey(name = "theme_option")
    const val PAGING_SIZE = 20
}
