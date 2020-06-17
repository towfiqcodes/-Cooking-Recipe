package com.example.cookingrecipe.utility.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogBox {
    var context:Context?=null

    constructor(context: Context){
        this.context=context
    }

     fun getDialog(text: String) {
        val dialogBuilder = context?.let { AlertDialog.Builder(it) }

        // set message of alert dialog
        dialogBuilder!!.setMessage(text)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Cooking Recipe")
        // show alert dialog
        alert.show()
    }
}