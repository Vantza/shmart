package com.cary.cwish.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cary.cwish.utils.WishConstant;

@Controller
@RequestMapping("/SHEmail")
public class SHEmailController {
	@RequestMapping(value= "/")
	public ModelAndView getReimbursementRecords(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(WishConstant.SHEMAIL);
		
		return mav;
	}
	
	@RequestMapping(value="/listRecords")
	public void getlistRecords(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		res.getWriter().write(jsonObject.toString());
	}
}
