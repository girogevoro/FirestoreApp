package com.girogevoro.firestoreapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.girogevoro.firestoreapp.GOOD_PRESSURE1
import com.girogevoro.firestoreapp.GOOD_PRESSURE2
import com.girogevoro.firestoreapp.MAX_PRESSURE1
import com.girogevoro.firestoreapp.MAX_PRESSURE2
import com.girogevoro.firestoreapp.MIN_PRESSURE1
import com.girogevoro.firestoreapp.MIN_PRESSURE2
import com.girogevoro.firestoreapp.databinding.ItemNoteBinding
import com.girogevoro.firestoreapp.domain.entity.Note
import com.girogevoro.firestoreapp.utils.toHourMinString
import kotlin.math.abs

class NoteViewHolder private constructor(
    private val binding: ItemNoteBinding

) : RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

    constructor(parent: ViewGroup) : this(
        ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    fun bind(
        data: Note,
        onEditNoteClicked: ((Note) -> Unit)?
    ) {
        if (layoutPosition != RecyclerView.NO_POSITION) {
            with(binding) {
                idTv.text = data.id
                pressure1Tv.text = data.pressure1.toString()
                pressure2Tv.text = data.pressure2.toString()
                pulseTv.text = data.pulse.toString()
                timeTv.text = data.dateTime.toHourMinString()

                editBtn.setOnClickListener {
                    onEditNoteClicked?.invoke(data)
                }


                if (data.pressure1 !in MIN_PRESSURE1..MAX_PRESSURE1 || data.pressure2 !in MIN_PRESSURE2..MAX_PRESSURE2) {
                    var delta =
                        abs(GOOD_PRESSURE1 - data.pressure1) + abs(GOOD_PRESSURE2 - data.pressure2)

                    val green = (255 * (100 - delta) / 100).coerceIn(0, 255)
                    val red = (255 - delta).coerceIn(0, 255)

                    val colors = intArrayOf(Color.rgb(red, green, 0), Color.WHITE)

                    linearLayout1.background = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
                    linearLayout2.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
                }
                else{
                    val colors = intArrayOf(Color.rgb(0, 200, 0), Color.WHITE)
                    linearLayout1.background = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
                    linearLayout2.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
                }
            }
        }
    }

    override fun onItemSelected() {

    }

    override fun onItemClear() {

    }
}