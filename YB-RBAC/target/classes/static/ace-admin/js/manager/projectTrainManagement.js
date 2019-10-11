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
    	url:"/manage/projectTrain/queryPageProjectTrain",
    	height:tableHeight()-70,//高度调整
    	toolbar: '#toolbar',
    	striped: true, //是否显示行间隔色
    	dataField: "records",
        pagination:true,//是否分页
        pageNumber: 1, //初始化加载第一页，默认第一页
        sortable: true,                     //是否启用排序
        sortOrder: "asc",
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
        		title:'项目编号',
        		field:'pro_id',
        		visible:false
        	},
        	{
        		title:'项目名称',
        		field:'pro_name',
        		sortable:true
        	},
        	{
        		title:'科目',
        		field:'sub_name',
        		sortable:true
        	},
        	{
        		title:'说明',
        		field:'remark'
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
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
		           fields: {
                       pro_name: {
                           validators: {
                               notEmpty: {
                                   message: '项目名不能为空'
                               }

                           }
                       },

                       'sub_id[]': {
                           validators: {
                               notEmpty: {
                                   message: '角色至少选择一种'
                               }
                           }
                       },

                       remark: {
                           validators: {
                               notEmpty: {
                                   message: '密码不能为空'
                               }
                           }

                       },
                   }
		     });
		    $('#editForm').bootstrapValidator({
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
                fields: {
                    pro_name: {
                        validators: {
                            notEmpty: {
                                message: '项目名不能为空'
                            }

                        }
                    },

                    'sub_id[]': {
                        validators: {
                            notEmpty: {
                                message: '角色至少选择一种'
                            }
                        }
                    },

                    remark: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            }
                        }

                    }
                }
		       });

//请求服务数据时所传参数
    function queryParams(params){
        return{
            pageSize: params.limit,
            pageNumber:params.offset /params.limit+1,
            pageIndex:params.pageNumber

        }
    }

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
                    $("#sel_sub").append("<option value='"+data[i].sub_id+"'>"+data[i].sub_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }
                //这一步不要忘记 不然下拉框没有数据
                $("#sel_sub").selectpicker("refresh")

            }
        });

    });

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

                //这一步不要忘记 不然下拉框没有数据
                $("#edit_sub").selectpicker("refresh")



            }
        });

    });




    //查询按钮事件
    $('#search_btn').click(function(){
    	$('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUserInfoByParams'});
    });
    
    //增加按钮事件
    $('#btn_add').click(function(){
		$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.addBody').css('display','block');
		$('.addBody').addClass('animated slideInRight');
    });
    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
    	var dataArr=$('#mytab .selected');
    	if(dataArr.length==1){
    		$('#btn_edit').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
            $('#btn_check').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');

    	}else{
    		$('#btn_edit').addClass('fadeOutRight');
    		setTimeout(function(){
    			$('#btn_edit').css('display','none');
    		},400);
            $('#btn_check').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_check').css('display','none');
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
    //修改按钮事件
    $('#btn_edit').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_ID').val(dataArr[0].pro_id);
		$('#edit_Name').val(dataArr[0].pro_name);
		$('#edit_remark').val(dataArr[0].remark);
        $('#edit_sub').val(dataArr[0].sub_name);

        //$('#edit_islogin').val(dataArr[0].islogin);

		//先清空角色复选框
	    $('#editForm .edit input').prop('checked',false);
		//获取用户角色
		$.post('../index.php/admin/Index/getUserById',
				{ID:dataArr[0].ID},
				function(data){
				   var roleIDArr=data.res.user.RoleID;
                   //将对应用户的角色列表显示到对应的修改页
                   for(var i=0;i<roleIDArr.length;i++){
                	   for(var j=0;j<$('#editForm .edit input').length;j++){
                		   if(roleIDArr[i]==$('#editForm .edit input:eq('+j+')').val()){
                			   $('#editForm .edit input:eq('+j+')').prop('checked',true);
                		   }
                	   }
                   }
		        }
		);
    });


    //查看按钮事件
    $('#btn_check').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.modal fade').css('display','block');
       $('.modal fade').addClass('animated slideInRight');
        $('#check_ID').val(dataArr[0].user_id);
        $('#check_LoginName').val(dataArr[0].account_name);
        $('#check_Name').val(dataArr[0].user_name);
        $('#check_roleNmae').val(dataArr[0].role_name);
        $('#check_Tel').val(dataArr[0].telphone);
        $('#check_identity').val(dataArr[0].identity_id);
        $('#check_sex').val(dataArr[0].sex);
        //$('#check_sex1').val(dataArr[0].sex);

        $('#check_hometowm').val(dataArr[0].hometowm);
        $('#check_birthday').val(dataArr[0].birthday);
        $('#check_school').val(dataArr[0].school);
        $('#check_islogin').val(dataArr[0].islogin);

        if (dataArr[0].sex == 0) {
            $("#checkForm input[name=sex]:eq(0)").prop("checked",true);
            $("#checkForm input[name=sex]:eq(1)").prop("checked",false);
        }
        else {
            $("#checkForm input[name=sex]:eq(1)").prop("checked",true);
            $("#checkForm input[name=sex]:eq(0)").prop("checked",false);
        }


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

                "/manage/projectTrain/createProjectTrainInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/projectTrain/queryPageProjectTrain'});
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
    		 $.post("/manage/projectTrain/updateProjectTrainInfoById",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/projectTrain/queryPageProjectTrain'});
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
    	$('.popup_de .show_msg').text('确定要删除该用户吗?');
    	$('.popup_de').addClass('bbox');
    	$('.popup_de .btn_submit').one('click',function(){
    		var ID=[];
        	for(var i=0;i<dataArr.length;i++){
        		ID[i]=dataArr[i].pro_id;
        	}

        	$.post("/manage/projectTrain/deleteProjectTrainInfos",
                {proIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/projectTrain/queryPageProjectTrain'});
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
