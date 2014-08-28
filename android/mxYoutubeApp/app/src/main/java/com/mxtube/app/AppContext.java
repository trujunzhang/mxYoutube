package com.mxtube.app;

import android.app.Application;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

@EApplication
public class AppContext extends Application {

	public static AppContext instance;

	public static Index index;

	@AfterInject
	void calledAfterInjection() {
		instance = this;
	}

}
