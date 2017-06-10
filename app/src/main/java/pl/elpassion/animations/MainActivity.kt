package pl.elpassion.animations

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtons()
    }

    fun setButtons() {
        oneButton.setOnClickListener { changeColor(it) }
        twoButton.setOnClickListener { changeShape(two) }
        threeButton.setOnClickListener { extend(three) }
        fourButton.setOnClickListener { layoutTransition() }
    }

    fun changeColor(view: View) {
        view.animate()
        val animator = AnimatorInflater.loadAnimator(this, R.animator.change_color) as ObjectAnimator
        animator.apply {
            target = view
            setEvaluator(ArgbEvaluator())
        }.start()
    }

    fun changeShape(view: ImageView) {

        fun squareIn() = view.animate()
                .setDuration(1000)
                .alpha(1f)

        fun circleOut() = view.animate()
                .setDuration(1000)
                .alpha(0f)
                .withEndAction {
                    view.apply {
                        setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.square))
                        alpha = 0f
                    }
                    squareIn()
                }

        fun circleIn() = view.animate()
                .setDuration(1000)
                .alpha(1f)
                .withEndAction {
                    circleOut()
                }

        fun squareOut() = view.animate()
                .setDuration(1000)
                .alpha(0f)
                .withEndAction {
                    view.apply {
                        setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.circle))
                        alpha = 0f
                    }
                    circleIn()
                }
        squareOut()
    }

    fun extend(view: View) {
        view.apply { pivotX = 0f; pivotY = 0f }

        //ViewPropertyAnimator
        view.animate()
                .scaleX(2f)
                .setDuration(1000)
                .withEndAction {
                    view.animate()
                            .scaleX(1f)
                            .setDuration(1000)
                }
        /*.setListener(object : AnimatorListenerSimple {
            override fun onAnimationEnd(animation: Animator?) {
                view.animate()
                        .scaleX(1f)
                        .setDuration(1000)
            }
        })*/

        //ObjectAnimator
        /*ObjectAnimator.ofFloat(view, "scaleX", 2f).apply {
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
        }.start()*/
    }

    fun layoutTransition() {
        val layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGING) }
        container.layoutTransition = layoutTransition
        container.addView(Button(this))
    }
}
