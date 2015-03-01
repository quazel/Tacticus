package com.bramble.kickback.util;

import android.view.View;
import android.view.ViewGroup;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class Util {

    public static String formatPhoneNumber(String phoneNumber) {
        int lastIndex = phoneNumber.length() - 1;
        return "+"+ phoneNumber.substring(0, lastIndex - 9) + " (" +
               phoneNumber.substring(lastIndex - 9, lastIndex - 6) + ") " +
               phoneNumber.substring(lastIndex - 6, lastIndex - 3) + "-" +
               phoneNumber.substring(lastIndex - 3);
    }

    public static void setListViewHeightBasedOnChildren(StickyListHeadersListView listView) {
        StickyListHeadersAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
