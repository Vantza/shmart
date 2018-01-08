package com.cary.cwish.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/"})
public class HomeRedirectController {
	private static Logger logger = Logger.getLogger(HomeRedirectController.class);
	
	@RequestMapping(value={"/"})
	public void index(HttpServletRequest req, HttpServletResponse res) throws IOException {
		logger.info("get in '/' and redirect to '/SHMail'");
		res.sendRedirect("/shmart/SHMail/");
	}
}
