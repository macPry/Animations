package pl.elpassion.animations

import android.animation.Animator
import android.view.animation.Animation

interface AnimatorListenerSimple : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationRepeat(animation: Animator?) {}
}

interface AnimationListenerSimple : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {}
    override fun onAnimationEnd(animation: Animation?) {}
    override fun onAnimationRepeat(animation: Animation?) {}
}
