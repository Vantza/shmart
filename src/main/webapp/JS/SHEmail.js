$(function(){ 
	$.ajaxSetup({cache:false});
});


function showData() {
	requiredId = $('.requiredId').val();
	$.ajax({
		type: "GET",
		url: "SHEmail/listRecords",
		async: true,
		data: {'processType' : processType},
		success: function(result) {
			result = eval('(' + result + ')');
		},
		error: function () {
            alert('Failed!! Please try again!!');
        }
	});
}