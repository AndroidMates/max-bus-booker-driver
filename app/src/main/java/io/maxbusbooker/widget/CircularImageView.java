/*
 * Copyright (c) 2018. Property of Dennis Kwabena Bilson. No unauthorized duplication of this material should be made without prior permission from the developer
 */

package io.maxbusbooker.widget;

import android.content.Context;
import android.util.AttributeSet;

import io.maxbusbooker.R;
import io.maxbusbooker.util.ViewUtils;


/**
 * An extension to image view that has a circular outline.
 */
public class CircularImageView extends ForegroundImageView {
	
	public CircularImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOutlineProvider(ViewUtils.CIRCULAR_OUTLINE);
		setClipToOutline(true);
	}
}
