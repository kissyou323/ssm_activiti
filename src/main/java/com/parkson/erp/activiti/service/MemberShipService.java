package com.parkson.erp.activiti.service;

import java.util.Map;

import com.parkson.erp.activiti.model.MemberShip;

public interface MemberShipService {
	/**
	 * 用户登入的方法
	 * @return
	 */
	public MemberShip userLogin(Map<String, Object> map);
	
	public int deleteAllGroupsByUserId(String userId);
	
	public int addMemberShip(MemberShip memberShip);
}
