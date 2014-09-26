package de.greenrobot.daogenerator.gentest.bean;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 聊天(chat)数据库表： 来源用户 聊天来源时间 目标用户 聊天发送时间 聊天内容（msg） 内容来源状态(stat)： 发送|接受 发送状态(stat):成功|失败 接受状态(stat):未知
 */
public class DBChatMessage {

	public static void addInfo(Schema schema) {
		Entity note = schema.addEntity("DBChatMessage");
		note.addIdProperty();

		note.addStringProperty("restId");// 此行服务器数据库里的唯一标示.唯一id

		note.addStringProperty("memberId");
		note.addStringProperty("friendId");

		note.addStringProperty("message");

		note.addStringProperty("sendTime");
		note.addBooleanProperty("status");

		note.addBooleanProperty("upload");// 是否上传到服务器

	}
}
