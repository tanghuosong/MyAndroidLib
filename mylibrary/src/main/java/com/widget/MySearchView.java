package com.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.iftech.mylibrary.R;
import com.utils.MyUtils;

/**
 * created by TangHuoSong 2017/5/27
 * description: 自定义搜索框
 * how to use:
 *     在需要使用tabBar 的布局文件XML中，加入
 *       <com.widget.MySearchView
 *            android:id="@+id/my_search"
 *            android:layout_width="match_parent"
 *            android:layout_height="40dp"/>
 *
 *       即可正常使用，在Activity 中监听 OnSearchListener 即可。
 **/
public class MySearchView extends RelativeLayout{

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {

        this.onSearchListener = onSearchListener;
    }

    public MySearchView(final Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        /* 设置搜索框背景 */
        int strokeWidth = MyUtils.dip2px(context,1); // 1dp 边框宽度
        int roundRadius = MyUtils.dip2px(context,8); // 8dp 圆角半径
        int strokeColor = Color.parseColor("#cccccc"); //边框颜色
        int fillColor = Color.parseColor("#ffffff"); //内部填充颜色
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        // 加载根布局
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setBackground(gd);
        addView(relativeLayout);

        /* 加载搜索框 */
        LinearLayout linearLayoutSearch = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayoutSearch.setLayoutParams(layoutParams1);
        linearLayoutSearch.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutSearch.setFocusable(true);
        linearLayoutSearch.setFocusableInTouchMode(true);
        relativeLayout.addView(linearLayoutSearch);

        LinearLayout.LayoutParams etLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        etLlp.weight = 1;
        etLlp.gravity = CENTER_IN_PARENT;
        final EditText searchET = new EditText(context);
        searchET.setBackground(null);
        searchET.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchET.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        searchET.setSingleLine(true);
        searchET.setTextSize(14);
        searchET.setGravity(Gravity.CENTER_VERTICAL);
        searchET.setPadding(MyUtils.dip2px(context,50),0,0,0);
        searchET.setLayoutParams(etLlp);
        linearLayoutSearch.addView(searchET);

        LinearLayout.LayoutParams cancelLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final TextView cancelTV =new TextView(context);
        cancelTV.setLayoutParams(cancelLlp);
        cancelTV.setText("取消");
        cancelTV.setTextColor(Color.BLACK);
        cancelTV.setVisibility(GONE);
        cancelTV.setPadding(0,0,MyUtils.dip2px(context,20),0);
        cancelTV.setGravity(Gravity.CENTER_VERTICAL);
        linearLayoutSearch.addView(cancelTV);

        /* 加载背景 */
        final LinearLayout bgLl = new LinearLayout(context);
        bgLl.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams bgLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bgLlp.gravity = Gravity.CENTER;
        bgLl.setLayoutParams(bgLlp);

        /* 搜索图标 */
        LinearLayout.LayoutParams imgLay = new LinearLayout.LayoutParams(MyUtils.dip2px(context,24),MyUtils.dip2px(context,24));
        imgLay.gravity = Gravity.CENTER;
        final ImageView searchIcon = new ImageView(context);
        searchIcon.setLayoutParams(imgLay);
        searchIcon.setImageResource(R.drawable.icon_search);
        bgLl.addView(searchIcon);

        /* 所搜框的提示文字 */
        final TextView searchHint = new TextView(context);
        searchHint.setText("搜索");
        searchHint.setTextSize(14);
        searchHint.setTextColor(Color.GRAY);
        searchHint.setGravity(Gravity.CENTER);
        bgLl.addView(searchHint);
        relativeLayout.addView(bgLl);

        /* 焦点事件更新UI */
        searchET.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cancelTV.setVisibility(VISIBLE);
                    searchHint.setVisibility(GONE);
                    bgLl.setGravity(Gravity.START);
                    bgLl.setPadding(MyUtils.dip2px(context,10),0,0,0);
                }else{
                    cancelTV.setVisibility(GONE);
                    searchHint.setVisibility(VISIBLE);
                    bgLl.setGravity(Gravity.CENTER);
                    searchET.setText("");
                }
            }
        });

        /* 取消搜索按钮 */
        cancelTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchET.clearFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();
                if (isOpen) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        /* 监听键盘“搜索”按钮的点击事件 */
        searchET.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(onSearchListener != null){
                        onSearchListener.searchClick(searchET.getText().toString());
                    }
                }
                return true;
            }
        });
    }


    /* 对外开放接口 */
    public interface OnSearchListener{
        void searchClick(String  searchStr);
    }
}
