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

var requiredId,
	invoiceData;

function getRecord() {
	requiredId = $('.requiredId').val();
	$.ajax({
		type: "GET",
		url: "getRecords",
		async: true,
		data: {'requiredId' : requiredId},
		success: function(result) {
			result = eval('(' + result + ')');
			invoiceData = result.invoices;
			alert(result.success);
			buildDownloadList(result.excelName);
			buildInvoiceTable([result.invoices]);
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
			buildFileInfo(result.info);
		}
	});
}

function buildFileInfo(info) {
	var outstr = "";

	outstr = outstr + info

	$('.fileInfo').html(outstr);
}

function buildInvoiceTable(invoices) {
	buildTable(invoices, 0);
}

function buildTable(invoices, fileNo) {
	var columns = [],
		data = [],
		row,
		invoiceList = invoices[fileNo];

	columns.push({
		field: 'invoice_type',
        title: '发票种类'
	}, {
		field: 'invoice_sequence',
        title: '单据号'
	}, {
		field: 'sequence_date',
        title: '单据日期'
	}, {
		field: 'customer_number',
        title: '客户编号'
	}, {
		field: 'customer_name',
        title: '客户名称'
	}, {
		field: 'customer_vatnumber',
        title: '客户税号'
	}, {
		field: 'customer_vataddresstele',
        title: '客户地址电话'
	}, {
		field: 'customer_vatbanknum',
        title: '客户银行及帐号'
	}, {
		field: 'comments',
        title: '备注'
	}, {
		field: 'SpecialInvoice_RedNumber',
        title: '专用发票红票通知单号'
	}, {
		field: 'CommonInvoice_EquivalentCode',
        title: '普通发票红票对应正数发票代码'
	}, {
		field: 'CommonInvoice_EquivalentNumber',
        title: '普通发票红票对应正数发票号码'
	}, {
		field: 'Created_UserName',
        title: '开票人'
	}, {
		field: 'Checked_UserName',
        title: '复核人'
	}, {
		field: 'Receipt_UserName',
        title: '收款人'
	}, {
		field: 'Sale_BankNum',
        title: '销方银行及帐号'
	}, {
		field: 'Sale_AddressTele',
        title: '销方地址电话'
	}, {
		field: 'Item_Number',
        title: '商品编号'
	}, {
		field: 'Item_Name',
        title: '商品名称'
	}, {
		field: 'Specification',
        title: '规格型号'
	}, {
		field: 'Uom_Code',
        title: '计量单位'
	}, {
		field: 'Qty',
        title: '数量'
	}, {
		field: 'VatInclusive_Amt',
        title: '金额（含税）'
	}, {
		field: 'Tax_Percent',
        title: '税率'
	}, {
		field: 'Tax_Amt',
        title: '税额'
	}, {
		field: 'VatInclusive_DiscountAmt',
        title: '折扣金额(含税)'
	}, {
		field: 'Tax_DiscountAmt',
        title: '折扣税额'
	}, {
		field: 'data1',
        title: '单价'
	}, {
		field: 'data2',
        title: '折扣率'
	}, {
		field: 'data3',
        title: '收货人'
	}, {
		field: 'data4',
        title: '收货人纳税人识别号'
	}, {
		field: 'data5',
        title: '发货人'
	}, {
		field: 'data6',
        title: '发货人纳税人识别号'
	}, {
		field: 'data7',
        title: '起运地、经由、到达地'
	}, {
		field: 'data8',
        title: '车种车号'
	}, {
		field: 'data9',
        title: '车船吨位'
	}, {
		field: 'data10',
        title: '运输货物信息'
	}, {
		field: 'data11',
        title: '编码版本号'
	}, {
		field: 'data12',
        title: '税收分类编码'
	}, {
		field: 'data13',
        title: '是否享受优惠政策'
	}, {
		field: 'data14',
        title: '享受税收优惠政策内容'
	}, {
		field: 'data15',
        title: '零税率标识'
	});

	for (var i=0; i<invoiceList.length; i++) {
		row = {}
		row['invoice_type'] = invoiceList[i].invoiceType;
		row['invoice_sequence'] = invoiceList[i].invoiceSequence;
		row['sequence_date'] = invoiceList[i].sequenceDate;
		row['customer_number'] = invoiceList[i].customerNumber;
		row['customer_name'] = invoiceList[i].customerName;
		row['customer_vatnumber'] = invoiceList[i].customerVatnumber;
		row['customer_vataddresstele'] = invoiceList[i].customerVataddresstele;
		row['customer_vatbanknum'] = invoiceList[i].customerVatbanknum;
		row['comments'] = invoiceList[i].comments;
		row['SpecialInvoice_RedNumber'] = invoiceList[i].specialinvoiceRednumber;
		row['CommonInvoice_EquivalentCode'] = invoiceList[i].commoninvoiceEquivalentcode;
		row['CommonInvoice_EquivalentNumber'] = invoiceList[i].commoninvoiceEquivalentnumber;
		row['Created_UserName'] = invoiceList[i].createdUsername;
		row['Checked_UserName'] = invoiceList[i].checkedUsername;
		row['Receipt_UserName'] = invoiceList[i].receiptUsername;
		row['Sale_BankNum'] = invoiceList[i].saleBanknum;
		row['Sale_AddressTele'] = invoiceList[i].saleAddresstele;
		row['Item_Number'] = invoiceList[i].itemNumber;
		row['Item_Name'] = invoiceList[i].itemName;
		row['Specification'] = invoiceList[i].specification;
		row['Uom_Code'] = invoiceList[i].uomCode;
		row['Qty'] = invoiceList[i].qty;
		row['VatInclusive_Amt'] = invoiceList[i].vatinclusiveAmt;
		row['Tax_Percent'] = invoiceList[i].taxPercent;
		row['Tax_Amt'] = invoiceList[i].taxAmt;
		row['VatInclusive_DiscountAmt'] = invoiceList[i].vatinclusiveDiscountamt;
		row['Tax_DiscountAmt'] = invoiceList[i].taxDiscountamt;
		row['data1'] = invoiceList[i].data1;
		row['data2'] = invoiceList[i].data2;
		row['data3'] = invoiceList[i].data3;
		row['data4'] = invoiceList[i].data4;
		row['data5'] = invoiceList[i].data5;
		row['data6'] = invoiceList[i].data6;
		row['data7'] = invoiceList[i].data7;
		row['data8'] = invoiceList[i].data8;
		row['data9'] = invoiceList[i].data9;
		row['data10'] = invoiceList[i].data10;
		row['data11'] = 1;
		row['data12'] = (invoiceList[i].taxPercent == 0.05?'3040502029902':'3040801');
		row['data13'] = invoiceList[i].data13;
		row['data14'] = invoiceList[i].data14;
		row['data15'] = invoiceList[i].data15;
		data.push(row);
	}

	$('#table').bootstrapTable('destroy').bootstrapTable({
        columns: columns,
        data: data
    });
}


function buildTablePre(i) {
	buildTable(invoiceData, i);
}

function showData() {
	requiredId = $('.requiredId').val();
	$.ajax({
		type: "GET",
		url: "listRecords",
		async: true,
		data: {'requiredId' : requiredId},
		success: function(result) {
			result = eval('(' + result + ')');
			buildTable([result.invoices], 0);
		},
		error: function () {
            alert('Failed!! Please try again!!');
        }
	});
}