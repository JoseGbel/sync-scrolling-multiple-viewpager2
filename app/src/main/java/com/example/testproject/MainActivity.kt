package com.example.testproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager1 = findViewById<ViewPager2>(R.id.view_pager1)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        val layout = findViewById<ConstraintLayout>(R.id.layout)

        val stringsForVP1 = listOf(
            "At vero eos et accusamus et iusto odio dignissimos",
            "ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti"
        )
        val stringsForVP2 = listOf(
            "ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti",
            "At vero eos et accusamus et iusto odio dignissimos"
        )

        viewPager1.adapter = ViewPagerAdapter(this, stringsForVP1)
        viewPager2.adapter = ViewPagerAdapter(this, stringsForVP2)

        layout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity.application as Context) {
            override fun onSwipe(offset: Float?) {
                offset?.let {
                    val offset = convertPixelsToDp(it, applicationContext)
                    viewPager1.beginFakeDrag()
                    viewPager1.fakeDragBy(offset)
                    viewPager1.endFakeDrag()
                    viewPager2.beginFakeDrag()
                    viewPager2.fakeDragBy(offset)
                    viewPager2.endFakeDrag()
                }
                super.onSwipe(offset)
            }
        })
    }
}

class ViewPagerAdapter(
    private val context: Context,
    private val items: List<String>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(string: String) {
            val textView = itemView.findViewById<TextView>(R.id.string_fragment_value_text_view)
            textView.text = string
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.view_pager_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}