package de.greenrobot.daogenerator.gentest.bean;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 收藏内容(collect)数据库表。
 */
public final class DBCollectInfo {

	public static void addInfo(Schema schema) {
		Entity note = schema.addEntity("DBCollectInfo");
		note.addIdProperty();

		note.addStringProperty("restId");// 此行服务器数据库里的唯一标示.唯一id

		note.addStringProperty("types"); // 类型.(区别不同模块).

		note.addStringProperty("title");// 标题.
		note.addStringProperty("abstracts");// 概要.
		note.addStringProperty("content");// 具体内容.

		note.addStringProperty("filepath");// 图片名称(在服务器图片目录下的文件名，和服务器数据库里的FILE_NAME对应)

		note.addStringProperty("sourceFrom");// 来源.
		note.addStringProperty("time");// 收藏时间.

		note.addStringProperty("telephone"); // 座机
		note.addStringProperty("mobile"); // 移动手机
		note.addStringProperty("webSite"); // 网址

	}
}
