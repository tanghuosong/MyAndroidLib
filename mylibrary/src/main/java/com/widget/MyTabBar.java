package com.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iftech.mylibrary.R;
import com.utils.MyUtils;

/***
 * created by TangHuoSong 2017/5/27
 * description: 自定义TabBar
 * how to use:
 *      在需要使用tabBar 的布局文件XML中，加入
 *      <com.ths.widget.MyTabBar
 *          android:id="@+id/tabBar"
 *          android:layout_width="match_parent"
 *          android:layout_height="60dp"/>
 *       即可正常使用，在Activity 中监听 OnItemMenuClick 即可。
 *       tabBar的item 可以单独设置image和text,请注意：image有两种状态（iconNormal非选中状态，iconSelect选中状态）
 *          并且image 和 text 的Id 数组长度必须相同。
 *       设置文字选中和非选中状态颜色只需要分别设置selectColor 和 defaultColor即可。
 ***/
public class MyTabBar extends RelativeLayout{

    /* TabBar 的显示的标题 */
    private int[] textMenu = {R.string.index,R.string.message,R.string.user};

    /* TabBar 正常情况下的图标 */
    private int[] iconNormal = { R.drawable.icon_tab_index_nomal, R.drawable.icon_tab_message_nomal, R.drawable.icon_tab_user_nomal };

    /* TabBar 选中状态下的图标 */
    private int[] iconSelect = { R.drawable.icon_tab_index_selected, R.drawable.icon_tab_message_selected, R.drawable.icon_tab_user_selected };

    /* 默认情况下的字体颜色 */
    private int defaultColor = R.color.colorPrimaryDark;

    /* 选中时的文字颜色 */
    private int selectColor = R.color.colorPrimary;

    /* 全局变量，记录当前选中的Item */
    private LinearLayout selectLineLayout;
    /* 全局变量，记录当前选中Item的Tag值 */
    private int selectTag = 0;

    private OnItemMenuClickListener onItemMenuClickListener;

    public void setOnItemMenuClick(OnItemMenuClickListener onItemMenuClickListener) {
        this.onItemMenuClickListener = onItemMenuClickListener;
    }

    public MyTabBar(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        LinearLayout linearLayoutRoot = new LinearLayout(context);
        linearLayoutRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayoutRoot.setBackgroundColor(Color.WHITE);
        addView(linearLayoutRoot);

        /* 根据text 数组长度实例化Item 个数*/
        for(int i=0 ; i <textMenu.length;i++) {

        /* 实例化子布局 */
            final LinearLayout linearLayoutChild = new LinearLayout(context);
            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
            linearLayoutChild.setGravity(Gravity.CENTER);
            linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
            linearLayoutChild.setWeightSum(1);
            linearLayoutChild.setTag(i);

        /* 实例化图标 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MyUtils.dip2px(context,26),MyUtils.dip2px(context,26));
            lp.setMargins(0, 5, 0, 0);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(iconNormal[i]);
            imageView.setTag("image_"+i);
            linearLayoutChild.addView(imageView);

        /* 实例化文字 */
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 0);
            TextView textView = new TextView(context);
            textView.setText(textMenu[i]);
            textView.setTextSize(14);
            textView.setTextColor(defaultColor);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(lp);
            textView.setTag("text_"+i);
            linearLayoutChild.addView(textView);

        /* 点击事件监听 */
            linearLayoutChild.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(v);
                    if(onItemMenuClickListener != null){
                        onItemMenuClickListener.onThisClick((int)v.getTag());
                    }
                }
            });

            /* 将该布局添加到根布局中 */
            linearLayoutRoot.addView(linearLayoutChild);
        }

        /* 设置默认选中项，即第一项 */
        selectLineLayout = (LinearLayout) (linearLayoutRoot.findViewWithTag(selectTag));

        /* 设置选中的图片 */
        ImageView imageView = (ImageView)(selectLineLayout.findViewWithTag("image_"+selectLineLayout.getTag()));
        imageView.setImageResource(iconSelect[(int)selectLineLayout.getTag()]);

        /* 设置选中的字体颜色 */
        TextView textView = (TextView)(selectLineLayout.findViewWithTag("text_"+selectLineLayout.getTag()));
        textView.setTextColor(Color.RED);

    }

    /***
     *  设置选中和未选中状态的颜色
     * */
    private void setSelect(View view){
        if(selectTag != (int) view.getTag()){

            /* 将上一次选中的图片颜色回复为未选中状态
               设置选中的图片 */
                ImageView imageView1 = (ImageView)(selectLineLayout.findViewWithTag("image_"+selectTag));
                imageView1.setImageResource(iconNormal[selectTag]);

            /* 设置选中的字体颜色 */
                TextView textView1 = (TextView)(selectLineLayout.findViewWithTag("text_"+selectTag));
                textView1.setTextColor(getContext().getResources().getColor(defaultColor));

            /* 重置选中的Tag */
                selectTag = (int) view.getTag();

            /* 设置选中的图片 */
                ImageView imageView = (ImageView)(view.findViewWithTag("image_"+view.getTag()));
                imageView.setImageResource(iconSelect[(int)view.getTag()]);

            /* 设置选中的字体颜色 */
                TextView textView = (TextView)(view.findViewWithTag("text_"+view.getTag()));
                textView.setTextColor(getContext().getResources().getColor(selectColor));

            /* 将选择的项存入全局变量，用于下一次点击更新控件*/
                selectLineLayout = (LinearLayout) view;
        }
    }

    public interface OnItemMenuClickListener{
        void onThisClick(int eachItem);
    }
}
