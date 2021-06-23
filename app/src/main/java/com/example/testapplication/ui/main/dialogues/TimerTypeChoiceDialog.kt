package com.example.testapplication.ui.main.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.testapplication.R
import utils.TimerTypes


class TimerTypeChoiceDialog: DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: SelectTimerTypeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface SelectTimerTypeDialogListener {
        fun onTimerTypeDialogPositiveClick(selected_timer: String)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = targetFragment as SelectTimerTypeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement SelectRepeatingDaysDialogListener"))
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var indexSelectedTimerType:Int = -1
            val builder = AlertDialog.Builder(it,R.style.MultipleDialogue)
            // Set the dialog title
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(R.array.timer_types,0
                ) { _, which ->
                    indexSelectedTimerType = which
                }
                // Set the action buttons
                .setPositiveButton(R.string.ok) { dialog, id ->
                    // User clicked OK, so save the selectedItems results somewhere
                    var selectedTimerType = ""

                    when(indexSelectedTimerType){
                        0 -> selectedTimerType = TimerTypes.basic.value
                        1 -> selectedTimerType = TimerTypes.incremental.value
                        2 -> selectedTimerType = TimerTypes.decremental.value
                        3 -> selectedTimerType = TimerTypes.pomodoro.value
                    }
                    listener.onTimerTypeDialogPositiveClick(selectedTimerType)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                    // User cancelled the dialog
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}