package ir.behnawwm.watchlist.core.utils.ui


import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.binding.BindingViewHolder
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.databinding.BottomSheetListItemBinding

class  OptionsListAdapter(
    private val titleResList: List<String>,
    private val iconResList: List<Int>?,
    private val selectedPosition: Int,
    private val redTintItemPosition: List<Int>? = null,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<BindingViewHolder<BottomSheetListItemBinding>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<BottomSheetListItemBinding> {
        return BindingViewHolder(
            BottomSheetListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = titleResList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BindingViewHolder<BottomSheetListItemBinding>, position: Int) {
        val binding = holder.binding
        if (iconResList != null && iconResList.size > position) {
            binding.iconView.setBackgroundResource(iconResList[position])
            binding.iconView.visibility = View.VISIBLE
        } else {
            binding.iconView.visibility = View.GONE
        }

        binding.titleView.text = titleResList[position]

        binding.root.setOnClickListener {
            onItemClick(position)
        }

        if (position == selectedPosition) {
            binding.selectedIcon.visibility = View.VISIBLE
        } else {
            binding.selectedIcon.visibility = View.GONE
        }

        if (redTintItemPosition != null)
            if (position in redTintItemPosition) {
                binding.iconView.backgroundTintList =
                    ColorStateList.valueOf(holder.itemView.context.resources.getColor(R.color.red))
                binding.titleView.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
            }
    }

}