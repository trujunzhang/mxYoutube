package de.greenrobot.daogenerator.gentest.bean;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 俱乐部基础数据(ClubBasedData)数据库表: uid (类型,名称,删除标识)
 */
public final class DBClubBasedData {

	public static void addInfo(Schema schema) {
		Entity note = schema.addEntity("DBClubBasedData");
		note.addIdProperty();

		note.addStringProperty("restId");// 此行服务器数据库里的唯一标示.唯一id

		note.addIntProperty("type");// 类型
		note.addStringProperty("name");// 名称
	}
}
