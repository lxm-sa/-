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
    	url:"/manage/class/queryPageClass",
    	height:tableHeight()-70,//高度调整
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
    	toolbar:'#toolbar',//指定工作栏
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
        		title:'班级编号',
        		field:'class_id',
                sortable:true
        	},
        	{
        		title:'班级名称',
        		field:'class_name',
        		sortable:true
        	},
            {
                title:'班级老师',
                field:'teach_name',
                sortable:true
            },
            {
                title:'班级状态',
                field:'status',
                align:'center',
                formatter:operateFormatter
            }
    	],
    	locale:'zh-CN',//中文支持,
    });


    /*
     * 用户管理首页事件
     */

		  //请求成功后生成增加用户页面表单内容
		  $('#addForm').bootstrapValidator({
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
		           fields: {
                       class_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '班级名称不能为空'
		                       }
		                   }
		               },
                       teach_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '老师名称不能为空'
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
                    class_name: {
                        validators: {
                            notEmpty: {
                                message: '班级名称不能为空'
                            }
                        }
                    },
                    teach_name: {
                        validators: {
                            notEmpty: {
                                message: '老师名称不能为空'
                            }
                        }
                    }
                }
		       });


    //请求服务数据时所传参数
   // var dataArr=$('#mytab').bootstrapTable('getSelections');
    function queryParams(params){
    	return{
            pageSize: params.limit,
            pageNumber:params.offset /params.limit+1,
    		pageIndex:params.pageNumber
    	}
    }
    function operateFormatter(value,row,index){
        if(value==0){
            return '<button  type="button" class="btn btn-primary btn-lg"  disabled="disabled">授课中</button>'

        }
        if(value==1){
            return '<button  type="button" class="btn btn-default btn-lg" style="background-color: #7399b8;color: white" disabled="disabled">已结业</button>'

        }
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


    /**新增****/
    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/user/queryTeacherList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框

                var userId = $("#userId").data('id');
                var userNmae = $("#userNmae").data('id');
                var roleId = $("#roleId").data('id');
                console.log(userId +'+'+userNmae+"+"+roleId);
                if(roleId==10003){
                    $("#add_teacher").append("<option value='"+userId+"'>"+userNmae+"</option>");
                }else{
                    for(var i=0;i<data.length;i++){
                        $("#add_teacher").append("<option value='"+data[i].user_id+"'>"+data[i].user_name+"</option>");

                    }
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
            url: '/manage/user/queryTeacherList',
            dataType: "json",
            success: function (data) {
                var userId = $("#userId").data('id');
                var userNmae = $("#userNmae").data('id');
                var roleId = $("#roleId").data('id');
                if(roleId==10003){
                    $("#edit_teacher").append("<option value='"+userId+"'>"+userNmae+"</option>");
                }else{
                    //拼接下拉框
                    for(var i=0;i<data.length;i++){

                        $("#edit_teacher").append("<option value='"+data[i].user_id+"'>"+data[i].user_name+"</option>");

                    }
                }

            }
        });

    });



    //修改按钮事件
    $('#btn_edit').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_ID').val(dataArr[0].class_id);
		$('#edit_Name').val(dataArr[0].class_name);
		$('#edit_teacher').val(dataArr[0].teach_id);


    });



    //内容部件管理按钮事件
    $('#btn_partManage').click(function(){
        $("#btn_partManage").bootstrapTable('destroy');
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.partBody').css('display','block');
        $('.partBody').addClass('animated slideInRight');
        $('#class_ID').val(dataArr[0].class_id);

        document.getElementById("classNmae").innerText='【'+dataArr[0].class_name +'】—学员管理';
        //生成部件数据
        $('#parttab').bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url:"/manage/class/queryUsersByClassId",
            height:tableHeight()-100,//高度调整
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
            toolbar:'#tool',//指定工作栏
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




        //生成用户数据
        $('#myparttab').bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url:"/manage/user/queryClassPageStudent",
            height:function (params) {
                return params.limit*36+200
            },//高度调整
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
        });

        //请求服务数据时所传参数
        function queryParams(params){
            return{
                pageSize: params.limit,
                pageNumber:params.offset /params.limit+1,
                pageIndex:params.pageNumber,
                class_id:$('#class_ID').val(),
                user_name:$('#search_name').val(),
                telphone:$('#search_tel').val()
            }
        }
        //查询按钮事件
        $('#search_btn').click(function(){
            $('#myparttab').bootstrapTable('refresh', {url: '/manage/user/queryPageClassStudentInfoByParams'});
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
                var classId =dataArr[0].class_id;
                $.post("/manage/class/deleteClassStuInfos",
                    {classId:classId,stuIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('删除成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/class/queryUsersByClassId'});
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
                var classId =dataArr[0].class_id;
                $.post("/manage/class/createClassStuInfos",
                    {classId:classId,stuIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('添加成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                                $('#mypartModal').modal('hide');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/class/queryUsersByClassId'});
                        }else{
                        }
                    },"json");
            })
        });





    });

    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
        var dataArr=$('#mytab .selected');
        var dataArrp=$('#parttab .selected');
        if(dataArr.length==1){
            $('#btn_edit').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_check').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_partManage').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_exam').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');

        }else{
            $('#btn_edit').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_edit').css('display','none');
            },400);
            $('#btn_check').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_check').css('display','none');
            },400);
            $('#btn_partManage').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_partManage').css('display','none');
            },400);
            $('#btn_exam').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_exam').css('display','none');
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

    //审核事件按钮
    $('#btn_exam').on('click', function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        layer.confirm('当前班级状态是？', {
                btn: ['正在授课', '已结业']
            },
            function(){
                layer.closeAll('dialog');
                $.post("/manage/class/updateClassStatusById",
                    {class_id:dataArr[0].class_id,status:0},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('正在授课！');

                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#mytab').bootstrapTable('refresh', {url: '/manage/class/queryPageClass'});
                        }else{
                        }
                    },"json");
            },
            function(){
                $.post("/manage/class/updateClassStatusById",
                    {class_id:dataArr[0].class_id,status:1},

                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('已经结业！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#mytab').bootstrapTable('refresh', {url: '/manage/class/queryPageClass'});
                        }else{
                        }
                    },"json");
            }
        )
    });



//部件管理页面回退按钮事件
    $('#backBtn').click(function(){
        $('.partBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.partBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.tableBody').css('display','block').addClass('animated slideInRight');
        //$('#editForm').data('bootstrapValidator').resetForm(true);
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
                "/manage/class/createClassInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
                        $('#myModal').modal('hide');
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/class/queryPageClass'});
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
    });

    //修改页面保存按钮事件
    $('#edit_saveBtn').click(function(){

    	$('#editForm').bootstrapValidator('validate');
    	if($("#editForm").data('bootstrapValidator').isValid()){
    		 $.post("/manage/class/updateClassInfo",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/class/queryPageClass'});
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
        		ID[i]=dataArr[i].class_id;
        	}

        	$.post("/manage/class/deleteClassInfos",
                {classIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/class/queryPageClass'});
        		}else{
        		}
        	},"json");
    	})
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
