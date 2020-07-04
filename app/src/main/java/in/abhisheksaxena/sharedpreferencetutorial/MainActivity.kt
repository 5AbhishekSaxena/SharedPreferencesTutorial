package `in`.abhisheksaxena.sharedpreferencetutorial

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        preferences.registerOnSharedPreferenceChangeListener(this)

        save_button.setOnClickListener {
            val name = name_edit_text.text.toString()
            val age = age_edit_text.text.toString().toInt()
            val number = number_edit_text.text.toString().toLong()
            val isNightThemeEnabled = theme_switch.isChecked

            if (remember_me_checkbox.isChecked) {
                editor.putString("key_name", name)
                editor.putInt("key_age", age)
                editor.putLong("key_number", number)
                editor.putBoolean("key_isNightThemeEnabled", isNightThemeEnabled)
            } else {
                editor.clear()
            }
                    editor.apply()
        }

        val name = preferences.getString("key_name", "")
        val age = preferences.getInt("key_age", -1)
        val number = preferences.getLong("key_number", -1)
        val isNightThemeEnabled = preferences.getBoolean("key_isNightThemeEnabled",
            false)

        Log.d(
            TAG,
            "onCreate: name: $name, age: $age, number: $number, " +
                    "isNightThemeEnabled: $isNightThemeEnabled"
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.let {
            Log.d(TAG, "onSharedPreferenceChanged called ${it.all}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}