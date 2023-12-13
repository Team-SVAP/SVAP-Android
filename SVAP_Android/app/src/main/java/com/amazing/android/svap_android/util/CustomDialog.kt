package com.amazing.android.svap_android.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.amazing.android.svap_android.R

class ConfirmDialog(context: Context,titleText:String,content:String) : DialogFragment(){
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
        btnOk.setOnClickListener {
            // 클릭 시 수행할 동작 작성
            //dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    fun show() {
        dialog.show()
    }
}