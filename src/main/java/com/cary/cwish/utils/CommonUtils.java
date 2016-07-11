package com.cary.cwish.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	
	/**
	 * get account user name
	 * @param req
	 * @return
	 */
	public final static String getUserNameInCookie(HttpServletRequest req) {
		if (req.getCookies() != null){
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals("account")) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * delete particular cookie by name
	 * @param name
	 * @param req
	 */
	public final static Cookie deleteCookie(String name, HttpServletRequest req) {
		if (req.getCookies() != null) {
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals(name)) {
					c.setValue(null);
					c.setMaxAge(0);
					return c;
				}
			}
		}
		return null;
	}
}
