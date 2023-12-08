package com.example.souq.ztrash

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BestProductsRVMargins:RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right=10
        outRect.bottom=10
        outRect.left=10
        outRect.top=10
    }
}