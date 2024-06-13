package com.capstone.herbalease.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.herbalease.R

class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private var clearButtonImage: Drawable
    private var searchButtonImage: Drawable

    private var isClearButton = false

    init {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
        searchButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_search) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNullOrEmpty()) {
                    resetButtons()
                    showSearchButton()
                } else {
                    resetButtons()
                    showClearButton()
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = ContextCompat.getDrawable(context, R.drawable.background_search_view)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showClearButton() {
        isClearButton = true
        setButtonDrawables(endOfTheText = clearButtonImage)
    }

    private fun showSearchButton() {
        isClearButton = false
        setButtonDrawables(endOfTheText = searchButtonImage)
    }

    private fun resetButtons() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val buttonStart: Float
            val buttonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                buttonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < buttonEnd -> isClearButtonClicked = true
                }
            } else {
                buttonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > buttonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButton) {
                if (isClearButtonClicked) {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            clearButtonImage =
                                ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                            showClearButton()
                            return true
                        }

                        MotionEvent.ACTION_UP -> {
                            clearButtonImage =
                                ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                            when {
                                text != null -> text?.clear()
                            }
                            resetButtons()
                            showSearchButton()
                            return true
                        }

                        else -> return false
                    }
                } else return false
            }
        }
        return false
    }
}