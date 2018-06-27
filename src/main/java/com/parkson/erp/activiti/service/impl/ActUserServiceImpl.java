package com.parkson.erp.activiti.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.parkson.erp.activiti.dao.ActUserDao;
import com.parkson.erp.activiti.model.ActUser;
import com.parkson.erp.activiti.service.ActUserService;

@Service("actUserService")
public class ActUserServiceImpl implements ActUserService{
	
	@Resource
	private ActUserDao actUserDao;
	
	public ActUser findById(String userId){
		return actUserDao.findById(userId);
	}
	/**
	 * 登入
	 * @return
	 */
	public ActUser userLogin(ActUser user){
		return actUserDao.userLogin(user);
	}
	
	/**'
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public List<ActUser> userPage(Map<String, Object> map){
		return actUserDao.userPage(map);
	}
	
	public int userCount(Map<String, Object> map){
		return actUserDao.userCount(map);
	}
	
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(List<String> id){
		return actUserDao.deleteUser(id);
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(ActUser user){
		return actUserDao.updateUser(user);
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(ActUser user){
		return actUserDao.addUser(user);
	}
}
