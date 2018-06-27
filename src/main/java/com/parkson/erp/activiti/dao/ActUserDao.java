package com.parkson.erp.activiti.dao;

import java.util.List;
import java.util.Map;

import com.parkson.erp.activiti.model.ActUser;

public interface ActUserDao {

	public ActUser findById(String userid);
	/**
	 * 登入
	 * 
	 * @return
	 */
	public ActUser userLogin(ActUser user);

	/**'
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public List<ActUser> userPage(Map<String, Object> map);
	
	public int userCount(Map<String, Object> map);
	
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(List<String> id);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(ActUser user);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(ActUser user);
}
