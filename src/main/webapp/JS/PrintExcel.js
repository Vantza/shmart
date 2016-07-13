function printExcel() {
	if ($('.requiredId').val()==null || $('.requiredId').val()=='') {
		alert('请输入RequiredId！！！');
	}
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
			// arts = eval('(' + result + ')');
			// setArticles(arts);
		}
	});
}