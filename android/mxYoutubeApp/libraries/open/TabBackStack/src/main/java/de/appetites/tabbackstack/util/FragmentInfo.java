package de.appetites.tabbackstack.util;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appetites.de on 24.03.2014. Helper class to save and restore a fragment.
 */
public class FragmentInfo implements Parcelable {
	public static final Creator<FragmentInfo> CREATOR = new Creator<FragmentInfo>() {
		public FragmentInfo createFromParcel(Parcel in) {
			return new FragmentInfo(in);
		}

		public FragmentInfo[] newArray(int size) {
			return new FragmentInfo[size];
		}
	};
	public String className;
	public Bundle arguments;

	public FragmentInfo(Parcel in) {
		className = in.readString();
		arguments = in.readBundle();
	}

	public FragmentInfo() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(className);
		out.writeBundle(arguments);
	}
}
