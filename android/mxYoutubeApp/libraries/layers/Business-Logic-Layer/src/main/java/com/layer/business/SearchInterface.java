package com.layer.business;

import android.content.Context;
import com.google.api.services.youtube.model.Video;

import java.util.List;


public interface SearchInterface {

	List<Video> search(Context context);
}
