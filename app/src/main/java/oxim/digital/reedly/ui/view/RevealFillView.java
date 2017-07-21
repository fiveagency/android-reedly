package oxim.digital.reedly.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import oxim.digital.reedly.R;

public final class RevealFillView extends View {

    private static final float STROKE_WIDTH = 3.0f;
    private static final float OPAQUE = 1.0f;
    private static final float TRANSPARENT = 0.0f;

    private static final int DEFAULT_ANIMATION_DURATION_MS = 300;

    private final Region viewRegion = new Region();
    private final Path circlePath = new Path();
    private final RectF circleRect = new RectF();

    private final ValueAnimator opacityAnimator;

    private final Paint fillPaint;

    private int width;
    private int height;

    private int circleRadius;

    private float x;
    private float y;

    private long animationDuration = DEFAULT_ANIMATION_DURATION_MS;

    public RevealFillView(final Context context) {
        this(context, null);
    }

    public RevealFillView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealFillView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        fillPaint.setStrokeWidth(STROKE_WIDTH);
        fillPaint.setColor(Color.BLACK);

        if (attrs != null) {
            parseAttributes(attrs);
        }

        opacityAnimator = ValueAnimator.ofFloat(OPAQUE, TRANSPARENT);
        opacityAnimator.setDuration(animationDuration);
        opacityAnimator.addUpdateListener(animation -> setAlpha((float) animation.getAnimatedValue()));
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RevealFillView, 0, 0);
        try {
            fillPaint.setColor(typedArray.getColor(R.styleable.RevealFillView_revealFillColor, Color.BLACK));
            animationDuration = typedArray.getInteger(R.styleable.RevealFillView_animationDuration, DEFAULT_ANIMATION_DURATION_MS);
        } finally {
            typedArray.recycle();
        }
    }

    public void setFillColor(final @ColorInt int color) {
        fillPaint.setColor(color);
    }

    public void setAnimationDuration(final long animationDurationMillis) {
        this.animationDuration = animationDurationMillis;
    }

    @Override
    protected void onSizeChanged(final int newWidth, final int newHeight, final int oldw, final int oldh) {
        super.onSizeChanged(newWidth, newHeight, oldw, oldh);
        width = newWidth;
        height = newHeight;
        x = width / 2;
        y = height / 2;

        final Rect viewRect = new Rect(0, 0, width, height);
        viewRegion.set(viewRect);

        createCirclePath();
    }

    private void createCirclePath() {
        circleRect.set(x - circleRadius, y - circleRadius, x + circleRadius, y + circleRadius);
        circlePath.reset();
        circlePath.addOval(circleRect, Path.Direction.CW);
        circlePath.close();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        drawCircle(canvas);
    }

    private void drawCircle(final Canvas canvas) {
        canvas.drawOval(circleRect, fillPaint);
    }

    public Animator startFillAnimation() {
        return startAnimation(false);
    }

    @NonNull
    private Animator startAnimation(final boolean reversed) {
        if (opacityAnimator.isRunning()) {
            opacityAnimator.cancel();
        }
        this.x = getWidth() / 2;
        this.y = getHeight() / 2;
        setAlpha(1.0f);
        final int maxDrawRadius = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) + 10;
        final ValueAnimator radiusAnimator = reversed ? ValueAnimator.ofInt(maxDrawRadius, 0) : ValueAnimator.ofInt(0, maxDrawRadius);
        radiusAnimator.setDuration(animationDuration);
        radiusAnimator.setInterpolator(reversed ? new DecelerateInterpolator() : new AccelerateInterpolator());
        radiusAnimator.addUpdateListener(animation -> {
            circleRadius = (int) animation.getAnimatedValue();
            createCirclePath();
            invalidate();
        });
        radiusAnimator.start();
        return radiusAnimator;
    }

    public void startHideAnimation() {
        opacityAnimator.start();
    }
}
