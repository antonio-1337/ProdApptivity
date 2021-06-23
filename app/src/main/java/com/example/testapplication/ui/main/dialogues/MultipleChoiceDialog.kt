package com.example.testapplication.ui.main.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.testapplication.R
import java.util.*
import kotlin.collections.ArrayList


class MultipleChoiceDialog: DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: SelectRepeatingDaysDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface SelectRepeatingDaysDialogListener {
        fun onDialogPositiveClick(selected_days: String)
        fun onDialogNegativeClick()
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = targetFragment as SelectRepeatingDaysDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement SelectRepeatingDaysDialogListener"))
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it,R.style.MultipleDialogue)
            // Set the dialog title
            builder.setTitle(getString(R.string.Repeating_days))
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.week_days, null) { dialog, which, isChecked ->
                    if (isChecked) {
                        // If the user checked the item, add it to the selected items
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        // Else, if the item is already in the array, remove it
                        selectedItems.remove(Integer.valueOf(which))
                    }
                }
                // Set the action buttons
                .setPositiveButton(R.string.ok) { dialog, id ->
                    // User clicked OK, so save the selectedItems results somewhere
                    var repeatingDaysString =""

                    for (item: Int in selectedItems){
                        when(item){
                            0 -> repeatingDaysString+= Calendar.MONDAY.toString() +";"
                            1 -> repeatingDaysString+= Calendar.TUESDAY.toString()+";"
                            2 -> repeatingDaysString+= Calendar.WEDNESDAY.toString()+";"
                            3 -> repeatingDaysString+= Calendar.THURSDAY.toString()+";"
                            4 -> repeatingDaysString+= Calendar.FRIDAY.toString()+";"
                            5 -> repeatingDaysString+= Calendar.SATURDAY.toString()+";"
                            6 -> repeatingDaysString+= Calendar.SUNDAY.toString()+";"
                        }
                    }

                    //remove last semicolon (;)
                    repeatingDaysString = repeatingDaysString.substring(0,repeatingDaysString.length - 1)

                    listener.onDialogPositiveClick(repeatingDaysString)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                    // User cancelled the dialog
                    listener.onDialogNegativeClick()
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}