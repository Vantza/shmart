<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ERROR PAGE</title>
</head>
<body>
	<h1>Cary says : yo!</h1>
	<h2>Some thing happens</h2>
	<%= request.getAttribute("Exception") %>
</body>
</html>