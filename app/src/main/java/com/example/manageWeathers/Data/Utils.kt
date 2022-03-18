package com.example.manageWeathers.Data

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * class to define generic methods
 */
class Utils {
    companion object {
        /**
         * Display a personilezed Alert
         */
        fun displayAlert(
            context: Context,
            customview: View?,
            method: () -> Unit,
            title: String?,
            positiveBotton: String,
            negativButton: String?
        ) {
            var validButton: Button
            var cancelButton: Button
            val customAlertDialog = MaterialAlertDialogBuilder(context)

            if (customview != null) {
                customAlertDialog.setView(customview)
            }
            customAlertDialog.setMessage(title)
            customAlertDialog.setNegativeButton(negativButton, null)
            customAlertDialog.setPositiveButton(positiveBotton, null)
            customAlertDialog.setCancelable(false)
            alertDialog = customAlertDialog.create()
            alertDialog.setOnShowListener {
                validButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                validButton.setOnClickListener {
                    method()
                    alertDialog.dismiss()

                }
                cancelButton.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
            alertDialog.show()
        }

        lateinit var alertDialog: AlertDialog
    }
}