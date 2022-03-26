package ir.behnawwm.watchlist.core.utils.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import ir.behnawwm.watchlist.databinding.BottomSheetLayoutBinding


open class OptionBottomSheetDialog(
    context: Context,
    private val titleResList: List<String>,
    private val iconResList: List<Int>? = null,
    private val selectedPosition: Int = -1,
    private val dialogTitle: String? = null,
    private val redTintItemPosition: List<Int>? = null,
    private val onDismissListener: (() -> Unit)? = null,
    val onOptionClick: (selectedTitle: String) -> Unit,
) : BottomSheetDialog(context) {
    lateinit var binding : BottomSheetLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        if (dialogTitle != null) {
            binding.tvTitleDialog.text = dialogTitle
        } else {
            binding.tvTitleDialog.visibility = View.GONE
            binding.titleDivider.visibility = View.GONE
        }

        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.bottomSheetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.bottomSheetRecyclerView.adapter =
            OptionsListAdapter(titleResList, iconResList, selectedPosition,redTintItemPosition) {
                onOptionClick(titleResList[it])
                dismiss()
            }
    }

    override fun onStop() {
        super.onStop()
        onDismissListener?.invoke()
    }
}