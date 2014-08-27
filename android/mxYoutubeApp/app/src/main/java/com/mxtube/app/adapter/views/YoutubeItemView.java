package com.mxtube.app.adapter.views;


import android.content.Context;
import android.widget.LinearLayout;
import com.mxtube.app.R;
import org.androidannotations.annotations.EViewGroup;

@EViewGroup(R.layout.youtube_item)
public class YoutubeItemView extends LinearLayout {

//    @ViewById
//    TextView firstNameView;
//
//    @ViewById
//    TextView lastNameView;

    public YoutubeItemView(Context context) {
        super(context);
    }

	// public void bind(Person person) {
	// firstNameView.setText(person.firstName);
	// lastNameView.setText(person.lastName);
	// }
}