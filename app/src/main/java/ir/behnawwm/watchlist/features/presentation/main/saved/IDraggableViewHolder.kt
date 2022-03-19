package ir.behnawwm.watchlist.features.presentation.main.saved

interface IDraggableViewHolder {
    /**
     * Called when an item enters drag state
     */
    fun onDragged()

    /**
     * Called when an item has been dropped
     */
    fun onDropped()
}