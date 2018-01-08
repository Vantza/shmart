<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>7个工作日提醒</title>
		<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="../CSS/bootstrap-table.min.css">
		<link rel="stylesheet" href="../CSS/table.css">
		<script src="http://libs.baidu.com/jquery/1.8.2/jquery.min.js"></script>
	   	<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<script src="../JS/jquery.base64.js"></script>
		<script src="../JS/bootstrap-table.js"></script>
		<script src="../JS/bootstrap-table-export.js"></script>
		<script src="../JS/SHEmail.js"></script>
	</head>
	<body>
	<div class="row"></div>
	<div class="container-fluid col-md-8 col-md-offset-2">
		<form class="form-inline" role="form col-md-8">
		   	<div class="form-group col-lg-6">
		   		<label>流程类型：</label>
              	<select id = "selectForm" class="form-control" style="width: auto;">
				  <option value ="contractProcess">科传签约流程</option>
				  <option value ="retireProcess">科传退租流程</option>
				</select>
               	<button class="btn btn-default" type="button" onclick='showData()'>查询!</button>
               	<button class="btn btn-primary" type="button" onclick=''>保存至本地!</button>
         	</div><!-- /.col-lg-6 -->
		</form>
	</div>
	<br/>
	<div class="container-fluid col-md-8 col-md-offset-2 fileInfo"></div>
	<br/>
	<div class="container-fluid col-md-8 col-md-offset-2 downloadList"></div>
	<div class="container-fluid col-md-8 col-md-offset-2"><p> </p><br/></div>
	<div class="container-fluid col-md-8 col-md-offset-2 showList"></div>
	<div class="container-fluid col-md-10 col-md-offset-1 invoiceTable">
		<table id="table"
	           data-toggle="table"
	           data-show-columns="true"
	           data-search="false"
	           data-show-refresh="false"
	           data-show-toggle="true"
	           data-pagination="true"
	           data-height="750"
	           data-striped="true"
	           data-pagination="true"
	           data-only-info-pagination="true"
	           data-page-size="20"
	           data-page-list=[20,30,50,ALL]>
	        <thead>
	        <tr>
	            <th data-field="id" data-formatter="idFormatter">ID</th>
	            <th data-field="name">Item Name</th>
	            <th data-field="price">Item Price</th>
	        </tr>
	        </thead>
	    </table>
	</div>

	</body>
</html>