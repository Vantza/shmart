<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>INVOICE_VAT_EXP</title>
		<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	   	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	   	<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="../JS/PrintExcel.js"></script>
	</head>
	<body>
	ShangHaiMart INVOICE_VAT_EXP
	<%=request.getAttribute("invoice").toString() %>
	<div class="container-fluid col-md-8 col-md-offset-2">
		<form role="form">
		   	<div class="col-lg-6">
            	<div class="input-group">
               		<input type="text" class="form-control">
               		<span class="input-group-btn">
	                  	<button class="btn btn-default" type="button" onclick='printExcel()'>
	                    	 导出到Excel!
	                  	</button>
               		</span>
            	</div><!-- /input-group -->
         	</div><!-- /.col-lg-6 -->
		</form>
	</div>

	</body>
</html>