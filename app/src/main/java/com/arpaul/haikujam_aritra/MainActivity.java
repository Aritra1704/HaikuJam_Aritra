package com.arpaul.haikujam_aritra;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private CardView card_view;
    private boolean isCircle = false;
    private int DELAY_IN_MILLIS = 500;
    GradientDrawable gradientDrawable;
    private FloatingActionButton fabTop, fabLeft, fabRight;
    int mWidth, mHeight;

    int state = CARD_TO_CIRCLE;

    private final static int CARD_TO_CIRCLE = 1;
    private final static int MOVE_TOP_CENTRE = 2;
    private final static int MOVE_LEFT_CENTRE = 3;
    private final static int MOVE_RIGHT_CENTRE = 4;
    private final static int CIRCLE_TO_CARD = 5;

    private final static int animationDelay = 500 * 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mParent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mParent);
//        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        card_view = findViewById(R.id.card_view);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(30.0f);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        mParent.setBackground(gradientDrawable);

        mWidth= this.getResources().getDisplayMetrics().widthPixels;
        mHeight= this.getResources().getDisplayMetrics().heightPixels;

        fabTop = findViewById(R.id.fabTop);
        fabLeft = findViewById(R.id.fabLeft);
        fabRight = findViewById(R.id.fabRight);

        disappearSmallCircles();
    }

    public void pressClicked(View view) {
        switch (state) {
            case CARD_TO_CIRCLE:
                card_view.setRadius(getResources().getDimension(R.dimen.m_150));
                showSmallCircles();
                state = MOVE_TOP_CENTRE;
                break;

            case MOVE_TOP_CENTRE:
                moveTopCenter();

                state = MOVE_LEFT_CENTRE;
                break;

            case MOVE_LEFT_CENTRE:
                moveLeftCenter();
                state = MOVE_RIGHT_CENTRE;
                break;

            case MOVE_RIGHT_CENTRE:
                moveRightCenter();
                state = CIRCLE_TO_CARD;
                break;

            case CIRCLE_TO_CARD:
                card_view.setRadius(getResources().getDimension(R.dimen.m_15));
                state = CARD_TO_CIRCLE;
                break;
        }
    }

    private void disappearSmallCircles() {
        ObjectAnimator fadeInTop = ObjectAnimator.ofFloat(fabTop, "alpha", 1f, 0f);
        ObjectAnimator fadeInLeft = ObjectAnimator.ofFloat(fabLeft, "alpha", 1f, 0f);
        ObjectAnimator fadeInRight = ObjectAnimator.ofFloat(fabRight, "alpha", 1f, 0f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5);
        animatorSet.playTogether(fadeInTop, fadeInLeft, fadeInRight);
        animatorSet.start();
    }

    private void showSmallCircles() {
        ObjectAnimator fadeInTop = ObjectAnimator.ofFloat(fabTop, "alpha", 0f, 1f);
        ObjectAnimator fadeInLeft = ObjectAnimator.ofFloat(fabLeft, "alpha", 0f, 1f);
        ObjectAnimator fadeInRight = ObjectAnimator.ofFloat(fabRight, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeInTop, fadeInLeft, fadeInRight);
        animatorSet.start();
    }


    private void moveTopCenter() {
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(fabTop, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fabTop, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationY);
        sendToInitialLoc(animatorSet, fabTop);
        animatorSet.start();
    }

    private void moveLeftCenter() {
        ObjectAnimator shiftAnimationX = ObjectAnimator.ofFloat(fabLeft, "x", mWidth/2);
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(fabLeft, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fabLeft, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationX, shiftAnimationY);
        sendToInitialLoc(animatorSet, fabLeft);
        animatorSet.start();
    }

    private void moveRightCenter() {
        ObjectAnimator shiftAnimationX = ObjectAnimator.ofFloat(fabRight, "x", mWidth/2);
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(fabRight, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fabRight, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationX, shiftAnimationY);
        sendToInitialLoc(animatorSet, fabRight);
        animatorSet.start();
    }

    private void sendToInitialLoc(AnimatorSet animatorSet, final View view) {
        final int[] array = new int[2];
        view.getLocationOnScreen(array);
        Log.d("sendToInitialLoc", "Width>> " + array[0] + " height>> " + array[1] + " view height>> " + view.getHeight());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator goBackX = ObjectAnimator.ofFloat(view, "x", array[0]);
                ObjectAnimator goBackY = ObjectAnimator.ofFloat(view, "y", array[1] - view.getHeight() - getResources().getDimension(R.dimen.fab_margin) * 2);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(animationDelay);
                animatorSet.playTogether(goBackX, goBackY);
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    private void animateToCircle() {
        final float currntRad = getResources().getDimension(R.dimen.m_15);
        final float maxRad = getResources().getDimension(R.dimen.m_150);

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", 30f, 200.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500 * 4);
        animatorSet.play(cornerAnimation);
//        animatorSet.playTogether(cornerAnimation, shiftAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("onAnimationStart", "current >> " + currntRad + " max >> " + maxRad);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("onAnimationEnd", "current >> " + currntRad + " max >> " + maxRad);
                card_view.setRadius(maxRad);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void animateToSquare() {
        float currntRad = getResources().getDimension(R.dimen.m_150);
        float minRad = getResources().getDimension(R.dimen.m_15);

        Log.d("animateToSquare", "current >> " + currntRad + " min >> " + minRad);
        boolean handler;
        for(;currntRad > minRad; currntRad--) {
            final float val = currntRad;
            handler = new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_view.setRadius(val);
                }
            }, DELAY_IN_MILLIS);
        }
        isCircle = false;
    }

    private void animateReveal() {
        int x = card_view.getRight();
        int y = card_view.getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(getResources().getDimension(R.dimen.m_150), getResources().getDimension(R.dimen.m_150));

        Animator anim = ViewAnimationUtils.createCircularReveal(card_view, x, y, startRadius, endRadius);

//        layoutButtons.setVisibility(View.VISIBLE);
        anim.start();
    }
}
