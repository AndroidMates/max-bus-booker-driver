package io.maxbusbooker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.maxbusbooker.ui.BaseActivity;

/**
 * Base class for all {@linkplain Fragment}s
 */
public abstract class BaseFragment extends Fragment {
	
	public BaseFragment() {
		// Required empty public constructor
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getInflatedView(), container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		onViewReady(savedInstanceState, (BaseActivity) getActivity(), view);
	}
	
	protected abstract int getInflatedView();
	
	protected abstract void onViewReady(Bundle instanceState, BaseActivity host, View view);
}
