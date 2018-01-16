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
//			alert(searchType);
//			alert(result);
			if ($('#selectForm')[0].value == 'contractProcess') {
				buildContractProcessPushTable(result.ContractProcessPushList);
			} else if ($('#selectForm')[0].value == 'retireProcess') {
				buildRetireProcessPushTable(result.RetireProcessPushList);
			} else if ($('#selectForm')[0].value == 'modifyContract') {
				buildModifyContractPushTable(result.ModifyContractPushList);
			} else if ($('#selectForm')[0].value == 'retireStart') {
				buildRetireStartPushTable(result.RetireStartPushList);
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


//构造退租流程审批列表
function buildRetireProcessPushTable(RetireProcessPushList) {
	var columns = [],
	data = [],
	row;
	
	columns.push({
		field: 'processPoint',
        title: '科传系统审批环节'
	}, {
		field: 'operator',
        title: '审批环节操作人'
	}, {
		field: 'units',
        title: '单元号'
	}, {
		field: 'leaseNumber',
        title: '合同编号'
	}, {
		field: 'startRetireTime',
        title: '退租创建日期'
	}, {
		field: 'originalStatus',
        title: '原系统审批状态'
	}, {
		field: 'originalPassTime',
        title: '原系统审批通过日期'
	});
	
	for (var i=0; i<RetireProcessPushList.length; i++) {
		row = {}
		row['processPoint'] = RetireProcessPushList[i].processPoint;
		row['operator'] = RetireProcessPushList[i].operator;
		row['units'] = RetireProcessPushList[i].units;
		row['leaseNumber'] = RetireProcessPushList[i].leaseNumber;
		row['startRetireTime'] = RetireProcessPushList[i].startRetireTime;
		row['originalStatus'] = RetireProcessPushList[i].originalStatus;
		row['originalPassTime'] = RetireProcessPushList[i].originalPassTime;
		data.push(row);
	}

	$('#table').bootstrapTable('destroy').bootstrapTable({
        columns: columns,
        data: data
    });
	
}


//构造签约流程未提交列表
function buildModifyContractPushTable(ModifyContractPushList) {
	var columns = [],
	data = [],
	row;
	
	columns.push({
		field: 'operator',
        title: '科传系统合同状态'
	}, {
		field: 'creator',
        title: '合同创建人'
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
	
	for (var i=0; i<ModifyContractPushList.length; i++) {
		row = {}
		row['operator'] = ModifyContractPushList[i].operator;
		row['creator'] = ModifyContractPushList[i].creator;
		row['leaseNumber'] = ModifyContractPushList[i].leaseNumber;
		row['bpmsn'] = ModifyContractPushList[i].bpmsn;
		row['proValue'] = ModifyContractPushList[i].proValue;
		row['rentValue'] = ModifyContractPushList[i].rentValue;
		row['rentType'] = ModifyContractPushList[i].rentType;
		row['accepttime'] = ModifyContractPushList[i].accepttime;
		data.push(row);
	}

	$('#table').bootstrapTable('destroy').bootstrapTable({
        columns: columns,
        data: data
    });
}

function buildRetireStartPushTable(RetireStartPushList) {
	var columns = [],
	data = [],
	row;
	
	columns.push({
		field: 'creator',
        title: '退租创建人'
	}, {
		field: 'units',
        title: '单元号'
	}, {
		field: 'leaseNumber',
        title: '合同编号'
	}, {
		field: 'createAt',
        title: '退租创建日期'
	}, {
		field: 'status',
        title: '原系统流程状态'
	}, {
		field: 'passTime',
        title: '原系统流程审批通过日期'
	});
	
	for (var i=0; i<RetireStartPushList.length; i++) {
		row = {}
		row['creator'] = RetireStartPushList[i].creator;
		row['units'] = RetireStartPushList[i].units;
		row['leaseNumber'] = RetireStartPushList[i].leaseNumber;
		row['createAt'] = RetireStartPushList[i].createAt;
		row['status'] = RetireStartPushList[i].status;
		row['passTime'] = RetireStartPushList[i].passTime;
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
			alert('Email sent successfully!!');
		},
		error: function () {
			alert('Email sent failed!!')
		}
	});
}