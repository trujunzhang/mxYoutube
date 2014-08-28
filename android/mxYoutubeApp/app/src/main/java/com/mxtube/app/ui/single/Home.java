package com.mxtube.app.ui.single;

import com.mxtube.app.R;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.single_home)
public class Home extends Single {

	@ViewById(R.id.gridView)
	android.widget.GridView gridView;

}
