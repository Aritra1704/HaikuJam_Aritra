package com.arpaul.haikujam_aritra;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private boolean isCircle = false;
    private int DELAY_IN_MILLIS = 500;
    GradientDrawable gradientDrawable;
    @BindView(R.id.clParent)
    protected ConstraintLayout clParent;
    @BindView(R.id.civTop)
    protected CircleImageView civTop;
    @BindView(R.id.civLeft)
    protected CircleImageView civLeft;
    @BindView(R.id.civRight)
    protected CircleImageView civRight;
    @BindView(R.id.tvStages)
    protected TextView tvStages;
    @BindView(R.id.card_view)
    protected CardView card_view;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    int mWidth, mHeight;

    int state = CARD_TO_CIRCLE;

    private final static int CARD_TO_CIRCLE = 1;
    private final static int MOVE_TOP_CENTRE = 2;
    private final static int MOVE_LEFT_CENTRE = 3;
    private final static int MOVE_RIGHT_CENTRE = 4;
    private final static int CIRCLE_TO_CARD = 5;

    private final static int animationDelay = 1000 * 2; //Millis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mParent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mParent);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(getResources().getDimension(R.dimen.m_15));
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(ContextCompat.getColor(this, R.color.colorWhite));
        card_view.setBackground(gradientDrawable);

        mWidth= this.getResources().getDisplayMetrics().widthPixels;
        mHeight= this.getResources().getDisplayMetrics().heightPixels;

        disappearSmallCircles();

        tvStages.setText("Stage " + state);
    }

    public void pressClicked(View view) {
        switch (state) {
            case CARD_TO_CIRCLE:
//                card_view.setRadius(getResources().getDimension(R.dimen.m_150));
                makeCircle();
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
                makeSquare();
//                state = CARD_TO_CIRCLE;
                break;
        }
        tvStages.setText("Stage " + (state - 1));
    }

    private void disappearSmallCircles() {
        ObjectAnimator fadeInTop = ObjectAnimator.ofFloat(civTop, "alpha", 1f, 0f);
        ObjectAnimator fadeInLeft = ObjectAnimator.ofFloat(civLeft, "alpha", 1f, 0f);
        ObjectAnimator fadeInRight = ObjectAnimator.ofFloat(civRight, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5);
        animatorSet.playTogether(fadeInTop, fadeInLeft, fadeInRight);
        animatorSet.start();
    }

    private void showSmallCircles() {
        ObjectAnimator fadeInTop = ObjectAnimator.ofFloat(civTop, "alpha", 0f, 1f);
        ObjectAnimator fadeInLeft = ObjectAnimator.ofFloat(civLeft, "alpha", 0f, 1f);
        ObjectAnimator fadeInRight = ObjectAnimator.ofFloat(civRight, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeInTop, fadeInLeft, fadeInRight);
        animatorSet.start();
    }

    private void moveTopCenter() {
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(civTop, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(civTop, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationY);
        animatorSet.start();
    }

    private void moveLeftCenter() {
        ObjectAnimator shiftAnimationX = ObjectAnimator.ofFloat(civLeft, "x", mWidth/2);
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(civLeft, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(civLeft, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationX, shiftAnimationY);
        animatorSet.start();
    }

    private void moveRightCenter() {
        ObjectAnimator shiftAnimationX = ObjectAnimator.ofFloat(civRight, "x", mWidth/2);
        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(civRight, "y", mHeight/2);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(civRight, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(fadeOut, shiftAnimationX, shiftAnimationY);
        animatorSet.start();
    }

    private void makeCircle() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable,
                        "cornerRadius",
                        getResources().getDimension(R.dimen.m_15),
                        getResources().getDimension(R.dimen.m_150));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.play(cornerAnimation);
        animatorSet.start();
    }

    private void makeSquare() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable,
                        "cornerRadius",
                        getResources().getDimension(R.dimen.m_150),
                        getResources().getDimension(R.dimen.m_15));

        ObjectAnimator shiftAnimationY = ObjectAnimator.ofFloat(card_view, "y", 0 + getResources().getDimension(R.dimen.m_35));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animationDelay);
        animatorSet.playTogether(cornerAnimation, shiftAnimationY);
        animatorSet.start();
    }
}
