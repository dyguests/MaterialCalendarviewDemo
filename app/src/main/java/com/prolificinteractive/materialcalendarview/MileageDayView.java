package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.util.List;

/**
 * 带里程的 DayView
 * <p>
 * Created by fanhl on 2017/1/24.
 */
@SuppressLint("ViewConstructor")
public class MileageDayView extends DayView {
    public MileageDayView(Context context, CalendarDay day, AttributeSet attrs) {
        super(context, day, attrs);
    }

    @Override public void setDayFormatter(DayFormatter formatter) {
        super.setDayFormatter(formatter);
        CharSequence currentLabel = this.getText();
        Object[] spans = null;
        if (currentLabel instanceof Spanned) {
            spans = ((Spanned) currentLabel).getSpans(0, currentLabel.length(), Object.class);
        }

        String labelStr = this.getLabel();
        int wrapIndex = labelStr.indexOf('\n');
        SpannableString newLabel = new SpannableString(labelStr);
        if (spans != null) {
            for (Object span : spans) {
                if (span instanceof RelativeSizeSpan) {
                    newLabel.setSpan(span, 0, wrapIndex > 0 ? wrapIndex : newLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    newLabel.setSpan(span, 0, newLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        this.setText(newLabel);
    }

    @Override void applyFacade(DayViewFacade facade) {
        super.applyFacade(facade);
        List spans = facade.getSpans();
        if(!spans.isEmpty()) {
            String label = this.getLabel();
            int wrapIndex = label.indexOf('\n');

            SpannableString formattedLabel = new SpannableString(this.getLabel());

            for (Object span1 : spans) {
                Object span = ((DayViewFacade.Span) span1).span;

                if (span instanceof RelativeSizeSpan) {
                    formattedLabel.setSpan(span, 0, wrapIndex > 0 ? wrapIndex : label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    formattedLabel.setSpan(span, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            this.setText(formattedLabel);
        } else {
            this.setText(this.getLabel());
        }
    }
}
