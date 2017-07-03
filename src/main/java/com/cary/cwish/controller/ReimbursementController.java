package com.cary.cwish.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cary.cwish.utils.WishConstant;

@Controller
@RequestMapping("/reimbursement")
public class ReimbursementController {
	
	@RequestMapping(value= "/")
	public ModelAndView getReimbursementRecords(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(WishConstant.REIMBURSEMENT);
		
		return mav;
	}
}
