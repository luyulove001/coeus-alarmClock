package net.tatans.coeus.alarm.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;


import net.tatans.coeus.alarm.R;

import java.util.ArrayList;

/**
 * 
 * @author SiliPing Purpose:自定义view动画 Create Time: 2015-7-2 下午6:17:05
 */
public class RippleBackground extends RelativeLayout {

	private static final int DEFAULT_RIPPLE_COUNT = 6;
	private static final int DEFAULT_DURATION_TIME = 3000;
	private static final float DEFAULT_SCALE = 6.0f;
	private static final int DEFAULT_FILL_TYPE = 0;

	private int rippleColor;
	private float rippleStrokeWidth;
	private float rippleRadius;
	private int rippleDurationTime;
	private int rippleAmount;
	private int rippleDelay;
	private float rippleScale;
	private int rippleType;
	private Paint paint;
	private boolean animationRunning = false;
	private AnimatorSet animatorSet;
	private ArrayList<Animator> animatorList;
	private LayoutParams rippleParams;
	private ArrayList<RippleView> rippleViewList = new ArrayList<RippleView>();

	public RippleBackground(Context context) {
		super(context);
	}

	public RippleBackground(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RippleBackground(Context context, AttributeSet attrs,
							int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	@SuppressLint("NewApi")
	private void init(final Context context, final AttributeSet attrs) {
		// 使用isInEditMode解决可视化编辑器无法识别自定义控件
		if (isInEditMode())
			return;

		if (null == attrs) {
			throw new IllegalArgumentException(
					"Attributes should be provided to this view,");
		}

		final TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RippleBackground);
		rippleColor = typedArray.getColor(
				R.styleable.RippleBackground_rb_color,
				getResources().getColor(R.color.rippelColor));
		rippleStrokeWidth = typedArray.getDimension(
				R.styleable.RippleBackground_rb_strokeWidth, getResources()
						.getDimension(R.dimen.rippleStrokeWidth));
		rippleRadius = typedArray.getDimension(
				R.styleable.RippleBackground_rb_radius, getResources()
						.getDimension(R.dimen.rippleRadius));
		rippleDurationTime = typedArray
				.getInt(R.styleable.RippleBackground_rb_duration,
						DEFAULT_DURATION_TIME);
		rippleAmount = typedArray.getInt(
				R.styleable.RippleBackground_rb_rippleAmount,
				DEFAULT_RIPPLE_COUNT);
		rippleScale = typedArray.getFloat(
				R.styleable.RippleBackground_rb_scale, DEFAULT_SCALE);
		rippleType = typedArray.getInt(R.styleable.RippleBackground_rb_type,
				DEFAULT_FILL_TYPE);
		typedArray.recycle();

		rippleDelay = rippleDurationTime / rippleAmount;

		paint = new Paint();
		paint.setAntiAlias(true);
		if (rippleType == DEFAULT_FILL_TYPE) {
			rippleStrokeWidth = 0;
			paint.setStyle(Paint.Style.FILL);
		} else
			paint.setStyle(Paint.Style.STROKE);
		paint.setColor(rippleColor);

		rippleParams = new LayoutParams(
				(int) (2 * (rippleRadius + rippleStrokeWidth)),
				(int) (2 * (rippleRadius + rippleStrokeWidth)));
		rippleParams.addRule(CENTER_IN_PARENT, TRUE);

		animatorSet = new AnimatorSet();
		animatorSet.setDuration(rippleDurationTime);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animatorList = new ArrayList<Animator>();

		for (int i = 0; i < rippleAmount; i++) {
			RippleView rippleView = new RippleView(getContext());
			addView(rippleView, rippleParams);
			rippleViewList.add(rippleView);
			final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(
					rippleView, "ScaleX", 1.0f, rippleScale);
			scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
			scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
			scaleXAnimator.setStartDelay(i * rippleDelay);
			animatorList.add(scaleXAnimator);
			final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(
					rippleView, "ScaleY", 1.0f, rippleScale);
			scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
			scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
			scaleYAnimator.setStartDelay(i * rippleDelay);
			animatorList.add(scaleYAnimator);
			final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(
					rippleView, "Alpha", 1.0f, 0f);
			alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
			alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
			alphaAnimator.setStartDelay(i * rippleDelay);
			animatorList.add(alphaAnimator);
		}

		animatorSet.playTogether(animatorList);
	}

	private class RippleView extends View {
		public RippleView(Context context) {
			super(context);
			this.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			int radius = (Math.min(getWidth(), getHeight())) / 2;
			canvas.drawCircle(radius, radius, radius - rippleStrokeWidth, paint);
		}
	}

	@SuppressLint("NewApi")
	public void startRippleAnimation() {
		if (!isRippleAnimationRunning()) {
			for (RippleView rippleView : rippleViewList) {
				rippleView.setVisibility(VISIBLE);
			}
			animatorSet.start();
			animationRunning = true;
		}
	}

	@SuppressLint("NewApi")
	public void stopRippleAnimation() {
		if (isRippleAnimationRunning()) {
			animatorSet.end();
			animationRunning = false;
		}
	}

	public boolean isRippleAnimationRunning() {
		return animationRunning;
	}
}
