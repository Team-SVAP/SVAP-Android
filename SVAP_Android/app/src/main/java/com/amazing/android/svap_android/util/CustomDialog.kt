package com.amazing.android.svap_android.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.amazing.android.svap_android.R

class CustomDialog(
    context: Context,
    titleText: String,
    content: String,
    btnText: String,
    private val listener: DialogClickListener
) : DialogFragment() {
    private val dialog = AlertDialog.Builder(context).create()
    private val inflater = LayoutInflater.from(context)
    private val view = inflater.inflate(R.layout.dialog_layout, null)

    init {
        dialog.setView(view)
        val title = view.findViewById<TextView>(R.id.tv_dialog_title)
        val msg = view.findViewById<TextView>(R.id.tv_dialog_content)
        val btnOk = view.findViewById<Button>(R.id.btn_dialog_ok)
        val btnCancel = view.findViewById<Button>(R.id.btn_dialog_cancel)
        title.text = titleText
        msg.text = content
        btnOk.text = btnText
        btnOk.setOnClickListener {
            listener.onYesBtnClick()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show() {
        dialog.show()
    }
}
