package com.tianxia.lib.baseworld.main;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.tianxia.lib.baseworld.BaseApplication;
import com.tianxia.lib.baseworld.R;

public class MainTabFrame extends ActivityGroup {

    // Tab Activity Layout
    private LocalActivityManager localActivityManager = null;
    private LinearLayout mainTab = null;
    private LinearLayout mainTabContainer = null;
    public static int mainTabContainerHeight = 0;
    private Intent mainTabIntent = null;

    String[] tabTexts;
    int tabSize;

    // Tab banner title
    private LinearLayout mainTabBanner = null;
    private TextView mainTabTitleTextView = null;

    // Tab ImageView
    private List<ImageView> tabImageViews = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_frame);

        mainTab = (LinearLayout) findViewById(R.id.main_tab);
        mainTabBanner = (LinearLayout) findViewById(R.id.main_tab_banner);

        mainTabContainer = (LinearLayout) findViewById(R.id.main_tab_container);
        mainTabContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (mainTabContainerHeight == 0) {
                    mainTabContainerHeight = mainTabContainer.getHeight() - mainTabBanner.getHeight();
                }
            }
        });

        initTab();
    }

    /**
     * init the tab
     * */
    private void initTab() {
        mainTabTitleTextView = (TextView) findViewById(R.id.main_tab_banner_title);
        mainTab.removeAllViews();
        tabImageViews = new ArrayList<ImageView>();

        ImageView tabImageView;
        ImageView splitImageView;
        tabTexts = getResources().getStringArray(R.array.tab_text);
        LinearLayout.LayoutParams tabLp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
        LinearLayout.LayoutParams splitLp = new LayoutParams(5, LayoutParams.FILL_PARENT);

        tabSize = ((BaseApplication) getApplication()).getTabActivitys().size();
        for (int i = 0; i < tabSize; i++) {
            tabImageView = new ImageView(this);
            tabImageView.setTag(i);
            tabImageView.setLayoutParams(tabLp);
            tabImageView.setImageResource(((BaseApplication) getApplication()).getTabNormalImages().get(i));
            tabImageView.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    int tabIndex = (Integer) (v.getTag());
                    mainTabTitleTextView.setText(tabTexts[tabIndex]);
                    setContainerView("tab" + tabIndex,
                            ((BaseApplication) getApplication()).getTabActivitys().get(tabIndex));
                    for (int j = 0; j < tabSize; j++) {
                        tabImageViews.get(j).setImageResource(
                                ((BaseApplication) getApplication()).getTabNormalImages().get(j));
                        tabImageViews.get(j).setBackgroundResource(R.drawable.tab_item_clear);
                    }
                    tabImageViews.get(tabIndex).setImageResource(
                            ((BaseApplication) getApplication()).getTabPressImages().get(tabIndex));
                    tabImageViews.get(tabIndex).setBackgroundResource(R.drawable.tab_item_front);
                }

            });
            tabImageViews.add(tabImageView);
            mainTab.addView(tabImageView);

            if (i < tabSize - 1) {
                splitImageView = new ImageView(this);
                splitImageView.setLayoutParams(splitLp);
                splitImageView.setImageResource(R.drawable.tab_split);
                mainTab.addView(splitImageView);
            }
        }

        // what's the current focus text and image when first show
        mainTabTitleTextView.setText(tabTexts[0]);
        tabImageViews.get(0).setImageResource(((BaseApplication) getApplication()).getTabPressImages().get(0));
        tabImageViews.get(0).setBackgroundResource(R.drawable.tab_item_front);
        localActivityManager = getLocalActivityManager();
        setContainerView("tab0", ((BaseApplication) getApplication()).getTabActivitys().get(0));
    }

    public void setContainerView(String id, Class<?> activity) {
        mainTabContainer.removeAllViews();
        mainTabIntent = new Intent(this, activity);
        View view = localActivityManager.startActivity(id, mainTabIntent).getDecorView();
        // make sure the subactivity will fill parent layout, it is a important
        // tips
        view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        mainTabContainer.addView(localActivityManager.startActivity(id, mainTabIntent).getDecorView());
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            localActivityManager.getCurrentActivity().openOptionsMenu();
            // return true means that the key event is intercepted here
            // successfully
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            localActivityManager.getCurrentActivity().onBackPressed();
            // return false means that it will skip over the key event here
            return true;
        }
        return false;
    }
}
