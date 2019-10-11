
$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()

        })
    });
    //生成用户数据
    $('#mytab').bootstrapTable({
    	method: 'get',
    	contentType: "application/x-www-form-urlencoded",
    	url:"/manage/test/queryPageExpireTestInfos",
    	height:tableHeight(),//高度调整
    	toolbar: '#toolbar',
    	striped: true, //是否显示行间隔色
    	dataField: "records",
    	pageNumber: 1, //初始化加载第一页，默认第一页
    	pagination:true,//是否分页
    	queryParamsType:'limit',
    	queryParams:queryParams,
    	sidePagination:'server',
    	pageSize:10,//单页记录数
    	pageList:[10,20,30,50],//分页步进值
        smartDisplay: false,
    	showRefresh:true,//刷新按钮
    	showColumns:true,
    	clickToSelect: true,//是否启用点击选中行
    	toolbarAlign:'right',
    	buttonsAlign:'right',//按钮对齐方式
        singleSelect : true, //单选
    	toolbar:'#toolbar',//指定工作栏
    	columns:[

        	{
        		title:'试卷编号',
        		field:'test_id',
        		visible:false
        	},
        	{
        		title:'试卷',
        		field:'test_title',
        		sortable:true
        	},
            {
                title:'考试科目',
                field:'sub_name',
                sortable:true
            },
            {
                title:'科目老师',
                field:'teach_name',
                sortable:true
            },
            {
                title:'考试起始日期',
                field:'start_time',
                sortable:true
            },
            {
                title:'考试截止时间',
                field:'end_time',
                sortable:true
            },
            {
                title:'考试时长',
                field:'test_time',
                sortable:true
            },
            {
                title:'状态',
                field:'status',
                align:'center',
                formatter:operateFormatter
            }

    	],
    	locale:'zh-CN',//中文支持,
    });


    //请求服务数据时所传参数
   // var dataArr=$('#mytab').bootstrapTable('getSelections');
    function queryParams(params){
    	return{
    		pageSize: params.limit,
    		pageIndex:params.pageNumber
    	}
    }

    function operateFormatter(value,row,index){
        if(value==2){
            return '<button  type="button" class="btn btn-primary btn-lg" style="background-color: #7399b8" disabled="disabled">已过期</button>'

        }
    }
    
    //增加按钮事件
    $('#btn_add').click(function(){

        $('.modal fade').css('display','block');
        $('.modal fade').addClass('animated slideInRight');
    });


    //删除按钮与修改,重置按钮的出现与消失

    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
        var dataArr=$('#mytab .selected');

        if(dataArr.length==1){
            $('#btn_exam').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
        }else{

            $('#btn_exam').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_exam').css('display','none');
            },400);
        }

    });



    //弹出框取消按钮事件
   　　$('.popup_de .btn_cancel').click(function(){
	   $('.popup_de').removeClass('bbox');
   　　});
    //弹出框关闭按钮事件
     $('.popup_de .popup_close').click(function(){
	   $('.popup_de').removeClass('bbox');
   　　})
});
function tableHeight() {
    return $(window).height() - 140;
}
