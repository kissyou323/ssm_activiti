package com.parkson.erp.activiti.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parkson.erp.activiti.model.Group;
import com.parkson.erp.activiti.model.MemberShip;
import com.parkson.erp.activiti.model.ActUser;
import com.parkson.erp.activiti.service.MemberShipService;
import com.parkson.erp.activiti.util.ResponseUtil;
import com.parkson.erp.activiti.util.StringUtil;

@Controller
@RequestMapping("/memberShip")
public class MenberShipController {
		@Resource
		private MemberShipService memberShipService;
		
		@RequestMapping("/updateMemberShip")
		public String updateMemberShip(HttpServletResponse response,String userId,String groupsIds) throws Exception{
			//刪除全部角色
			memberShipService.deleteAllGroupsByUserId(userId);
			
			if(StringUtil.isNotEmpty(groupsIds)){
				//分割字符串，以，分割
				String idsArr[]=groupsIds.split(",");
				for(String groupId:idsArr){
					ActUser user=new ActUser();
					user.setId(userId);
					Group group=new Group();
					group.setId(groupId);
					MemberShip memberShip=new MemberShip();
					memberShip.setUser(user);
					memberShip.setGroup(group);
					memberShipService.addMemberShip(memberShip);
				}
			}
			JSONObject result=new JSONObject();
			result.put("success", true);
			ResponseUtil.write(response, result);
			return null;
		}
}
