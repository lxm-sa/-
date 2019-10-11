function aaa() {
    location.href="/testPage"
}

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
    	url:"/manage/test/queryPageTests",
    	height:tableHeight()-90,//高度调整
    	toolbar: '#toolbar',
    	striped: true, //是否显示行间隔色
    	dataField: "records",
    	pageNumber: 1, //初始化加载第一页，默认第一页
    	pagination:true,//是否分页
    	queryParamsType:'limit',
        queryParams: queryParams,
    	sidePagination:'server',
    	pageSize:10,//单页记录数
    	pageList:[10,20,30,50],//分页步进值
        smartDisplay: false,
    	showRefresh:true,//刷新按钮
    	showColumns:true,
    	clickToSelect: true,//是否启用点击选中行
    	toolbarAlign:'right',
    	buttonsAlign:'right',//按钮对齐方式指定工作栏
    	columns:[
        	{
        		title:'全选',
        		field:'select',
        		checkbox:true,
        		width:25,
        		align:'center',
        		valign:'middle'
        	},
        	{
        		title:'试卷编号',
        		field:'test_id',
        		visible:false
        	},
        	{
        		title:'试卷标题',
        		field:'test_title',
        		sortable:true
        	},
            {
                title:'考试科目',
                field:'sub_name',
                sortable:true
            },
        	{
        		title:'试卷总分',
        		field:'total_score',
        		sortable:true
        	},
            {
                title:'科目老师',
                field:'user_name',
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
                title:'出题规则',
                field:'test_rule',
                sortable:true
            },

        	{
        		title:'创建日期',
        		field:'create_time',
        		sortable:true
        	}
    	],
    	locale:'zh-CN',//中文支持,

    });

    /*
     * 用户管理首页事件
     */

		  //请求成功后生成增加用户页面表单内容
		  $('#addForm').bootstrapValidator({
              live: 'enabled',//enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
              excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件
              submitButtons: '#add_saveBtn',              //指定提交按钮
              message: '通用的验证失败消息',
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
		           fields: {
                       test_title: {
		                   validators: {
		                       notEmpty: {
		                           message: '试卷标题不能为空'
		                       }
		                   }
		               },
                       sub_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '请选择考试科目'
		                       }
		                   }
		               },
                       total_score: {
                           validators: {
                               notEmpty: {
                                   message: '必须设置试卷总分'
                               }
                           }
                       },
                       start_time: {
                           trigger:"focusout",
                           validators: {
                               notEmpty: {
                                   message: '请设置考试起始日期'
                               }
                           }
                       },
                       end_time: {
                           trigger:"focusout",
                           validators: {
                               notEmpty: {
                                   message: '请设置考试截止日期'
                               }
                           }
                       },
                       test_rule: {
                           validators: {
                               notEmpty: {
                                   message: '请选择出题规则'
                               }
                           }
                       },
                       title_count: {
                           validators: {
                               notEmpty: {
                                   message: '考题数量不能为空'
                               }
                           }
                       }
		           }
		     });
		    $('#editForm').bootstrapValidator({
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
                fields: {

		       	    test_title: {
                    validators: {
                        notEmpty: {
                            message: '试卷标题不能为空'
                        }
                    }
                },
                    sub_name: {
                        validators: {
                            notEmpty: {
                                message: '请选择考试科目'
                            }
                        }
                    },
                    total_score: {
                        validators: {
                            notEmpty: {
                                message: '必须设置试卷总分'
                            }
                        }
                    },
                    start_time: {
                        trigger:"focusout",
                        validators: {
                            notEmpty: {
                                message: '请设置考试起始日期'
                            }
                        }
                    },
                    end_time: {
                        validators: {
                            trigger:"focusout",
                            notEmpty: {
                                message: '请设置考试截止日期'
                            }
                        }
                    },
                    出题规则: {
                        validators: {
                            notEmpty: {
                                message: '请选择出题规则'
                            }
                        }
                    }
                }
		       });


    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });
        $('#datetimepicker2').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });
        $('#datetimepicker3').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });
        $('#datetimepicker4').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });


    });

    //请求服务数据时所传参数
   // var dataArr=$('#mytab').bootstrapTable('getSelections');
    function queryParams(params){
    	return{
                pageSize: params.limit,
                pageNumber:params.offset /params.limit+1
    	};
    }


    //查询按钮事件
    $('#search_btn').click(function(){
    	$('#mytab').bootstrapTable('refresh', {url: '/manage/parts/queryPagePartsInfoByParams'});
    });
    
    //增加按钮事件
    $('#btn_add').click(function(){

        $('.modal fade').css('display','block');
        $('.modal fade').addClass('animated slideInRight');
    });




    //修改按钮事件
    $('#btn_edit').click(function(){
        $("#btn_edit").bootstrapTable('destroy');
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_ID').val(dataArr[0].test_id);
		$('#edit_Name').val(dataArr[0].test_title);
		$('#edit_sub').val(dataArr[0].sub_name);
        $('#edit_teacher').val(dataArr[0].teacher_id);
        $('#edit_score').val(dataArr[0].total_score);
        $('#edit_testTime').val(dataArr[0].test_time);
        $('#edit_startTime').val(dataArr[0].start_time);
        $('#edit_endTime').val(dataArr[0].end_time);
        $('#edit_titleCount').val(dataArr[0].title_count);
        if (dataArr[0].test_rule == 0) {
            $("#editForm input[name=test_rule]:eq(0)").prop("checked",true);
            $("#editForm input[name=test_rule]:eq(1)").prop("checked",false);
        }
        else {
            $("#editForm input[name=test_rule]:eq(1)").prop("checked",true);
            $("#editForm input[name=test_rule]:eq(0)").prop("checked",false);
        }

    });


    //参考学员管理按钮事件
    $('#btn_stuManage').click(function(){
        $("#btn_stuManage").bootstrapTable('destroy');
        $("#parttab").bootstrapTable('destroy');
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.partBody').css('display','block');
        $('.partBody').addClass('animated slideInRight');
        $('#test_ID').val(dataArr[0].test_id);

        //生成学员数据
        $('#parttab').bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url:"/manage/test/queryUsersByParams",
            height:tableHeight()-130,//高度调整
            toolbar: '#tool',
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
            columns:[
                {
                    title:'全选',
                    field:'select',
                    checkbox:true,
                    width:25,
                    align:'center',
                    valign:'middle'
                },
                {
                    title:'学员编号',
                    field:'user_id',
                    visible:false
                },
                {
                    title:'学员名字',
                    field:'user_name',
                    sortable:true
                },
                {
                    title:'学员帐号',
                    field:'account_name',
                    sortable:true
                },
                {
                    title:'学员学校',
                    field:'school',
                    sortable:true
                },

                {
                    title:'手机号码',
                    field:'telphone',
                    sortable:true
                }
            ],
            locale:'zh-CN',//中文支持,

        });


        $('#btn_addPart').click(function () {
            $("#myparttab").bootstrapTable('destroy');

            $('#myparttab').bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url:"/manage/user/queryPageStudent",

                toolbar: '#ptool',
                striped: true, //是否显示行间隔色
                dataField: "records",
                pageNumber: 1, //初始化加载第一页，默认第一页
                pagination:true,//是否分页
                queryParamsType:'limit',
                queryParams:queryParams,
                sidePagination:'server',
                pageSize:10,//单页记录数
                pageList:[10,20,30,50],//分页步进值
                showRefresh:true,//刷新按钮
                showColumns:true,
                clickToSelect: true,//是否启用点击选中行
                toolbarAlign:'right',
                buttonsAlign:'right',//按钮对齐方式
                toolbar:'#ptool',//指定工作栏
                height:function(params){
                    return 35*params.total+200
                },//高度调整
                columns:[
                    {
                        title:'全选',
                        field:'select',
                        checkbox:true,
                        width:25,
                        align:'center',
                        valign:'middle'
                    },
                    {
                        title:'学员编号',
                        field:'user_id',
                        visible:false
                    },
                    {
                        title:'登录名',
                        field:'account_name',
                        sortable:true
                    },
                    {
                        title:'学员姓名',
                        field:'user_name',
                        sortable:true
                    },
                    {
                        title:'手机号',
                        field:'telphone'
                    },
                    {
                        title:'学校',
                        field:'school'
                    }
                ],
                locale:'zh-CN',//中文支持,
                onLoadSuccess:function (params) {
                    $('.fixed-table-body').attr('style','height:'+35*params.total+'px')
                },

            });

        })
        //生成用户数据

    /*//请求服务数据时所传参数
        function queryParams(param){
            return{
                pageSize: param.limit,
                pageIndex:param.pageNumber,
                part_name:$('#search_name').val()

            }
        }*/
        //请求服务数据时所传参数
        function queryParams(params){
            return{
                pageSize: params.limit,
                pageNumber:params.offset /params.limit+1,
                pageIndex:params.pageNumber,
                test_id:$('#test_ID').val(),
                user_name:$('#search_name').val(),
                telphone:$('#search_tel').val()
            }
        }
        //查询按钮事件
        $('#search_btn').click(function(){
            $('#myparttab').bootstrapTable('refresh', {url: '/manage/user/queryPageStudentInfoByParams'});
        });
        /*
         * 用户管理首页事件
         */

        //删除按钮与修改,重置按钮的出现与消失
        $('.bootstrap-table').change(function(){
            var dataArrp=$('#parttab .selected');

            if(dataArrp.length>=1){

                $('#btn_deletePart').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            }else{
                $('#btn_deletePart').addClass('fadeOutRight');
                setTimeout(function(){
                    $('#btn_deletePart').css('display','none');
                },400);
            }

        });

        //删除事件按钮
        $('#btn_deletePart').click(function(){
            var dataArrp=$('#parttab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定要删除该内容如吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){
                var ID=[];
                for(var i=0;i<dataArrp.length;i++){
                    ID[i]=dataArrp[i].user_id;
                }
              var testId =dataArr[0].test_id;
                $.post("/manage/test/deleteStuTestInfos",
                    {testId:testId,stuIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('删除成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/test/queryUsersByParams'});
                        }else{
                        }
                    },"json");
            })
        });

        //添加事件按钮,试卷添加参考人员
        $('#btn_padd').click(function(){
            var pdataArr=$('#myparttab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定添加吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){
                var ID=[];
                for(var i=0;i<pdataArr.length;i++){
                    ID[i]=pdataArr[i].user_id;
                }
                var testId =dataArr[0].test_id;
                $.post("/manage/test/createStuTestInfos",
                    {testId:testId,stuIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('添加成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                                $('#mypartModal').modal('hide');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/test/queryUsersByParams'});
                        }else{
                        }
                    },"json");
            })
        });





    });


    //考试题目管理按钮事件
    $('#btn_titleManage').click(function(){
        $("#btn_stuManage").bootstrapTable('destroy');
        $(function(){
            //初始化下拉框
            //动态加载
            $.ajax({
                type: 'get',
                url: '/manage/title/queryTitleTypeList',
                dataType: "json",
                success: function (data) {
                    //拼接下拉框
                    for(var i=0;i<data.length;i++){
                        $("#search_type").append("<option value='"+data[i].title_type_name+"'>"+data[i].title_type_name+"</option>");
                        //$("#sel_role").val(dataArr[i].role_name);
                    }

                }
            });

        });
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.testTitleBody').css('display','block');
        $('.testTitleBody').addClass('animated slideInRight');
        $('#test_ID').val(dataArr[0].test_id);

        //生成学员数据
        $('#testTitletab').bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url:"/manage/test/queryTitleByParams",
            height:tableHeight()-150,//高度调整
            toolbar: '#toolt',
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
            toolbar:'#toolt',//指定工作栏
            columns:[
                {
                    title:'全选',
                    field:'select',
                    checkbox:true,
                    width:25,
                    align:'center',
                    valign:'middle'
                },
                {
                    title:'题目编号',
                    field:'title_id',
                    align:'center',
                    valign:'middle',
                    sortable:true
                },
                {
                    title:'题目',
                    field:'title_content',
                    align:'center',
                    valign:'middle',
                    sortable:true
                }
            ],
            locale:'zh-CN',//中文支持,
        });



        //生成用户数据
        $('#title').click(function () {
            $("#myTitletab").bootstrapTable('destroy');
            $('#myTitletab').bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url:"/manage/title/queryPageTestTitleInfo",
                height:function (params) {
                    return params.limit*36+200
                },//高度调整
                toolbar: '#ttool',
                striped: true, //是否显示行间隔色
                dataField: "records",
                pageNumber: 1, //初始化加载第一页，默认第一页
                pagination:true,//是否分页
                queryParamsType:'limit',
                queryParams:queryParams,
                sidePagination:'server',
                pageSize:10,//单页记录数
                pageList:[10,20,30,50],//分页步进值
                showRefresh:true,//刷新按钮
                showColumns:true,
                clickToSelect: true,//是否启用点击选中行
                toolbarAlign:'right',
                buttonsAlign:'right',//按钮对齐方式
                toolbar:'#ttool',//指定工作栏
                columns:[
                    {
                        title:'全选',
                        field:'select',
                        checkbox:true,
                        width:25,
                        align:'center',
                        valign:'middle'
                    },
                    {
                        title:'题目编号',
                        field:'title_id',
                        align:'center',
                        valign:'middle',
                        visible:false
                    },
                    {
                        title:'题目',
                        field:'title_content',
                        align:'center',
                        valign:'middle',
                        sortable:true
                    },
                    {
                        title:'题型',
                        field:'title_type_name',
                        align:'center',
                        valign:'middle',
                        sortable:true
                    }
                ],
                locale:'zh-CN',//中文支持,
            });
            })

        /*//请求服务数据时所传参数
         function queryParams(param){
         return{
         pageSize: param.limit,
         pageIndex:param.pageNumber,
         part_name:$('#search_name').val()

         }
         }*/
        //请求服务数据时所传参数
        function queryParams(params){
            return{
                pageSize: params.limit,
                pageNumber:params.offset /params.limit+1,
                pageIndex:params.pageNumber,
                test_id:$('#test_ID').val(),
                title_content:$('#search_title').val(),
                title_type_name:$('#search_type').val()
            }
        }
        //查询按钮事件
        $('#search_tbtn').click(function(){
            $('#myTitletab').bootstrapTable('refresh', {url: '/manage/title/queryTitleListByParams'});
        });
        /*
         * 用户管理首页事件
         */

        //删除按钮与修改,重置按钮的出现与消失
        $('.bootstrap-table').change(function(){
            var dataArrt=$('#testTitletab .selected');

            if(dataArrt.length>=1){

                $('#btn_deleteTitle').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            }else{
                $('#btn_deleteTitle').addClass('fadeOutRight');
                setTimeout(function(){
                    $('#btn_deleteTitle').css('display','none');
                },400);
            }

        });

        //删除事件按钮
        $('#btn_deleteTitle').click(function(){
            var dataArrt=$('#testTitletab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定要删除该内容如吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){
                var ID=[];
                for(var i=0;i<dataArrt.length;i++){
                    ID[i]=dataArrt[i].title_id;
                }
                var testId =dataArr[0].test_id;
                $.post("/manage/test/deleteTestTitleInfos",
                    {testId:testId,titleIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('删除成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#testTitleBody').bootstrapTable('refresh', {url: '/manage/test/queryTitleByParams'});
                        }else{
                        }
                    },"json");
            })
        });

        //添加事件按钮,试卷添加参考人员
        $('#btn_tadd').click(function(){
            var tdataArr=$('#myTitletab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定添加吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){
                var ID=[];
                for(var i=0;i<tdataArr.length;i++){
                    ID[i]=tdataArr[i].title_id;
                }
                var testId =dataArr[0].test_id;
                $.post("/manage/test/createTestTitleInfos",
                    {testId:testId,titleIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('添加成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                                $('#titleModal').modal('hide');
                            });
                            $('#testTitleBody').bootstrapTable('refresh', {url: '/manage/test/queryTitleByParams'});
                        }else{
                        }
                    },"json");
            })
        });





    });



    //删除按钮与修改,重置按钮的出现与消失

    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
        var dataArr=$('#mytab .selected');
        var dataArrp=$('#parttab .selected');
        if(dataArr.length==1){
            $('#btn_edit').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_check').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_stuManage').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_titleManage').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
        }else{
            $('#btn_edit').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_edit').css('display','none');
            },400);
            $('#btn_check').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_check').css('display','none');
            },400);
            $('#btn_stuManage').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_stuManage').css('display','none');
            },400);
            $('#btn_titleManage').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_titleManage').css('display','none');
            },400);
        }
        if(dataArr.length>=1){

            $('#btn_delete').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');

        }else{
            $('#btn_delete').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_delete').css('display','none');
            },400);

        }

    });


//部件管理页面回退按钮事件
    $('#backBtn').click(function(){
        $('.partBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.partBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.tableBody').css('display','block').addClass('animated slideInRight');
        window.location.reload();
    });

    $('#testbackBtn').click(function(){
        $('.testTitleBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.testTitleBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.tableBody').css('display','block').addClass('animated slideInRight');
        window.location.reload();
    });
    /**新增****/
    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/subject/queryPageSubject',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#add_sub").append("<option value='"+data[i].sub_id+"'>"+data[i].sub_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }


            }
        });

    });

    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/user/queryTeacherList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#add_teacher").append("<option value='"+data[i].user_id+"'>"+data[i].user_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }


            }
        });

    });




  /****************修改************/
    $(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/subject/queryPageSubject',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#edit_sub").append("<option value='"+data[i].sub_name+"'>"+data[i].sub_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }





            }
        });

    });
    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/user/queryTeacherList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#edit_teacher").append("<option value='"+data[i].user_id+"'>"+data[i].user_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }


            }
        });

    });

    /*
     * 用户管理增加用户页面所有事件
    */
    //增加页面表单验证   
    // Validate the form manually
    $('#add_saveBtn').click(function() {

       //点击保存时触发表单验证
       $('#addForm').bootstrapValidator('validate');
       //如果表单验证正确，则请求后台添加用户
       if($("#addForm").data('bootstrapValidator').isValid()){
    	   var _info = $('#addForm').serialize();

    	  $.post(
                "/manage/test/createTestInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
                        $('#myModal').modal('hide');
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageTests'});
				    	$('#addForm').data('bootstrapValidator').resetForm(true);
				    	//隐藏修改与删除按钮
						$('#btn_delete').css('display','none');
						$('#btn_edit').css('display','none');

					}
					//否则
					else{
                        console.log("添加失败")
					}
				},"json"
			) 
       }
    });
    //增加页面返回按钮事件
    $('#add_backBtn').click(function() {
    	$('.addBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.addBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
    	$('.tableBody').css('display','block').addClass('animated slideInRight');  
    	$('#addForm').data('bootstrapValidator').resetForm(true);
    });
    /*
     * 用户管理修改用户页面所有事件
    */
    //修改页面回退按钮事件
    $('#edit_backBtn').click(function(){
    	$('.changeBody').addClass('animated slideOutLeft');
    	setTimeout(function(){
			$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
    	$('#editForm').data('bootstrapValidator').resetForm(true);
        window.location.reload();
    });

    //修改页面保存按钮事件
    $('#edit_saveBtn').click(function(){

    	$('#editForm').bootstrapValidator('validate');
    	if($("#editForm").data('bootstrapValidator').isValid()){
    		 $.post("/manage/test/updateTestInfo",
				$('#editForm').serialize(),
				function(data){
					if(data){
						//隐藏修改与删除按钮
						$('#btn_delete').css('display','none');
						$('#btn_edit').css('display','none');
						//回退到人员管理主页
						$('.changeBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
				    	//刷新人员管理主页
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageTests'});
				    	//修改页面表单重置
				    	$('#editForm').data('bootstrapValidator').resetForm(true);
					}else{
					}
			    }
    		 ) 
    	}
    });



    //删除事件按钮
    $('#btn_delete').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.popup_de .show_msg').text('确定要删除该内容如吗?');
    	$('.popup_de').addClass('bbox');
    	$('.popup_de .btn_submit').one('click',function(){
    		var ID=[];
        	for(var i=0;i<dataArr.length;i++){
        		ID[i]=dataArr[i].test_id;
        	}

        	$.post("/manage/test/deleteTestInfos",
                {testIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageTests'});
        		}else{
        		}
        	},"json");
    	})
    });




    //查看按钮事件
    $('#btn_check').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('#myTestModal').css('display','block');
        $('#myTestModal').addClass('animated slideInRight');
        document.getElementById("testTitle").innerText=dataArr[0].test_title;

        document.getElementById("subName").innerText=dataArr[0].sub_name;

        document.getElementById("testTime").innerText=dataArr[0].test_time +" 分钟";

        document.getElementById("totalScore").innerText=dataArr[0].total_score +" 分";


        $(function(){
            //初始化下拉框
            //动态加载
            $.ajax({
                type: 'get',
                url: '/manage/test/queryTitleListById',
                data: {testId:dataArr[0].test_id,testRule:dataArr[0].test_rule},
                dataType: "json",
                success: function (data) {
                    //拼接div


                    var title="";
                    var answer="";
                    var n = 0;
                    var str ="";
                    for(var i=0;i<data.length;i++){
                         n = i+1;
                        title= "第"+n+"题：&nbsp;"+data[i].title_content;
                        answer= "&nbsp;&nbsp;&nbsp;&nbsp;答案：&nbsp;"+data[i].titleAnswer ;
                        str += "<div style='width: 100%'>"+ title + "</div>" +
                            "<div style='width: 100%;margin-top:20px;font-weight: normal;color: #00BE67'>"+answer+"</div>"+"<div class='hr-line-dashed' style='margin-top: 20px'></div>";
                       //
                    }

                    $("#titleContet").html(str);
                //这一步不要忘记 不然下拉框没有数据
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
    return $(window).height();
}
