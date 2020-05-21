package mibnu.team.petugas.utils

import android.content.Context

class Utilities {
    companion object {
        fun getToken(c : Context) : String? {
            val s = c.getSharedPreferences("USER", Context.MODE_PRIVATE)
            return s?.getString("TOKEN", null)
        }

        fun setToken(context: Context, token : String){
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun isValidUsername(username : String?) = !username.isNullOrEmpty()
        fun isValidPassword(password : String) = password.length >= 1
    }
}