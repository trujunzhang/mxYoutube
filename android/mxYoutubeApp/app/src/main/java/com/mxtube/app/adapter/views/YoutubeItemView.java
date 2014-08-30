package com.mxtube.app.adapter.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.mxtube.app.R;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.math.BigInteger;

@EViewGroup(R.layout.youtube_item)
public class YoutubeItemView extends LinearLayout {
	@ViewById(R.id.thumbnails)
	android.widget.ImageView thumbnails;
	@ViewById(R.id.duration)
	android.widget.TextView duration;
	@ViewById(R.id.title)
	android.widget.TextView title;
	@ViewById(R.id.rating)
	android.widget.TextView rating;
	@ViewById(R.id.viewCount)
	android.widget.TextView viewCount;

	public YoutubeItemView(Context context) {
		super(context);
	}

	/**
	 *
	 * "contentDetails": { "duration": "PT5M11S", "dimension": "2d", "definition": "sd", "caption": "false",
	 * "licensedContent": true },
	 */

	public void bind(Context context, Video singleVideo) {
		// Confirm that the result represents a video. Otherwise, the
		// item will not contain a video ID.
		Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getHigh();

		System.out.println(" Video Id" + singleVideo.getId());
		System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
		System.out.println(" Thumbnail: " + thumbnail.getUrl());
		System.out.println(" width: " + thumbnail.getWidth() + " height: " + thumbnail.getHeight());
		System.out.println("\n-------------------------------------------------------------\n");

		String _title = singleVideo.getSnippet().getTitle();
		String _duration = singleVideo.getContentDetails().getDuration();

		BigInteger _viewCount = singleVideo.getStatistics().getViewCount();

		BigInteger likeCount = singleVideo.getStatistics().getLikeCount();
		BigInteger dislikeCount = singleVideo.getStatistics().getDislikeCount();
		BigInteger count = likeCount.add(dislikeCount);
		BigDecimal bigDecX = new BigDecimal(likeCount);
		BigDecimal bigDecY = new BigDecimal(count);
		// to divide:
//		BigDecimal _rating = bigDecX.divide(bigDecY);

		Picasso.with(context).load(thumbnail.getUrl()).into(this.thumbnails);

		this.title.setText(_title);
		this.duration.setText(_duration.replace("PT", "").replace("M", ":").replace("S", ""));
//		this.rating.setText(String.valueOf(_rating));
		this.viewCount.setText(String.valueOf(_viewCount));
	}
}