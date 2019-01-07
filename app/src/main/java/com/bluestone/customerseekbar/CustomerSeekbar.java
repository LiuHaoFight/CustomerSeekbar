package com.bluestone.customerseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomerSeekbar extends RelativeLayout {

    private static final String TAG = CustomerSeekbar.class.getSimpleName();
    private RelativeLayout mSeekbar;
    private LinearLayout mBackground;
    private TextView thumb;
    private View mBackView;
    private int mViewHeight = 0;
    private int mThumbHeight = 0;
    private int mMax = 100;
    private int mLastProgress = 0;
    private int mProgress = 0;
    private boolean mIsFocused = false;
    private IOnProgressChangeListener mIOnProgressChangeListener;

    public interface IOnProgressChangeListener {
        void onProgressChanged(int progress, boolean byUser);
    }

    public CustomerSeekbar(Context context) {
        this(context, null);
    }

    public CustomerSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerSeekbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {

            mThumbHeight = thumb.getMeasuredHeight();

            ViewGroup.LayoutParams parameter = mBackView.getLayoutParams();
            mViewHeight = mSeekbar.getMeasuredHeight();
            parameter.height = mViewHeight;
            parameter.width = mSeekbar.getMeasuredWidth();
            mBackView.setLayoutParams(parameter);
            mIsFocused = true;
            updateProgress();
        }
    }

    private void initLayout(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mSeekbar = (RelativeLayout) layoutInflater.inflate(R.layout.seekbar, this);
        mBackground = findViewById(R.id.backgroud);
        thumb = findViewById(R.id.thumb);
        mBackView = findViewById(R.id.backview);
        mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                ViewGroup.LayoutParams parameter = mBackground.getLayoutParams();
                int maxHeight = mViewHeight;
                int thumbY = 0;
                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        parameter.height = (int) (maxHeight - motionEvent.getY());
                        if (parameter.height > maxHeight - mThumbHeight / 2) {
                            parameter.height = maxHeight - mThumbHeight / 2;
                        }
                        if (parameter.height < mThumbHeight / 2) {
                            parameter.height = mThumbHeight / 2;
                        }
                        mBackground.setLayoutParams(parameter);
                        thumbY = (int) (motionEvent.getY() - mThumbHeight / 2);
                        if (thumbY < 0) {
                            thumbY = 0;
                        }
                        if (thumbY > maxHeight - mThumbHeight) {
                            thumbY = maxHeight - mThumbHeight;
                        }

                        mProgress = mMax - Math.round((mMax * thumbY) / (maxHeight - mThumbHeight));
                        if (mProgress != mLastProgress) {
                            if (mIOnProgressChangeListener != null) {
                                mIOnProgressChangeListener.onProgressChanged(mProgress, true);
                            }
                            mLastProgress = mProgress;
                            if (mProgress == 0) {
                                thumbY = maxHeight - mThumbHeight;
                            }
                            if (mProgress == mMax) {
                                thumbY = 0;
                            }
                            thumb.setY(thumbY);
                            thumb.setText(mProgress + "");
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        parameter.height = (int) (maxHeight - motionEvent.getY());
                        if (parameter.height > maxHeight - mThumbHeight / 2) {
                            parameter.height = maxHeight - mThumbHeight / 2;
                        }
                        if (parameter.height < mThumbHeight / 2) {
                            parameter.height = mThumbHeight / 2;
                        }
                        mBackground.setLayoutParams(parameter);
                        thumbY = (int) (motionEvent.getY() - mThumbHeight / 2);
                        if (thumbY < 0) {
                            thumbY = 0;
                        }
                        if (thumbY > maxHeight - mThumbHeight) {
                            thumbY = maxHeight - mThumbHeight;
                        }
                        mProgress = mMax - Math.round((mMax * thumbY) / (maxHeight - mThumbHeight));
                        if (mProgress != mLastProgress) {
                            if (mIOnProgressChangeListener != null) {
                                mIOnProgressChangeListener.onProgressChanged(mProgress, true);
                            }
                            mLastProgress = mProgress;
                            if (mProgress == 0) {
                                thumbY = maxHeight - mThumbHeight;
                            }
                            if (mProgress == mMax) {
                                thumbY = 0;
                            }
                            thumb.setY(thumbY);
                            thumb.setText(mProgress + "");
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                }
                return true;
            }
        });
    }

    public void setMax(int max) {
        if (max > 0) {
            mMax = max;
            if (mProgress > mMax) {
                mProgress = mMax;
            }
            updateProgress();
        }
    }

    public void setProgress(int progress) {
        if (progress >= 0 && progress <= mMax) {
            mProgress = progress;
            mLastProgress = mProgress;
            if (mIsFocused) {
                updateProgress();
            }
            if (mIOnProgressChangeListener != null) {
                mIOnProgressChangeListener.onProgressChanged(mProgress, false);
            }
        }
    }

    private void updateProgress() {
        ViewGroup.LayoutParams parameter = mBackground.getLayoutParams();

        int length = (mProgress * (mViewHeight - mThumbHeight)) / mMax;
        parameter.height = length + mThumbHeight / 2;
        if (parameter.height > mViewHeight - mThumbHeight / 2) {
            parameter.height = mViewHeight - mThumbHeight / 2;
        }
        if (parameter.height < mThumbHeight / 2) {
            parameter.height = mThumbHeight / 2;
        }
        mBackground.setLayoutParams(parameter);
        int thumbY = (int) (mViewHeight - length - mThumbHeight / 2);
        if (thumbY < 0) {
            thumbY = 0;
        }
        if (thumbY > mViewHeight - mThumbHeight) {
            thumbY = mViewHeight - mThumbHeight;
        }
        thumb.setY(thumbY);
        thumb.setText(mProgress + "");
    }

    public void setOnProgressChangeListener(IOnProgressChangeListener listener) {
        mIOnProgressChangeListener = listener;
    }
}
