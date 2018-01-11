$(function(){ 
	$.ajaxSetup({cache:false});
});


function showData() {
	var searchType = $('#selectForm')[0].value;
	
	$.ajax({
		type: "GET",
		url: "listRecords",
		async: true,
		data: {'SearchType': searchType},
		success: function(result) {
			result = eval('(' + result + ')');
			//alert('success');
			//alert(searchType);
			if ($('#selectForm')[0].value == 'contractProcess') {
				buildContractProcessPushTable(result.ContractProcessPushList);
			}
			
		},
		error: function () {
            alert('Failed!! Please try again!!');
        }
	});
}

//构造签约流程审批列表
function buildContractProcessPushTable(ContractProcessPushList) {
	var columns = [],
	data = [],
	row;
	
	columns.push({
		field: 'processPoint',
        title: '科传系统待审批人'
	}, {
		field: 'creator',
        title: '创建人'
	}, {
		field: 'leaseNumber',
        title: '合同编号'
	}, {
		field: 'bpmsn',
        title: '签约流程号'
	}, {
		field: 'proValue',
        title: '审批状态'
	}, {
		field: 'rentValue',
        title: '租赁状态'
	}, {
		field: 'rentType',
        title: '租约类型'
	}, {
		field: 'accepttime',
        title: '原系统租约生效日期'
	});
	
	
	for (var i=0; i<ContractProcessPushList.length; i++) {
		row = {}
		row['processPoint'] = ContractProcessPushList[i].processPoint;
		row['creator'] = ContractProcessPushList[i].creator;
		row['leaseNumber'] = ContractProcessPushList[i].leaseNumber;
		row['bpmsn'] = ContractProcessPushList[i].bpmsn;
		row['proValue'] = ContractProcessPushList[i].proValue;
		row['rentValue'] = ContractProcessPushList[i].rentValue;
		row['rentType'] = ContractProcessPushList[i].rentType;
		row['accepttime'] = ContractProcessPushList[i].accepttime;
		data.push(row);
	}

	$('#table').bootstrapTable('destroy').bootstrapTable({
        columns: columns,
        data: data
    });
	
}


//保存选择的流程信息至EXCEL文件
function saveData() {
	var saveType = $('#selectForm')[0].value;
	
	$.ajax({
		type: "GET",
		url: "saveRecordsToExcel",
		async: true,
		data: {'saveType' : saveType},
		success: function(result) {
			result = eval('(' + result + ')');
			alert('Save data successfully!!');
		},
		error: function () {
			alert('Save data failed!!')
		}
	});
}


//发送提醒邮件给用户
function sendReminderEmail() {
	var saveType = $('#selectForm')[0].value;
	
	$.ajax({
		type: "GET",
		url: "sendReminderEmail",
		async: true,
		data: {'saveType' : saveType},
		success: function(result) {
			result = eval('(' + result + ')');
		},
		error: function () {
			alert('Email sent failed!!')
		}
	});
}