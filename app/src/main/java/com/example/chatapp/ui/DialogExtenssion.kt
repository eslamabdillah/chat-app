package com.example.chatapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

fun Fragment.showMessage(
    message: String,
    posActionName: String? = null,
    postAction: DialogInterface.OnClickListener? = null,
    negActionName: String? = null,
    negAction: DialogInterface.OnClickListener? = null,
    isCancelable: Boolean = true

): AlertDialog {

    //object from alertDialog (context = this in activity )
    val dialogBuilder = AlertDialog.Builder(context)

    //option in AlertDialog
    dialogBuilder.setMessage(message)
    if (posActionName !== null) {
        dialogBuilder.setPositiveButton(posActionName, postAction)

    }
    if (negActionName !== null) {
        dialogBuilder.setNeutralButton(negActionName, negAction)

    }
    dialogBuilder.setCancelable(isCancelable)
    return dialogBuilder.show()
}

fun Activity.showMessage(
    message: String,
    posActionName: String? = null,
    postAction: OnDialogActionClick? = null,
    negActionName: String? = null,
    negAction: OnDialogActionClick? = null,
    isCancelable: Boolean = true
): AlertDialog {
    //object from alertDialog
    val dialogBuilder = AlertDialog.Builder(this)
    //option in AlertDialog
    dialogBuilder.setMessage(message)
    if (posActionName !== null) {
        dialogBuilder.setNeutralButton(posActionName, { dialog, id ->
            dialog.dismiss()
            postAction?.onActionClick()
        })


    }
    if (negActionName !== null) {
        dialogBuilder.setNeutralButton(negActionName, { dialog, id ->
            dialog.dismiss()
            negAction?.onActionClick()
        })

    }
    dialogBuilder.setCancelable(isCancelable)
    return dialogBuilder.show()
}

