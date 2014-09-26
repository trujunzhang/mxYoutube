package de.greenrobot.daogenerator.gentest.bean;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 聊天用户(histroyConversation)数据库表： 用户id 最后一次聊天时间
 */
public class DBConversationUser {

	public static void addInfo(Schema schema) {
		Entity note = schema.addEntity("DBConversationUser");
		note.addIdProperty();

		note.addStringProperty("memberId");// 来源用户
		note.addStringProperty("friendId");// 目标用户

		note.addStringProperty("talkMessage");
	}
}
