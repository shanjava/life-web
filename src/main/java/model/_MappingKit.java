package model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("article", "id", Article.class);
		arp.addMapping("dictionary", "id", Dictionary.class);
		arp.addMapping("friend_link", "id", FriendLink.class);
		arp.addMapping("link_dictionary", "id", LinkDictionary.class);
		arp.addMapping("sys_user", "id", SysUser.class);
	}
}

