/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.beeplayer.bs.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * ��ƣ�PullToRefreshView.java ����������ˢ�ºͼ��ظ���View.
 */
public class PullToRefreshView extends LinearLayout {
	/** ʱ����. */
	public static final String dateFormatHMS = "HH:mm:ss";
	/** ������. */
	private Context mContext = null;

	/** ����ˢ�µĿ���. */
	private boolean mEnablePullRefresh = true;

	/** ���ظ��Ŀ���. */
	private boolean mEnableLoadMore = true;

	/** x��һ�α����. */
	private int mLastMotionX;

	/** y��һ�α����. */
	private int mLastMotionY;

	/** header view. */
	private ListViewHeader mHeaderView;

	/** footer view. */
	private ListViewFooter mFooterView;

	/** list or grid. */
	private AdapterView<?> mAdapterView;

	/** Scrollview. */
	private ScrollView mScrollView;

	/** header view �߶�. */
	private int mHeaderViewHeight;

	/** footer view �߶�. */
	private int mFooterViewHeight;

	/** ����״̬. */
	private int mPullState;

	/** �ϻ�����. */
	private static final int PULL_UP_STATE = 0;

	/** ��������. */
	private static final int PULL_DOWN_STATE = 1;

	/** ��һ�ε�����. */
	private int mCount = 0;

	/** ��������ˢ��. */
	private boolean mPullRefreshing = false;

	/** ���ڼ��ظ��. */
	private boolean mPullLoading = false;

	/** Footer���ظ�������. */
	private OnFooterLoadListener mOnFooterLoadListener;

	/** Header����ˢ�¼�����. */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/** UI��ƵĻ�׼���. */
	public static int UI_WIDTH = 720;

	/** UI��ƵĻ�׼�߶�. */
	public static int UI_HEIGHT = 1280;

	/** UI��Ƶ��ܶ�. */
	public static int UI_DENSITY = 2;

	/**
	 * �����������Ļ��С����.
	 * 
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		// Ϊ�˼��ݳߴ�С�ܶȴ�����
		if (mDisplayMetrics.scaledDensity > UI_DENSITY) {
			// �ܶ�
			if (mDisplayMetrics.widthPixels > UI_WIDTH) {
				value = value * (1.3f - 1.0f / mDisplayMetrics.scaledDensity);
			} else if (mDisplayMetrics.widthPixels < UI_WIDTH) {
				value = value * (1.0f - 1.0f / mDisplayMetrics.scaledDensity);
			}
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	public void setVisible() {
		mFooterView.setVisibility(View.VISIBLE);
	}

	/**
	 * ��ȡ��Ļ�ߴ����ܶ�.
	 * 
	 * @param context
	 *            the context
	 * @return mDisplayMetrics
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		Resources mResources;
		if (context == null) {
			mResources = Resources.getSystem();

		} else {
			mResources = context.getResources();
		}
		// DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
		// xdpi=160.421, ydpi=159.497}
		// DisplayMetrics{density=2.0, width=720, height=1280,
		// scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
		DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
		return mDisplayMetrics;
	}

	/**
	 * �����������Ļ��С����.
	 * 
	 * @param displayWidth
	 *            the display width
	 * @param displayHeight
	 *            the display height
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		if (pxValue == 0) {
			return 0;
		}
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / UI_WIDTH;
			float scaleHeight = (float) displayHeight / UI_HEIGHT;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

	/**
	 * ����PX padding.
	 * 
	 * @param view
	 *            the view
	 * @param left
	 *            the left padding in pixels
	 * @param top
	 *            the top padding in pixels
	 * @param right
	 *            the right padding in pixels
	 * @param bottom
	 *            the bottom padding in pixels
	 */
	public static void setPadding(View view, int left, int top, int right,
			int bottom) {
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}

	/**
	 * �������ִ�С,�������õĺô������ֵĴ�С�����ܶ��йأ� �ܹ�ʹ���ִ�С�ڲ�ͬ����Ļ����ʾ������ȷ
	 * 
	 * @param textView
	 *            button
	 * @param sizePixels
	 *            pxֵ
	 * @return
	 */
	public static void setTextSize(TextView textView, float sizePixels) {
		float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
	}

	/**
	 * �����������Ļ��С�����ı�.
	 * 
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleTextValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		// Ϊ�˼��ݳߴ�С�ܶȴ�����
		if (mDisplayMetrics.scaledDensity > 2) {
			// ��С���ܶȷ�֮һ
			// value = value*(1.1f - 1.0f/mDisplayMetrics.scaledDensity);
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	/**
	 * �������view ���ͨ��getMeasuredWidth()��ȡ��Ⱥ͸߶�.
	 * 
	 * @param view
	 *            Ҫ������view
	 * @return �������view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * ��������ȡ��ʾ��ǰ����ʱ����ַ�.
	 * 
	 * @param format
	 *            ��ʽ���ַ��磺"yyyy-MM-dd HH:mm:ss"
	 * @return String String���͵ĵ�ǰ����ʱ��
	 */
	public static String getCurrentDate(String format) {
		Log.d("DateUtil.class", "getCurrentDate:" + format);
		String curDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curDateTime;

	}

	/**
	 * ��������ȡsrc�е�ͼƬ��Դ.
	 * 
	 * @param src
	 *            ͼƬ��src·�����磨��image/arrow.png����
	 * @return Bitmap ͼƬ
	 */
	public static Bitmap getBitmapFromSrc(String src) {
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(PullToRefreshView.class
					.getResourceAsStream(src));
		} catch (Exception e) {
			Log.d("FileUtil.class", "��ȡͼƬ�쳣��" + e.getMessage());
		}
		return bit;
	}

	/**
	 * ����.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ����.
	 * 
	 * @param context
	 *            the context
	 */
	public PullToRefreshView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * ��ʼ��View.
	 * 
	 * @param context
	 *            the context
	 */
	private void init(Context context) {
		mContext = context;
		this.setOrientation(LinearLayout.VERTICAL);
		// ����HeaderView
		addHeaderView();
	}

	/**
	 * add HeaderView.
	 */
	private void addHeaderView() {
		mHeaderView = new ListViewHeader(mContext);
		mHeaderViewHeight = mHeaderView.getHeaderHeight();
		mHeaderView.setGravity(Gravity.BOTTOM);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		// ����topMargin��ֵΪ����header View�߶�,���������������Ϸ�
		params.topMargin = -(mHeaderViewHeight);
		addView(mHeaderView, params);

	}

	/**
	 * add FooterView.
	 */
	private void addFooterView() {

		mFooterView = new ListViewFooter(mContext);
		mFooterViewHeight = mFooterView.getFooterHeight();

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		addView(mFooterView, params);
	}

	/**
	 * remove FooterView.
	 */
	public void removeFooterView() {
		// removeView(mFooterView);
		mFooterView.setVisibility(View.GONE);
	}

	/**
	 * �ڴ����footer view��֤��ӵ�linearlayout�е����.
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView, GridView and so on; or init ScrollView.
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��������down�¼�,��¼y���
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 �������˶�,< 0�������˶�
			int deltaX = x - mLastMotionX;
			int deltaY = y - mLastMotionY;
			// ���������ƶ��ĳ�ͻ
			if (Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(deltaY) > 10) {
				if (isRefreshViewScroll(deltaY)) {
					return true;
				}
			}

			break;
		}
		return false;
	}

	/*
	 * �����onInterceptTouchEvent()������û������(��onInterceptTouchEvent()������ return
	 * false)����PullToRefreshView ����View������;����������ķ���������(����PullToRefreshView�Լ�������)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				// ִ������
				headerPrepareToRefresh(deltaY);
			} else if (mPullState == PULL_UP_STATE) {
				// ִ������
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		// UP��CANCELִ����ͬ�ķ���
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// ��ʼˢ��
					headerRefreshing();
				} else {
					// ��û��ִ��ˢ�£���������
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				// ������ʲôʱ����ظ��
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					// ��ʼִ��footer ˢ��
					footerLoading();
				} else {
					// ��û��ִ��ˢ�£���������
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;

		}
		return super.onTouchEvent(event);
	}

	/**
	 * �жϻ������򣬺��Ƿ���Ӧ�¼�.
	 * 
	 * @param deltaY
	 *            deltaY > 0 �������˶�,< 0�������˶�
	 * @return true, if is refresh view scroll
	 */
	private boolean isRefreshViewScroll(int deltaY) {

		if (mPullRefreshing || mPullLoading) {
			return false;
		}
		// ����ListView��GridView
		if (mAdapterView != null) {
			// ��view(ListView or GridView)���������
			if (deltaY > 0) {
				// �ж��Ƿ��������ˢ�²���
				if (!mEnablePullRefresh) {
					return false;
				}
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// ���mAdapterView��û�����,������
					// return false;

					mPullState = PULL_DOWN_STATE;
					return true;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 11) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				// �ж��Ƿ�����������ظ�����
				if (!mEnableLoadMore) {
					return false;
				}
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					// ���mAdapterView��û�����,������
					// return false;
					mPullState = PULL_UP_STATE;
					return true;
				}
				// ���һ����view��BottomС�ڸ�View�ĸ߶�˵��mAdapterView�����û������view,
				// ���ڸ�View�ĸ߶�˵��mAdapterView�Ѿ����������
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// ����ScrollView
		if (mScrollView != null) {

			// ��scroll view���������
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				// �ж��Ƿ��������ˢ�²���
				if (!mEnablePullRefresh) {
					return false;
				}
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				// �ж��Ƿ�����������ظ�����
				if (!mEnableLoadMore) {
					return false;
				}
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header ׼��ˢ��,��ָ�ƶ����,��û���ͷ�.
	 * 
	 * @param deltaY
	 *            ��ָ�����ľ���
	 */
	private void headerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}

		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// ��header view��topMargin>=0ʱ��˵��header view��ȫ��ʾ������ ,�޸�header view ����ʾ״̬
		if (newTopMargin >= 0
				&& mHeaderView.getState() != ListViewHeader.STATE_REFRESHING) {
			// ��ʾ�ɿ�ˢ��
			mHeaderView.setState(ListViewHeader.STATE_READY);

		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
			// ��ʾ����ˢ��
			mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		}
	}

	/**
	 * footer ׼��ˢ��,��ָ�ƶ����,��û���ͷ� �ƶ�footer view�߶�ͬ����ƶ�header view
	 * �߶���һ����ͨ���޸�header view��topmargin��ֵ���ﵽ.
	 * 
	 * @param deltaY
	 *            ��ָ�����ľ���
	 */
	private void footerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}
		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// ���header view topMargin �ľ��ֵ���ڻ����header + footer �ĸ߶�
		// ˵��footer view ��ȫ��ʾ�����ˣ��޸�footer view ����ʾ״̬
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterView.getState() != ListViewFooter.STATE_LOADING) {
			mFooterView.setState(ListViewFooter.STATE_READY);
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterView.setState(ListViewFooter.STATE_LOADING);
		}
	}

	/**
	 * �޸�Header view top margin��ֵ.
	 * 
	 * @param deltaY
	 *            the delta y
	 * @return the int
	 */
	private int updateHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		// �����������һ������,��Ϊ��ǰ������Ȼ���ͷ���ֱָ������,�������ˢ�¸���
		// ��ʾ�������������һ�ξ���,Ȼ��ֱ������
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// ͬ���,��������һ������,������ָ���������ʱһ���bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		return params.topMargin;
	}

	/**
	 * ����ˢ��.
	 */
	public void headerRefreshing() {
		mPullRefreshing = true;
		mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
		setHeaderTopMargin(0);
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * ���ظ��.
	 */
	private void footerLoading() {
		mPullLoading = true;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		if (mOnFooterLoadListener != null) {
			mOnFooterLoadListener.onFooterLoad(this);
		}
	}

	/**
	 * ����header view ��topMargin��ֵ.
	 * 
	 * @param topMargin
	 *            the new header top margin
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
	}

	/**
	 * header view ��ɸ��º�ָ���ʼ״̬.
	 */
	public void onHeaderRefreshFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		if (mAdapterView != null) {
			mCount = mAdapterView.getCount();
			// �ж���û�����
			if (mCount > 0) {
				mFooterView.setState(ListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(ListViewFooter.STATE_EMPTY);
			}
		} else {
			mFooterView.setState(ListViewFooter.STATE_READY);
		}

		mPullRefreshing = false;
	}

	/**
	 * footer view ��ɸ��º�ָ���ʼ״̬.
	 */
	public void onFooterLoadFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(ListViewHeader.STATE_NORMAL);
		if (mAdapterView != null) {
			int countNew = mAdapterView.getCount();
			// �ж���û�и�������
			if (countNew > mCount) {
				mFooterView.setState(ListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(ListViewFooter.STATE_NO);
			}
		} else {
			mFooterView.setState(ListViewFooter.STATE_READY);
		}

		mPullLoading = false;
	}

	/**
	 * ��ȡ��ǰheader view ��topMargin.
	 * 
	 * @return the header top margin
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	/**
	 * ��������ˢ�µļ�����.
	 * 
	 * @param headerRefreshListener
	 *            the new on header refresh listener
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	/**
	 * ���ü��ظ��ļ�����.
	 * 
	 * @param footerLoadListener
	 *            the new on footer load listener
	 */
	public void setOnFooterLoadListener(OnFooterLoadListener footerLoadListener) {
		mOnFooterLoadListener = footerLoadListener;
	}

	/**
	 * �򿪻��߹ر�����ˢ�¹���.
	 * 
	 * @param enable
	 *            ���ر��
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
	}

	/**
	 * �򿪻��߹رռ��ظ�๦��.
	 * 
	 * @param enable
	 *            ���ر��
	 */
	public void setLoadMoreEnable(boolean enable) {
		mEnableLoadMore = enable;
	}

	/**
	 * ����ˢ���Ǵ򿪵���.
	 * 
	 * @return true, if is enable pull refresh
	 */
	public boolean isEnablePullRefresh() {
		return mEnablePullRefresh;
	}

	/**
	 * ���ظ���Ǵ򿪵���.
	 * 
	 * @return true, if is enable load more
	 */
	public boolean isEnableLoadMore() {
		return mEnableLoadMore;
	}

	/**
	 * ��������ȡHeader View.
	 * 
	 * @return the header view
	 */
	public ListViewHeader getHeaderView() {
		return mHeaderView;
	}

	/**
	 * ��������ȡFooter View.
	 * 
	 * @return the footer view
	 */
	public ListViewFooter getFooterView() {
		return mFooterView;
	}

	/**
	 * ��������ȡHeader ProgressBar�����������Զ�����ʽ.
	 * 
	 * @return the header progress bar
	 */
	public ProgressBar getHeaderProgressBar() {
		return mHeaderView.getHeaderProgressBar();
	}

	/**
	 * ��������ȡFooter ProgressBar�����������Զ�����ʽ.
	 * 
	 * @return the footer progress bar
	 */
	public ProgressBar getFooterProgressBar() {
		return mFooterView.getFooterProgressBar();
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 * 
	 * @see OnFooterLoadEvent
	 */
	public interface OnFooterLoadListener {

		/**
		 * On footer load.
		 * 
		 * @param view
		 *            the view
		 */
		public void onFooterLoad(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 * 
	 * @see OnHeaderRefreshEvent
	 */
	public interface OnHeaderRefreshListener {

		/**
		 * On header refresh.
		 * 
		 * @param view
		 *            the view
		 */
		public void onHeaderRefresh(PullToRefreshView view);
	}

}

class ListViewHeader extends LinearLayout {

	/** ������. */
	private Context mContext;

	/** ��View. */
	private LinearLayout headerView;

	/** ��ͷͼ��View. */
	private ImageView arrowImageView;

	/** ���ͼ��View. */
	private ProgressBar headerProgressBar;

	/** ��ͷͼ��. */
	private Bitmap arrowImage = null;

	/** �ı���ʾ��View. */
	private TextView tipsTextview;

	/** ʱ���View. */
	private TextView headerTimeView;

	/** ��ǰ״̬. */
	private int mState = -1;

	/** ���ϵĶ���. */
	private Animation mRotateUpAnim;

	/** ���µĶ���. */
	private Animation mRotateDownAnim;

	/** ����ʱ��. */
	private final int ROTATE_ANIM_DURATION = 180;

	/** ��ʾ ����ˢ��. */
	public final static int STATE_NORMAL = 0;

	/** ��ʾ �ɿ�ˢ��. */
	public final static int STATE_READY = 1;

	/** ��ʾ ����ˢ��.... */
	public final static int STATE_REFRESHING = 2;

	/** ������һ�ε�ˢ��ʱ��. */
	private String lastRefreshTime = null;

	/** Header�ĸ߶�. */
	private int headerHeight;

	/**
	 * ��ʼ��Header.
	 * 
	 * @param context
	 *            the context
	 */
	public ListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * ��ʼ��Header.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * ��ʼ��View.
	 * 
	 * @param context
	 *            the context
	 */
	private void initView(Context context) {

		mContext = context;

		// ����ˢ������������
		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.HORIZONTAL);
		headerView.setGravity(Gravity.CENTER);

		PullToRefreshView.setPadding(headerView, 0, 10, 0, 10);

		// ��ʾ��ͷ����
		FrameLayout headImage = new FrameLayout(context);
		arrowImageView = new ImageView(context);
		// �Ӱ����ȡ�ļ�ͷͼƬ
		arrowImage = PullToRefreshView.getBitmapFromSrc("image/arrow.png");
		arrowImageView.setImageBitmap(arrowImage);
		// style="?android:attr/progressBarStyleSmall" Ĭ�ϵ���ʽ
		headerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyle);
		headerProgressBar.setVisibility(View.GONE);

		LayoutParams layoutParamsWW = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.height = PullToRefreshView.scaleValue(mContext, 50);
		headImage.addView(arrowImageView, layoutParamsWW);
		headImage.addView(headerProgressBar, layoutParamsWW);

		// ����ˢ�����ı�����
		LinearLayout headTextLayout = new LinearLayout(context);
		tipsTextview = new TextView(context);
		headerTimeView = new TextView(context);
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		PullToRefreshView.setPadding(headTextLayout, 0, 0, 0, 0);
		LayoutParams layoutParamsWW2 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		headTextLayout.addView(tipsTextview, layoutParamsWW2);
		headTextLayout.addView(headerTimeView, layoutParamsWW2);
		tipsTextview.setTextColor(Color.rgb(107, 107, 107));
		headerTimeView.setTextColor(Color.rgb(107, 107, 107));
		PullToRefreshView.setTextSize(tipsTextview, 30);
		PullToRefreshView.setTextSize(headerTimeView, 27);

		LayoutParams layoutParamsWW3 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.rightMargin = PullToRefreshView
				.scaleValue(mContext, 10);

		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER);

		headerLayout.addView(headImage, layoutParamsWW3);
		headerLayout.addView(headTextLayout, layoutParamsWW3);

		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		// ��Ӵ󲼾�
		headerView.addView(headerLayout, lp);
		this.addView(headerView, lp);
		// ��ȡView�ĸ߶�
		PullToRefreshView.measureView(this);
		headerHeight = this.getMeasuredHeight();

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
		setState(STATE_NORMAL);
	}

	/**
	 * ����״̬.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {
		if (state == mState)
			return;
		if (state == STATE_REFRESHING) {
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.VISIBLE);
		} else {
			arrowImageView.setVisibility(View.VISIBLE);
			headerProgressBar.setVisibility(View.INVISIBLE);
		}
		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				arrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				arrowImageView.clearAnimation();
			}
			tipsTextview.setText("����ˢ��");

			if (lastRefreshTime == null) {
				lastRefreshTime = PullToRefreshView
						.getCurrentDate(PullToRefreshView.dateFormatHMS);
				headerTimeView.setText("ˢ��ʱ�䣺" + lastRefreshTime);
			} else {
				headerTimeView.setText("�ϴ�ˢ��ʱ�䣺" + lastRefreshTime);
			}
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(mRotateUpAnim);
				tipsTextview.setText("�ɿ�ˢ��");
				headerTimeView.setText("�ϴ�ˢ��ʱ�䣺" + lastRefreshTime);
				lastRefreshTime = PullToRefreshView
						.getCurrentDate(PullToRefreshView.dateFormatHMS);
			}
			break;
		case STATE_REFRESHING:
			tipsTextview.setText("����ˢ��...");
			headerTimeView.setText("����ˢ��ʱ�䣺" + lastRefreshTime);
			break;
		default:
		}

		mState = state;
	}

	/**
	 * ����header�ɼ�ĸ߶�.
	 * 
	 * @param height
	 *            the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) headerView
				.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * ��ȡheader�ɼ�ĸ߶�.
	 * 
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams) headerView
				.getLayoutParams();
		return lp.height;
	}

	/**
	 * ��������ȡHeaderView.
	 * 
	 * @return the header view
	 */
	public LinearLayout getHeaderView() {
		return headerView;
	}

	/**
	 * ������һ��ˢ��ʱ��.
	 * 
	 * @param time
	 *            ʱ���ַ�
	 */
	public void setRefreshTime(String time) {
		headerTimeView.setText(time);
	}

	/**
	 * ��ȡheader�ĸ߶�.
	 * 
	 * @return �߶�
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}

	/**
	 * ����������������ɫ.
	 * 
	 * @param color
	 *            the new text color
	 */
	public void setTextColor(int color) {
		tipsTextview.setTextColor(color);
		headerTimeView.setTextColor(color);
	}

	/**
	 * ���������ñ�����ɫ.
	 * 
	 * @param color
	 *            the new background color
	 */
	public void setBackgroundColor(int color) {
		headerView.setBackgroundColor(color);
	}

	/**
	 * ��������ȡHeader ProgressBar�����������Զ�����ʽ.
	 * 
	 * @return the header progress bar
	 */
	public ProgressBar getHeaderProgressBar() {
		return headerProgressBar;
	}

	/**
	 * ����������Header ProgressBar��ʽ.
	 * 
	 * @param indeterminateDrawable
	 *            the new header progress bar drawable
	 */
	public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
		headerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * �������õ���ǰ״̬.
	 * 
	 * @return the state
	 */
	public int getState() {
		return mState;
	}

	/**
	 * ������ʾ״̬���ֵĴ�С.
	 * 
	 * @param size
	 *            the new state text size
	 */
	public void setStateTextSize(int size) {
		tipsTextview.setTextSize(size);
	}

	/**
	 * ������ʾʱ�����ֵĴ�С.
	 * 
	 * @param size
	 *            the new time text size
	 */
	public void setTimeTextSize(int size) {
		headerTimeView.setTextSize(size);
	}

	/**
	 * Gets the arrow image view.
	 * 
	 * @return the arrow image view
	 */
	public ImageView getArrowImageView() {
		return arrowImageView;
	}

	/**
	 * ���������ö���ˢ��ͼ��.
	 * 
	 * @param resId
	 *            the new arrow image
	 */
	public void setArrowImage(int resId) {
		this.arrowImageView.setImageResource(resId);
	}

}

class ListViewFooter extends LinearLayout {

	/** The m context. */
	private Context mContext;

	/** The m state. */
	private int mState = -1;

	/** The Constant STATE_READY. */
	public final static int STATE_READY = 1;

	/** The Constant STATE_LOADING. */
	public final static int STATE_LOADING = 2;

	/** The Constant STATE_NO. */
	public final static int STATE_NO = 3;

	/** The Constant STATE_EMPTY. */
	public final static int STATE_EMPTY = 4;

	/** The footer view. */
	private LinearLayout footerView;

	/** The footer progress bar. */
	private ProgressBar footerProgressBar;

	/** The footer text view. */
	private TextView footerTextView;

	/** The footer content height. */
	private int footerHeight;

	/**
	 * Instantiates a new ab list view footer.
	 * 
	 * @param context
	 *            the context
	 */
	public ListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * Instantiates a new ab list view footer.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		setState(STATE_READY);
	}

	/**
	 * Inits the view.
	 * 
	 * @param context
	 *            the context
	 */
	private void initView(Context context) {
		mContext = context;

		// �ײ�ˢ��
		footerView = new LinearLayout(context);
		// ���ò��� ˮƽ����
		footerView.setOrientation(LinearLayout.HORIZONTAL);
		footerView.setGravity(Gravity.CENTER);
		footerView
				.setMinimumHeight(PullToRefreshView.scaleValue(mContext, 100));
		footerTextView = new TextView(context);
		footerTextView.setGravity(Gravity.CENTER_VERTICAL);
		setTextColor(Color.rgb(107, 107, 107));
		PullToRefreshView.setTextSize(footerTextView, 30);

		PullToRefreshView.setPadding(footerView, 0, 10, 0, 10);

		footerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyle);
		footerProgressBar.setVisibility(View.GONE);

		LayoutParams layoutParamsWW = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.height = PullToRefreshView.scaleValue(mContext, 50);
		layoutParamsWW.rightMargin = PullToRefreshView.scaleValue(mContext, 10);
		footerView.addView(footerProgressBar, layoutParamsWW);

		LayoutParams layoutParamsWW1 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		footerView.addView(footerTextView, layoutParamsWW1);

		LayoutParams layoutParamsFW = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(footerView, layoutParamsFW);

		// ��ȡView�ĸ߶�
		PullToRefreshView.measureView(this);
		footerHeight = this.getMeasuredHeight();
	}

	/**
	 * ���õ�ǰ״̬.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {

		if (state == STATE_READY) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("������");
		} else if (state == STATE_LOADING) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.VISIBLE);
			footerTextView.setText("���ڼ���...");
		} else if (state == STATE_NO) {
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("û���ˣ�");
		} else if (state == STATE_EMPTY) {
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.GONE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("û�����");
		}
		mState = state;
	}

	/**
	 * Gets the visiable height.
	 * 
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		return lp.height;
	}

	/**
	 * ����footerView.
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = 0;
		footerView.setLayoutParams(lp);
		footerView.setVisibility(View.GONE);
	}

	/**
	 * ��ʾfooterView.
	 */
	public void show() {
		footerView.setVisibility(View.VISIBLE);
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		footerView.setLayoutParams(lp);
	}

	/**
	 * ����������������ɫ.
	 * 
	 * @param color
	 *            the new text color
	 */
	public void setTextColor(int color) {
		footerTextView.setTextColor(color);
	}

	/**
	 * ���������������С.
	 * 
	 * @param size
	 *            the new text size
	 */
	public void setTextSize(int size) {
		footerTextView.setTextSize(size);
	}

	/**
	 * ���������ñ�����ɫ.
	 * 
	 * @param color
	 *            the new background color
	 */
	public void setBackgroundColor(int color) {
		footerView.setBackgroundColor(color);
	}

	/**
	 * ��������ȡFooter ProgressBar�����������Զ�����ʽ.
	 * 
	 * @return the footer progress bar
	 */
	public ProgressBar getFooterProgressBar() {
		return footerProgressBar;
	}

	/**
	 * ����������Footer ProgressBar��ʽ.
	 * 
	 * @param indeterminateDrawable
	 *            the new footer progress bar drawable
	 */
	public void setFooterProgressBarDrawable(Drawable indeterminateDrawable) {
		footerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * ��������ȡ�߶�.
	 * 
	 * @return the footer height
	 */
	public int getFooterHeight() {
		return footerHeight;
	}

	/**
	 * ���ø߶�.
	 * 
	 * @param height
	 *            �µĸ߶�
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) footerView
				.getLayoutParams();
		lp.height = height;
		footerView.setLayoutParams(lp);
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public int getState() {
		return mState;
	}

}
