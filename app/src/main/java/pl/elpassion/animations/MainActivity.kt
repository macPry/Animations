package pl.elpassion.animations

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
    }

    fun changeColor(view: View) {
        val animator = AnimatorInflater.loadAnimator(this, R.animator.change_color) as ObjectAnimator
        animator.apply {
            target = view
            setEvaluator(ArgbEvaluator())
        }.start()
    }

    fun changeShape(view: ImageView) {
        val squareOutAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
                .apply { duration = 1000 }
        val circleInAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
                .apply { duration = 1000 }
        val circleOutAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
                .apply { duration = 1000 }
        val squareInAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
                .apply { duration = 1000 }
        squareOutAnim.setAnimationListener(object : AnimationListenerSimple {
            override fun onAnimationEnd(animation: Animation?) {
                view.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.circle))
                view.startAnimation(circleInAnim)
            }
        })
        circleInAnim.setAnimationListener(object : AnimationListenerSimple {
            override fun onAnimationEnd(animation: Animation?) {
                view.startAnimation(circleOutAnim)
            }
        })
        circleOutAnim.setAnimationListener(object : AnimationListenerSimple {
            override fun onAnimationEnd(animation: Animation?) {
                view.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.square))
                view.startAnimation(squareInAnim)
            }
        })
        view.startAnimation(squareOutAnim)
    }

    fun extend(view: View) {
        view.apply { pivotX = 0f; pivotY = 0f }

        //ViewPropertyAnimator
        view.animate()
                .scaleX(2f)
                .setDuration(1000)
                .withEndAction { view.animate().scaleX(1f).setDuration(1000) }
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
}
