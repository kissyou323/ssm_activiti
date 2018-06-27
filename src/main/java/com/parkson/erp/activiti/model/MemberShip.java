package com.parkson.erp.activiti.model;

import com.parkson.erp.activiti.model.Group;
import com.parkson.erp.activiti.model.ActUser;

public class MemberShip {

	private ActUser user; // 用户
	private Group group; // 角色
	private String userId;
	private String groupId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ActUser getUser() {
		return user;
	}
	public void setUser(ActUser user) {
		this.user = user;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	
}
