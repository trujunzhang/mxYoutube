package de.appetites.tabbackstack.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by appetites.de on 22.03.2014. Class to manage, save and retrieve a tabs back stack.
 */
public class FragmentBackStack implements Parcelable {
	public static final Creator<FragmentBackStack> CREATOR = new Creator<FragmentBackStack>() {
		public FragmentBackStack createFromParcel(Parcel in) {
			return new FragmentBackStack(in);
		}

		public FragmentBackStack[] newArray(int size) {
			return new FragmentBackStack[size];
		}
	};

	// The stack with a reference to the Fragments, so they don't get garbage collected.
	Stack<Fragment> mFragments;
	// Helper list to save and restore a back stack.
	ArrayList<FragmentInfo> fragmentInfos;

	public FragmentBackStack() {
		mFragments = new Stack<Fragment>();
	}

	private FragmentBackStack(Parcel in) {
		mFragments = new Stack<Fragment>();
		fragmentInfos = new ArrayList<FragmentInfo>();
		in.readTypedList(fragmentInfos, FragmentInfo.CREATOR);
	}

	/**
	 * Returns the size of the back stack.
	 *
	 * @return The size.
	 */
	public int size() {
		return mFragments.size();
	}

	/**
	 * Pushes a fragment to the back stack.
	 *
	 * @param fragment
	 *            The fragment to push.
	 * @param fragmentTransaction
	 *            The transaction to use for the replacement.
	 * @param containerId
	 *            The id of the container into which the fragment shall be replaced.
	 */
	public void push(Fragment fragment, FragmentTransaction fragmentTransaction, int containerId) {
		fragmentTransaction.replace(containerId, fragment);
		if (mFragments.size() == 0 || mFragments.size() > 0 && fragment != mFragments.peek()) {
			if (mFragments.size() > 1) {// only one child fragment.
				mFragments.pop();
			}
			mFragments.push(fragment);
		}
	}

	/**
	 * Pops a fragment from the back stack.
	 *
	 * @return The popped fragment.
	 */
	public Fragment pop() {
		return mFragments.pop();
	}

	/**
	 * Pops all fragments till the give position
	 * 
	 * @param position
	 *            The position of the last fragment that shall not be popped
	 * @return The new last fragment of the stack
	 */
	public Fragment popToPosition(int position) {
		if (position > 0 && position < mFragments.size()) {
			mFragments.setSize(position);
		}
		return mFragments.peek();
	}

	/**
	 * Pops the backstack to the root fragment
	 *
	 * @return The root fragment
	 */
	public Fragment popToRoot() {
		mFragments.setSize(1);
		return mFragments.peek();
	}

	/**
	 * Get the last fragment on the back stack.
	 *
	 * @return The last fragment.
	 */
	public Fragment getCurrent() {
		return mFragments.peek();
	}

	/**
	 * Recreates the fragment back stack using the restored fragmentInfo list.
	 *
	 * @param context
	 *            The context needed to instantiate the fragments.
	 */
	public void recreateBackStack(Context context) {
		if (mFragments.size() == 0 && fragmentInfos != null) {
			Log.d("xxx", String.format("fragments = %d, fragmentInfos = %d", mFragments.size(), fragmentInfos.size()));
			for (FragmentInfo fragmentInfo : fragmentInfos) {
				Fragment fragment = Fragment.instantiate(context, fragmentInfo.className);
				fragment.setArguments(fragmentInfo.arguments);
				mFragments.push(fragment);
			}
			fragmentInfos.clear();
			fragmentInfos = null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		ArrayList<FragmentInfo> fragmentInfos = new ArrayList<FragmentInfo>();
		for (Fragment fragment : mFragments) {
			FragmentInfo fragmentInfo = new FragmentInfo();
			fragmentInfo.className = fragment.getClass().getName();
			fragmentInfo.arguments = fragment.getArguments();
			fragmentInfos.add(fragmentInfo);
		}
		out.writeTypedList(fragmentInfos);
	}
}
