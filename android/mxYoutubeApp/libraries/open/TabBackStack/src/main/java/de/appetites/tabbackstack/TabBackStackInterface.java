package de.appetites.tabbackstack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public interface TabBackStackInterface {
	/**
	 * Returns the id of the container used for replacing the tab bars fragments. NEEDS TO BE IMPLEMENTED AND RETURN A
	 * VALID CONTAINER ID
	 *
	 * @return id of the container
	 */
	public int getContainerId();

	public Fragment initTab(int position);

    public FragmentTransaction getFragmentTransaction(boolean hasPush);

}
