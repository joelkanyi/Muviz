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

import android.content.Context

/*
actual val versionName: String
        get() = try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "---"
        }

    actual val versionCode: Int
        get() = try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionCode
            version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0
        }
 */

fun Context.appVersionName(): String {
    return try {
        val pInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val version = pInfo.versionName
        version
    } catch (e: Exception) {
        e.printStackTrace()
        "---"
    }
}

fun Context.appVersionCode(): Int {
    return try {
        val pInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val version = pInfo.versionCode
        version
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun String.createImageUrl(): String {
    return "${Constants.IMAGE_BASE_UR}/$this"
}
