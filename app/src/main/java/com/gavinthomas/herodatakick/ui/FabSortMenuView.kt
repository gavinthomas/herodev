package com.gavinthomas.herodatakick.ui

import android.animation.Animator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.gavinthomas.herodatakick.R
import com.gavinthomas.herodatakick.presenter.SortType
import kotlinx.android.synthetic.main.fab_sort_menu_view.view.*

class FabSortMenuView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var fabOpen = false
    private var sortTypePressedListener: OnSortTypePressed? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.fab_sort_menu_view, this, true)
        initFabLayout()
    }

    interface OnSortTypePressed {
        fun onSortTypePressed(type: SortType)
    }

    fun setSortTypePressed(listener: OnSortTypePressed) {
        sortTypePressedListener = listener
    }

    fun isFabOpen() = fabOpen

    private fun initFabLayout() {
        fab.setOnClickListener {
            if (!fabOpen) showFABMenu()
            else closeFABMenu()
        }
        fabLayout1.setOnClickListener {
            sortTypePressedListener?.let { listener ->
                fab.setImageResource(R.drawable.ic_sort_reverse_numeric)
                closeFABMenu()
                listener.onSortTypePressed(SortType.NUMERIC_REVERSE)
            }
        }
        fabLayout2.setOnClickListener {
            sortTypePressedListener?.let { listener ->
                fab.setImageResource(R.drawable.ic_sort_by_numeric_order)
                closeFABMenu()
                listener.onSortTypePressed(SortType.NUMERIC)
            }
        }
        fabLayout3.setOnClickListener {
            sortTypePressedListener?.let { listener ->
                fab.setImageResource(R.drawable.ic_sort_reverse_alphabetical_order)
                closeFABMenu()
                listener.onSortTypePressed(SortType.ALPHABETIC_REVERSE)
            }
        }
        fabLayout4.setOnClickListener {
            sortTypePressedListener?.let { listener ->
                fab.setImageResource(R.drawable.ic_sort_by_alphabet)
                closeFABMenu()
                listener.onSortTypePressed(SortType.ALPHABETIC)
            }
        }
    }


    private fun showFABMenu() {
        fabOpen = true
        fabLayout1.visibility = View.VISIBLE
        fabLayout2.visibility = View.VISIBLE
        fabLayout3.visibility = View.VISIBLE
        fabLayout4.visibility = View.VISIBLE

        fab.animate().rotationBy(360f)
        fabLayout1.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fabLayout2.animate().translationY(-resources.getDimension(R.dimen.standard_100))
        fabLayout3.animate().translationY(-resources.getDimension(R.dimen.standard_145))
        fabLayout4.animate().translationY(-resources.getDimension(R.dimen.standard_190))
        fab1card.animate().alpha(1.0f)
        fab2card.animate().alpha(1.0f)
        fab3card.animate().alpha(1.0f)
        fab4card.animate().alpha(1.0f)
    }

    fun closeFABMenu() {
        fabOpen = false
        fab.animate().rotationBy(-360f)
        fabLayout1.animate().translationY(0f)
        fabLayout2.animate().translationY(0f)
        fabLayout3.animate().translationY(0f)
        fabLayout4.animate().translationY(0f).setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                if (!fabOpen) {
                    fabLayout1.visibility = View.GONE
                    fabLayout2.visibility = View.GONE
                    fabLayout3.visibility = View.GONE
                    fabLayout4.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })

        fab1card.animate().alpha(0f)
        fab2card.animate().alpha(0f)
        fab3card.animate().alpha(0f)
        fab4card.animate().alpha(0f)

    }

}