package de.greenrobot.daogenerator.gentest.bean;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 聊天(chat)数据库表： 来源用户 聊天来源时间 目标用户 聊天发送时间 聊天内容（msg） 内容来源状态(stat)： 发送|接受 发送状态(stat):成功|失败 接受状态(stat):未知
 */
public class DBLoginInfo {

	public static void addInfo(Schema schema) {
		Entity note = schema.addEntity("DBLoginInfo");
		note.addIdProperty();

		note.addStringProperty("restId");

		note.addStringProperty("nickName");
		note.addStringProperty("name");
		note.addStringProperty("age");
		note.addStringProperty("sex");
		note.addStringProperty("birth");
		note.addStringProperty("industry");
		note.addStringProperty("attentionTopical");
		note.addStringProperty("attentionService");
		note.addStringProperty("company");
		note.addStringProperty("telephone");
		note.addStringProperty("createdTime");
		note.addStringProperty("email");
		note.addStringProperty("userName");
		note.addStringProperty("password");
		note.addStringProperty("fax");

		note.addStringProperty("headImage");// 用户头像-文件名

		note.addBooleanProperty("isRememberUserName");// 是否记住我的信息
		note.addBooleanProperty("isRememberPassword");// 是否记住我的信息
	}
}
