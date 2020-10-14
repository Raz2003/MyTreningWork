package com.example.mytreningwork.ViewModel

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher (val function:(CharSequence?)->Unit) : TextWatcher
{
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    {
        function(s)
    }
}