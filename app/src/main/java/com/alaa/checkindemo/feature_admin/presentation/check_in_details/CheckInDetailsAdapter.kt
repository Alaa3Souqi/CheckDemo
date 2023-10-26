package com.alaa.checkindemo.feature_admin.presentation.check_in_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.ItemCheckInDetailsBinding
import com.alaa.checkindemo.feature_auth.domain.model.CheckInLog
import com.alaa.checkindemo.feature_auth.domain.model.CheckInState

class CheckInDetailsAdapter(
    private val checkInDetails: List<CheckInLog>
) : RecyclerView.Adapter<CheckInDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCheckInDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = checkInDetails[position]
        holder.apply {

            if (item.type == CheckInState.CheckedIn) {
                type.text = "Check In"
                type.setTextColor(itemView.context.getColor(R.color.check_in_color))

            } else {
                type.text = "Check Out"
                type.setTextColor(itemView.context.getColor(R.color.check_out_color))
            }

            date.text = "Date: " + checkInDetails[position].date
            time.text = "Time: " + checkInDetails[position].time
        }
    }

    override fun getItemCount(): Int = checkInDetails.size

    class ViewHolder(binding: ItemCheckInDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        val type = binding.tvType
        val time = binding.tvTime
        val date = binding.tvDate
    }
}