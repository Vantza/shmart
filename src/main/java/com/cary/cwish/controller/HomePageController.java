package com.cary.cwish.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cary.cwish.pojo.Invoice;
import com.cary.cwish.service.InvoiceService;
import com.cary.cwish.utils.WishConstant;

@Controller
@RequestMapping("/home")
public class HomePageController {
	@Resource
	InvoiceService is;
	
	@RequestMapping(value= "/")
	public ModelAndView getHomePage(HttpServletRequest request, Model model) throws Exception{
		ModelAndView mav = new ModelAndView(WishConstant.HOME_PAGE);
		Invoice i = is.getInvoiceByID(2);
		mav.addObject("invoice", i);
        return mav;
	}
}
