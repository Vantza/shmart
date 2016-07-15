$(function(){ 
　　$.ajaxSetup({cache:false});
});

function printExcel() {
	if ($('.requiredId').val()==null || $('.requiredId').val()=='') {
		alert('请输入RequiredId！！！');
	}
	getFileInfo();
	getRecord();
}

var requiredId;

function getRecord() {
	requiredId = $('.requiredId').val();
	$.ajax({
		type: "GET",
		url: "getRecords",
		async: true,
		data: {'requiredId' : requiredId},
		success: function(result) {
			alert(result);
			result = eval('(' + result + ')');
			// setArticles(arts);
			buildDownloadList(result.excelName);
		},
		error: function () {
            alert('Failed!! Please try again!!');
        }
	});
}

function buildDownloadList(excelName) {
	var outstr = "";

	$.each(excelName, function (i, name)  {
		outstr = outstr + "<a href='../InvoiceExcel/" + name + "' download='" + name + "'>" + name + "</a><br/>"
	});

	$('.downloadList').html(outstr);
}

function getFileInfo() {
	requiredId = $('.requiredId').val();
	$.ajax({
		type: "GET",
		url: "getFileInfo",
		async: true,
		data: {'requiredId' : requiredId},
		success: function(result) {
			alert(result);
			result = eval('(' + result + ')');
			// setArticles(arts);
			buildFileInfo(result.info);
		}
	});
}

function buildFileInfo(info) {
	var outstr = "";

	outstr = outstr + info

	$('.fileInfo').html(outstr);
}