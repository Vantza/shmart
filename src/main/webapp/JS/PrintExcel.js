function printExcel() {
	getRecord();
}

function getRecord() {
	$.ajax({
		type: "GET",
		url: "getRecords",
		async: true,
		success: function(result) {
			alert(result);
			// arts = eval('(' + result + ')');
			// setArticles(arts);
		}
	});
}