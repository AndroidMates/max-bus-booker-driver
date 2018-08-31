package io.maxbusbooker.util;

import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

/**
 * Utility methods for working with HTML.
 */
public class HtmlUtils {
	
	private HtmlUtils() {
	}
	
	
	public static void setTextWithNiceLinks(TextView textView, CharSequence input) {
		textView.setText(input);
		textView.setMovementMethod(LinkTouchMovementMethod.getInstance());
		textView.setFocusable(false);
		textView.setClickable(false);
		textView.setLongClickable(false);
	}
	
	/*public static SpannableStringBuilder parseHtml(
			String input,
			ColorStateList linkTextColor,
			@ColorInt int linkHighlightColor) {
		SpannableStringBuilder spanned = fromHtml(input);
		
		// strip any trailing newlines
		while (spanned.charAt(spanned.length() - 1) == '\n') {
			spanned = spanned.delete(spanned.length() - 1, spanned.length());
		}
		
		return linkifyPlainLinks(spanned, linkTextColor, linkHighlightColor);
	}*/
	
	/*public static void parseAndSetText(TextView textView, String input) {
		if (TextUtils.isEmpty(input)) return;
		setTextWithNiceLinks(textView, parseHtml(input, textView.getLinkTextColors(),
				textView.getHighlightColor()));
	}*/
	
	/*private static SpannableStringBuilder linkifyPlainLinks(
			CharSequence input,
			ColorStateList linkTextColor,
			@ColorInt int linkHighlightColor) {
		final SpannableString plainLinks = new SpannableString(input); // copy of input
		
		// Linkify doesn't seem to work as expected on M+
		// TODO: figure out why
		//Linkify.addLinks(plainLinks, Linkify.WEB_URLS);
		
		final URLSpan[] urlSpans = plainLinks.getSpans(0, plainLinks.length(), URLSpan.class);
		
		// add any plain links to the output
		final SpannableStringBuilder ssb = new SpannableStringBuilder(input);
		for (URLSpan urlSpan : urlSpans) {
			ssb.removeSpan(urlSpan);
			ssb.setSpan(new TouchableUrlSpan(urlSpan.getURL(), linkTextColor, linkHighlightColor),
					plainLinks.getSpanStart(urlSpan),
					plainLinks.getSpanEnd(urlSpan),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		return ssb;
	}*/
	
	private static SpannableStringBuilder fromHtml(String input) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return (SpannableStringBuilder) Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return (SpannableStringBuilder) Html.fromHtml(input);
		}
	}
	
	
	/*public static CharSequence parseMarkdownAndPlainLinks(
			TextView textView,
			String input,
			Bypass markdown,
			Bypass.LoadImageCallback loadImageCallback) {
		CharSequence markedUp = markdown.markdownToSpannable(input, textView, loadImageCallback);
		return linkifyPlainLinks(
				markedUp, textView.getLinkTextColors(), textView.getHighlightColor());
	}*/
	
	/**
	 * Parse Markdown and plain-text links and set on the {@link TextView} with proper clickable
	 * spans.
	 */
	/*public static void parseMarkdownAndSetText(
			TextView textView,
			String input,
			Bypass markdown,
			Bypass.LoadImageCallback loadImageCallback) {
		if (TextUtils.isEmpty(input)) return;
		setTextWithNiceLinks(textView,
				parseMarkdownAndPlainLinks(textView, input, markdown, loadImageCallback));
	}*/
	
}

