
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
    	url:"/manage/test/queryStuTestScores",
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
        		title:'选择',
        		field:'select',
        		checkbox:true,
        		width:25,
        		align:'center',
        		valign:'middle'
        	},
        	{
        		title:'成绩序号',
        		field:'score_id',
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
                title:'考试用时',
                field:'test_time',
                sortable:true
            },
            {
                title:'得分',
                field:'score',
                sortable:true
            },
            {
                title:'考试日期',
                field:'create_time',
                sortable:true
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
    //页面回退按钮事件
    $('#edit_backBtn').click(function(){
        $('.changeBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.tableBody').css('display','block').addClass('animated slideInRight');
        $('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryStuTestScores'});
    });


    //删除按钮与修改,重置按钮的出现与消失

    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
        var dataArr=$('#mytab .selected');
        var dataA=$('#mytab').bootstrapTable('getSelections');
        console.log(dataA)
        var nowTime = new Date();
        var endTime = dataA[0].end_time;
        console.log(endTime)
        endTime = endTime.replace("-","/");
        var d1 = new Date(Date.parse(endTime));
        if(dataArr.length==1 && nowTime>d1){
            $('#btn_exam').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
        }else{

            $('#btn_exam').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_exam').css('display','none');
            },400);
        }

    });


    var n = 1;

    //查看试卷答题详情事件
    $('#btn_exam').click(function(){
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.changeBody').css('display','block');
        $('.changeBody').addClass('animated slideInRight');
        var dataArr=$('#mytab').bootstrapTable('getSelections');

        document.getElementById("testTitle").innerText=dataArr[0].test_title;

        document.getElementById("subName").innerText=dataArr[0].sub_name;

        document.getElementById("testTime").innerText=dataArr[0].test_time +" 分钟";

        document.getElementById("totalScore").innerText=dataArr[0].score +" 分";
        var QuestionJosn =[];
        var title_id;
        $(function(){
            //初始化下拉框
            //动态加载
            $.ajax({
                type: 'get',
                url: '/manage/test/queryStuTsetAnswerInfos',
                data: {testId:dataArr[0].test_id,count:dataArr[0].score_number},
                dataType: "json",
                success: function (data) {
                    //拼接div
                    var str ="";
                    for(var i=0;i<data.length;i++) {
                        var partId = '';
                        var partName='';
                        if(data[i].part_id == 0){
                            var partId = '';
                        }else{
                            var partId = data[i].part_id;
                        }
                        if(data[i].part_name == null){
                            var partName = '未填';
                        }else{
                            var partName = data[i].part_name;
                        }

                        if (data[i].status == 2) {

                            str += "<li><span class='glyphicon glyphicon-ok' style='color:green '></span>&nbsp;<strong>第 " + (i + 1) + " 题 、</strong>" + data[i].title_content + "</li>" +
                                "<li style='margin-left: 25px;'><strong>你的答案：</strong><span >" + partId + "."+partName+"</span></li>" +
                                "<li style='margin-left: 25px;color: #00BE67'><strong>正确答案：</strong><span>" + data[i].modelAnswer + "</span></li>" +
                                "<li class='hr-line-dashed'></li>";

                        }else{

                            str += "<li><span class='glyphicon glyphicon-remove' style='color:red '></span>&nbsp;<strong>第 " + (i + 1) + " 题 、</strong>" + data[i].title_content + "</li>" +
                                "<li style='margin-left: 25px;'><strong>你的答案：</strong><span >" + partId + "."+partName+"</span></li>" +
                                "<li style='margin-left: 25px;color: #00BE67'><strong>正确答案：</strong><span>" + data[i].modelAnswer + "</span></li>" +
                                "<li class='hr-line-dashed'></li>";
                        }
                    }

                        $(".question_title").html(str);

                        //
                }
            });

        });


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
