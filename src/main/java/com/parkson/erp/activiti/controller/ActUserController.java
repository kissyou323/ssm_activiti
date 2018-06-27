package com.parkson.erp.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parkson.erp.activiti.model.Group;
import com.parkson.erp.activiti.model.MemberShip;
import com.parkson.erp.activiti.model.PageInfo;
import com.parkson.erp.activiti.model.ActUser;
import com.parkson.erp.activiti.service.GroupService;
import com.parkson.erp.activiti.service.MemberShipService;
import com.parkson.erp.activiti.service.ActUserService;
import com.parkson.erp.activiti.util.ResponseUtil;

/**
 * 用户管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class ActUserController {

	@Resource
	private ActUserService actuserService;
	
	@Resource
	private MemberShipService menberShipService;
	
	@Resource
	private GroupService groupService;
	/**
	 * 
	 * 登入
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userLogin")
	public String userLogin(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userName", request.getParameter("userName"));
		map.put("password", request.getParameter("password"));
		map.put("groupId", request.getParameter("groupId"));
		MemberShip memberShip=menberShipService.userLogin(map);
		JSONObject result=new JSONObject();
		if(memberShip==null){
			result.put("success", false);
			result.put("errorInfo", "用户名或者密码错误！");
		}else{
			result.put("success", true);
			request.getSession().setAttribute("currentMemberShip", memberShip);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 分页查询用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/userPage")
	public String userPage(HttpServletResponse response,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			ActUser user) throws Exception{
		Map<String,Object> userMap=new HashMap<String, Object>();
		userMap.put("id", user.getId());
		
		PageInfo<ActUser> userPage = new PageInfo<ActUser>();
		Integer pageSize=Integer.parseInt(rows);
		userPage.setPageSize(pageSize);
		
		// 第几页
		String pageIndex = page;
		if (pageIndex == null || pageIndex == "") {
			pageIndex = "1";
		}
		userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
				* pageSize);
		// 取得总页数
		int userCount=actuserService.userCount(userMap);
		userPage.setCount(userCount);
		userMap.put("pageIndex", userPage.getPageIndex());
		userMap.put("pageSize", userPage.getPageSize());

		List<ActUser> cusDevPlanList = actuserService.userPage(userMap);
		JSONObject json = new JSONObject();
		// 把List格式转换成JSON
		JSONArray jsonArray = JSONArray.fromObject(cusDevPlanList);
		json.put("rows", jsonArray);
		json.put("total", userCount);
		ResponseUtil.write(response, json);
		return null;
	}
	/**
	 * 修改用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateUser")
	public String updateUser(HttpServletResponse response,ActUser uses) throws Exception{
		int result=actuserService.updateUser(uses);
		JSONObject json=new JSONObject();
		if(result>0){
			json.put("success", true);
		}else{
			json.put("success", false);
		}
		ResponseUtil.write(response, json);
		return null;
	}
	/**
	 * 批量刪除用戶
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteUser")
	public String deleteUser(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String id=request.getParameter("ids");
		JSONObject json=new JSONObject();
		List<String> list=new ArrayList<String>();
		  String[] strs = id.split(",");
	        for (String str : strs) {
	        	list.add(str);
	        }
	   try {	
			int userResult=actuserService.deleteUser(list);
			if(userResult>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		} catch (Exception e) {
			json.put("success", false);
			e.printStackTrace();
		}
		ResponseUtil.write(response, json);
		return null;
	}

	/**
	 * 新增用戶
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/userSave")
	public String userSave(HttpServletResponse response,ActUser user) throws Exception{
		int userResult=actuserService.addUser(user);
		JSONObject json=new JSONObject();
		if(userResult>0){
			json.put("success", true);
		}else{
			json.put("success", false);
		}
		ResponseUtil.write(response, json);
		return null;
	}
	
	@RequestMapping("/listWithGroups")
	public String listWithGroups(HttpServletResponse response,String rows,String page,ActUser user) throws Exception{
		PageInfo<ActUser> userPage=new PageInfo<ActUser>();
		Map<String,Object> userMap=new HashMap<String, Object>();
		userMap.put("id", user.getId());
		Integer pageSize=Integer.parseInt(rows);
		userPage.setPageSize(pageSize);
		
		// 第几页
		String pageIndex = page;
		if (pageIndex == null || pageIndex == "") {
			pageIndex = "1";
		}
		userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
				* pageSize);
		// 取得总页数
		int userCount=actuserService.userCount(userMap);
		userPage.setCount(userCount);
		userMap.put("pageIndex", userPage.getPageIndex());
		userMap.put("pageSize", userPage.getPageSize());

		List<ActUser> userList = actuserService.userPage(userMap);
		for(ActUser users:userList){
			StringBuffer buffer=new StringBuffer();
			List<Group> groupList=groupService.findByUserId(users.getId());
			for(Group g:groupList){
				buffer.append(g.getName()+",");
			}
			if(buffer.length()>0){
				//deleteCharAt 删除最后一个元素
				users.setGroups(buffer.deleteCharAt(buffer.length()-1).toString());
			}else{
				user.setGroups(buffer.toString());
			}
		}
		JSONArray jsonArray=JSONArray.fromObject(userList);
		JSONObject result=new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", userCount);
		ResponseUtil.write(response, result);
		return null;
	}
	
}
