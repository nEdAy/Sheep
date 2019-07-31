package cn.neday.sheep.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * 阻尼效果的scrollview
 *
 * @author nEdAy
 */
public class DampView extends ScrollView {
    private static final int LEN = 0xc8;
    private static final int DURATION = 500;
    private static final int MAX_DY = 200;
    private final int[] li = new int[2];
    private final int[] li2 = new int[2];
    private Scroller mScroller;
    private TouchTool tool;
    private int top;
    private float startY;
    private int imageViewH;
    private ImageView imageView;
    private boolean scrollerType;
    private IRefreshListener refreshListener;
    private float lastLy;
    private boolean startIsTop = true;

    public DampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public DampView(Context context) {
        super(context);
    }

    public void setOnRefreshListener(IRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        int action = event.getAction();
        if (!mScroller.isFinished()) {
            return super.onTouchEvent(event);
        }
        float currentY = event.getY();
        imageView.getLocationInWindow(li);
        getLocationOnScreen(li2);
        imageView.getTop();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (li[1] != li2[1]) {// 判断开始触摸时，imageView和窗口顶部对齐没
                    startIsTop = false;
                }
                top = imageView.getBottom();
                imageViewH = imageView.getHeight();
                startY = currentY;
                tool = new TouchTool(imageView.getBottom());
                break;
            case MotionEvent.ACTION_MOVE:
                if (!startIsTop && li[1] == li2[1]) {
                    startY = currentY;
                    startIsTop = true;
                }
                if (imageView.isShown() && imageView.getTop() >= 0) {
                    if (tool != null) {
                        int t = tool.getScrollY(currentY - startY);
                        if (!scrollerType && currentY < lastLy && imageView.getHeight() > imageViewH) {
                            scrollTo(0, 0);
                            imageView.getLocationInWindow(li);
                            getLocationOnScreen(li2);
                            android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            params.height = t;
                            imageView.setLayoutParams(params);
                            if (imageView.getHeight() == imageViewH && li[1] == li2[1]) {
                                scrollerType = true;
                            }
                            if (startIsTop && li[1] != li2[1]) {
                                startIsTop = false;
                            }
                        }
                        if (t >= top && t <= imageView.getBottom() + LEN && li[1] == li2[1] && currentY > lastLy) {
                            android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            params.height = t;
                            imageView.setLayoutParams(params);
                        }
                    }
                    scrollerType = false;
                }
                lastLy = currentY;
                break;
            case MotionEvent.ACTION_UP:
                if (li[1] == li2[1]) {
                    scrollerType = true;
                    mScroller.startScroll(imageView.getLeft(), imageView.getBottom(), 0 - imageView.getLeft(),
                            imageViewH - imageView.getBottom(), DURATION);
                    if (imageViewH != imageView.getBottom() && refreshListener != null) {
                        refreshListener.onRefresh();
                    }
                    invalidate();
                }
                startIsTop = true;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            imageView.layout(0, 0, x + imageView.getWidth(), y);
            invalidate();
            if (!mScroller.isFinished() && scrollerType && y > MAX_DY) {
                android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.height = y;
                imageView.setLayoutParams(params);
            }
        }
    }

    public interface IRefreshListener {
        void onRefresh();
    }

    public class TouchTool {

        private final int startY;

        TouchTool(int startY) {
            super();
            this.startY = startY;
        }

        int getScrollY(float dy) {
            return (int) (startY + dy / 2.5F);
        }
    }
}
