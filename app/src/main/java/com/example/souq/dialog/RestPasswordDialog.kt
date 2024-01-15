package com.example.souq.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.souq.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupBottomSheetDialog(
    onSendClick:(String)->Unit
) {

    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state=BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edResetEmail = view.findViewById<EditText>(R.id.edResetEmail)
    val btnCancel = view.findViewById<Button>(R.id.btCancelReset)
    val btnSend = view.findViewById<Button>(R.id.btSendReset)

    btnCancel.setOnClickListener {
        dialog.dismiss()

    }
    btnSend.setOnClickListener {
        val email=edResetEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()

    }

}