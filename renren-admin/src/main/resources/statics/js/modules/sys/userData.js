$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/userData/list',
        datatype: "json",
        colModel: [
			{ label: '用户ID', name: 'id', width: 30, key: true },
			{ label: '微信名', name: 'nickName', sortable: false, width: 60 },
            { label: '获得红包', name: 'redPacket', sortable: false, width: 60 },
			{ label: '可提现佣金', name: 'money', width: 100},
			{ label: '分享人数', name: 'shareNum', width: 80 },
            { label: '购买人数', name: 'payNum', width: 80 },
			{ label: '重置红包', width: 80,formatter: function (value,options,row) {
				return "<a  style=\"cursor:pointer\" onclick='resetRedPacket(" + row.id + "," + row.redPacket + ")'> 操作 </a>"
			}},
			{ label: '重置佣金', width: 80,formatter: function (value,options,row) {
				return "<a  style=\"cursor:pointer\" onclick='resetMoney(" + row.id + ")'> 操作 </a>"
			}}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });



});

function resetRedPacket(uid,redPacket) {
	console.log("begin resetRedPacket")
	if(redPacket == 0) {
		alert("已重置红包")
		return
	}
	confirm("确定要重置红包吗？",function () {
		$.ajax({
			type: "POST",
			url: baseURL + "sys/userData/resetRedPacket",
			contentType: "application/json",
			data: JSON.stringify(uid),
			success: function(r){
				if(r.code == 0){
					alert(r.msg, function(index){
						vm.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	})
}

function resetMoney(uid) {
	console.log("begin resetMoney")
	confirm("确定要重置佣金吗？",function () {
		$.ajax({
			type: "POST",
			url: baseURL + "sys/userData/resetMoney",
			contentType: "application/json",
			data: JSON.stringify(uid),
			success: function(r){
				if(r.code == 0){
					alert(r.msg, function(index){
						vm.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	})
}


var vm = new Vue({
	el:'#rrapp',
	data:{
		paramKey: null,
		showList: true,
		title: null,
		foods: {
            imgUrl: null
		},

	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			console.log()
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'id': vm.paramKey},
                page:page
            }).trigger("reloadGrid");
		},



	}
});