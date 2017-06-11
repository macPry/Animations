package pl.elpassion.animations

import android.animation.*
import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
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
        image.setOnClickListener { sharedElementTransition() }
    }

    fun changeColor(view: View) {
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

        //ViewAnimation
        val scaleAnimation = ScaleAnimation(1f, 2f, 1f, 1f).apply {
            duration = 1000
            repeatMode = Animation.REVERSE
            repeatCount = 1
        }
        view.startAnimation(scaleAnimation)

        /*
        //ObjectAnimator
        ObjectAnimator.ofFloat(view, "scaleX", 2f).apply {
            duration = 1000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
        }.start()
        */

        /*
        //ViewPropertyAnimator
        view.animate()
                .scaleX(2f)
                .setDuration(1000)
                .withEndAction {
                    view.animate()
                            .scaleX(1f)
                            .setDuration(1000)
                }*/
        /*.setListener(object : AnimatorListenerSimple {
            override fun onAnimationEnd(animation: Animator?) {
                view.animate()
                        .scaleX(1f)
                        .setDuration(1000)
            }
        })
        */
    }

    fun layoutTransition() {
        val layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGING) }
        container.layoutTransition = layoutTransition
        container.addView(Button(this))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun sharedElementTransition() {
        val intent = Intent(this, TransitionActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, image, "image")
        startActivity(intent, options.toBundle())
    }
}
