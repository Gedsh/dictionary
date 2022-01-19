package pan.alexander.core_utils.validators

import android.text.Editable
import android.text.TextWatcher
import pan.alexander.core_utils.Constants.EMAIL_PATTERN

class EmailValidator : TextWatcher {

    var isValid = false

    override fun afterTextChanged(editableText: Editable) {
        isValid = isValidEmail(editableText)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

    companion object {

        fun isValidEmail(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }
    }
}
