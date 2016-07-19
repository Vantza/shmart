<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>INVOICE_VAT_EXP</title>
		<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="../CSS/bootstrap-table.min.css">
		<link rel="stylesheet" href="../CSS/table.css">
		<script src="http://libs.baidu.com/jquery/1.8.2/jquery.min.js"></script>
	   	<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="../JS/PrintExcel.js"></script>
		<script src="../JS/tableExport.js"></script>
		<script src="../JS/jquery.base64.js"></script>
		<script src="../JS/bootstrap-table.js"></script>
		<script src="../JS/bootstrap-table-export.js"></script>
	</head>
	<body>
	<div class="row"></div>
	<div class="container-fluid col-md-8 col-md-offset-2">
		<form role="form">
		   	<div class="col-lg-6">
		   		<label>请输入请求编号： </label>
            	<div class="input-group">
               		<input type="text" class="form-control requiredId" placeholder="Input RequiredId...">
               		<span class="input-group-btn">
	                  	<button class="btn btn-default" type="button" onclick='showData()'>
	                    	 查询!
	                  	</button>
	                  	<button class="btn btn-default" type="button" onclick='printExcel()'>
	                    	 导出到Excel!
	                  	</button>
               		</span>
            	</div><!-- /input-group -->
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
	           data-height="800">
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