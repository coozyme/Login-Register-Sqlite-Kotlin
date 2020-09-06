package apps.programmeranak.loreg.helpers

import android.app.Activity
import android.content.Context
import android.os.Message
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation (private val context: Context) {

    fun isInputEditTextFilled(textInputEditText: EditText, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            textInputEditText.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        }
        return true
        }

    /**
     * method to check InputEditText has valid email .
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    fun isInputEditTextEmail(textInputEditText: EditText, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputEditText.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        }
        return true
    }

    /**
     * method to check both InputEditText value matches.
     *
     * @param textInputEditText1
     * @param textInputEditText2
     * @param textInputLayout
     * @param message
     * @return
     */
    fun isInputEditTextMatches(textInputEditText1: EditText, textInputEditText2: EditText,  message: String): Boolean {
        val value1 = textInputEditText1.text.toString().trim()
        val value2 = textInputEditText2.text.toString().trim()
        if (!value1.contentEquals(value2)) {
            textInputEditText2.error = message
            hideKeyboardFrom(textInputEditText2)
            return false
        }
        return true
    }


    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private fun hideKeyboardFrom(view: View) {
        val imm =  context.getSystemService(   Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}
}



