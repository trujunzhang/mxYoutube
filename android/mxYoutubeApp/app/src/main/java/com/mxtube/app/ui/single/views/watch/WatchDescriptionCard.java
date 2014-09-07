package com.mxtube.app.ui.single.views.watch;

import android.content.Context;
import android.widget.LinearLayout;

import com.common.utils.StringUtils;
import com.google.api.services.youtube.model.Video;
import com.mxtube.app.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.math.BigInteger;

@EViewGroup(R.layout.watch_description_card)
public class WatchDescriptionCard extends LinearLayout {

	@ViewById(R.id.description_title)
	android.widget.TextView descriptionTitle;
	@ViewById(R.id.separator)
	android.widget.ImageView separator;
	@ViewById(R.id.like_button)
	android.widget.TextView likeButton;
	@ViewById(R.id.viewCount_label)
	android.widget.TextView viewCountLabel;
	@ViewById(R.id.description)
	android.widget.TextView description;

	public WatchDescriptionCard(Context context) {
		super(context);
	}

	public void bind(Context context, Video singleVideo) {
		java.math.BigInteger _likeCount = singleVideo.getStatistics().getLikeCount();
		java.math.BigInteger _disLikeCount = singleVideo.getStatistics().getDislikeCount();
		BigInteger _viewCount = singleVideo.getStatistics().getViewCount();

		String _likeAndDislike = String.format("%s likes, %s dislikes", _likeCount, _disLikeCount);

		String _title = singleVideo.getSnippet().getTitle();
		String _description = singleVideo.getSnippet().getDescription();

		// ...... line01
		this.descriptionTitle.setText(_title);

		// ...... line02
		this.likeButton.setText(_likeAndDislike);
		this.viewCountLabel.setText(String.valueOf(_viewCount));

		// ...... line03
		this.description.setText(_description);
	}

}
