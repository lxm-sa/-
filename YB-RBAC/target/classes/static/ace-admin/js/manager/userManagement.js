
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
    	url:"/manage/user/queryPageUser",
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
        		title:'用户编号',
        		field:'user_id',
        		visible:false
        	},
        	{
        		title:'登录名',
        		field:'account_name',
        		sortable:true
        	},
        	{
        		title:'姓名',
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
        	},
        	{
        		title:'注册日期',
        		field:'create_time'
        	},
        	{
        		title:'登录权限',
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
		               account_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '登录名不能为空'
		                       },
		                       stringLength:{
		               			min:5,
		               			max:15,
		               			message:'姓名为5-10位'
		               		}
		                   }
		               },
		               user_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '姓名不能为空'
		                       },
		               		stringLength:{
		               			min:2,
		               			max:10,
		               			message:'姓名为2-10位'
		               		}
		                   }
		               },
		               'RoleID[]': {
	                        validators: {
	                            notEmpty: {
	                                message: '角色至少选择一种'
	                            }
	                        }
	                    },

		               password:{
		               	validators:{
		               		notEmpty:{
		               			message:'密码不能为空'
		               		},
		                       stringLength:{
		                       	min:6,
		                       	max:32,
		                       	message:'密码为6-32位'
		                       }
		               	}
		               	
		               },
                       identity_id: {
                           validators: {
                               notEmpty: {
                                   message: '身份证号不能为空'
                               },
                               regexp: {
                                   regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                                   message: '请填写正确的身份证号'
                               }
                           }
                       },
		               telphone: {
		                   validators: {
		                       notEmpty: {
		                           message: '手机号不能为空'
		                       },
		                       stringLength: {
		                           min: 11,
		                           max: 11,
		                           message: '手机号必须为11位'
		                       },
		                       regexp: {
		                           regexp: /^1(3|4|5|7|8)\d{9}$/,
		                           message: '请填写正确的手机号'
		                       }
		                   }
		               },
		               school: {
		                   validators: {
		                   	notEmpty:{
		                   		message:'学校不能为空'
		                   	}

		                   }
		               },
		               status: {
		                   validators: {
		                       notEmpty: {
		                           message: '状态不能为空'
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
		               account_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '登录名不能为空'
		                       },
		                       stringLength:{
		               			min:5,
		               			max:10,
		               			message:'登录名为5-10位'
		               		}
		                   }
		               },
		               user_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '姓名不能为空'
		                       },
		               		stringLength:{
		               			min:2,
		               			max:10,
		               			message:'姓名为2-10位'
		               		}
		                   }
		               },
		               role_id: {
	                        validators: {
	                            notEmpty: {
	                                message: '角色不能为空'
	                            }
	                        }
	                    },
                       identity_id: {
                           validators: {
                               notEmpty: {
                                   message: '身份证号不能为空'
                               },
                               regexp: {
                                   regexp: /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/,
                                   message: '请填写正确的身份证号'
                               }
                           }
                       },

		               telphone: {
		                   validators: {
		                       notEmpty: {
		                           message: '手机号不能为空'
		                       },
		                       stringLength: {
		                           min: 11,
		                           max: 11,
		                           message: '手机号必须为11位'
		                       },
		                       regexp: {
		                           regexp: /^1(3|4|5|7|8)\d{9}$/,
		                           message: '请填写正确的手机号'
		                       }
		                   }
		               },
		               school: {
		                   validators: {
		                   	notEmpty:{
		                   		message:'学校不能为空'
		                   	}
		                   }
		               },
		               status: {
		                   validators: {
		                       notEmpty: {
		                           message: '状态不能为空'
		                       }
		                   }
		               }
		           }
		       });



      $(function () {
          $.ajax({
              type: 'get',
              url: '/manage/role/queryPageRole',
              dataType: "json",
              success: function (data) {
                  //拼接下拉框
                  var str ="";
                  var b=$('#aaa').data('id');;
                  for(var i=0;i<data.length;i++){
                      if((data[i].role_id != 10002)||(b==10002)){
                          str += "<option value='"+data[i].role_id+"'>"+data[i].role_name+"</option>"
                      }

                  }
                  $("#sel_role").append(str);

              }
          });
      })
        //初始化下拉框
        //动态加载





    $(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/role/queryPageRole',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                var str ="";
                for(var i=0;i<data.length;i++){
                    if(data[i].role_id != 10002){
                        str += "<option value='"+data[i].role_name+"'>"+data[i].role_name+"</option>"
                    }

                }
                $("#edit_role").append(str);

            }
        });

    });


    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-cn')
        });
        $('#datetimepicker2').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-cn')
        });
    });


    function operateFormatter(value,row,index){
        if(value==0){
            return '<button  type="button" class="btn btn-danger btn-lg active">禁止</button>'

        }else if(value==1){
            return ' <button  type="button" class="btn btn-primary btn-lg active">通过</button>'
        }else{
            return '<button type="button" class="btn btn-default btn-lg active" style=" background-color: #6A8CA8">待审</button>'
        }
    }



//审核事件按钮
    $('#btn_exam').click( function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        layer.confirm('是否允许该用户登录该系统？', {
                btn: ['通过', '不通过']
			},
       function(){
           layer.closeAll('dialog');
            $.post("/manage/user/updateUserStatusById",
                {user_id:dataArr[0].user_id,status:1},
                function(data){
                    if(data){
                        $('.popup_de .show_msg').text('审核通过！');

                        $('.popup_de .btn_cancel').css('display','none');
                        $('.popup_de').addClass('bbox');
                        $('.popup_de .btn_submit').one('click',function(){
                            $('.popup_de').removeClass('bbox');
                        });
                        $('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
                    }else{
                    }
                },"json");
        },
            function(){
                    $.post("/manage/user/updateUserStatusById",
                        {user_id:dataArr[0].user_id,status:0},

                        function(data){
                            if(data){
                                $('.popup_de .show_msg').text('禁止登录！');
                                $('.popup_de .btn_cancel').css('display','none');
                                $('.popup_de').addClass('bbox');
                                $('.popup_de .btn_submit').one('click',function(){
                                    $('.popup_de').removeClass('bbox');
                                });
                                $('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
                            }else{
                            }
                        },"json");
            }
		)
    });

    //请求服务数据时所传参数
    function queryParams(params){
    	return{
            pageSize: params.limit,
            pageNumber:params.offset /params.limit+1,
    		pageIndex:params.pageNumber,
            user_name:$('#search_name').val(),
    		telphone:$('#search_tel').val()
    	}
    }
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
            $('#btn_resetPsw').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
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
            $('#btn_resetPsw').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_resetPsw').css('display','none');
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
    //修改按钮事件
    $('#btn_edit').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_ID').val(dataArr[0].user_id);
		$('#edit_LoginName').val(dataArr[0].account_name);
		$('#edit_Name').val(dataArr[0].user_name);
        $('#edit_role').val(dataArr[0].role_name);
		$('#edit_Tel').val(dataArr[0].telphone);
        $('#edit_identityId').val(dataArr[0].identity_id);
        $('#edit_hometowm').val(dataArr[0].hometowm);
        $('#edit_birthday').val(dataArr[0].birthday);
        $('#edit_school').val(dataArr[0].school);


        if (dataArr[0].sex == 0) {
            $("#editForm input[name=sex]:eq(0)").prop("checked",true);
            $("#editForm input[name=sex]:eq(1)").prop("checked",false);
        }
        else {
            $("#editForm input[name=sex]:eq(1)").prop("checked",true);
            $("#editForm input[name=sex]:eq(0)").prop("checked",false);
        }
		if(dataArr[0].status==1){
			$("#editForm input[name=islogin]:eq(0)").prop("checked",true);
			$("#editForm input[name=islogin]:eq(1)").prop("checked",false);
		}
		else if(dataArr[0].status==2){
			$("#editForm input[name=islogin]:eq(1)").prop("checked",true);
			$("#editForm input[name=islogin]:eq(0)").prop("checked",false);
		}
		//先清空角色复选框
	    $('#editForm .edit input').prop('checked',false);

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
    $('#add_saveBtn').click(function() {

       //点击保存时触发表单验证
       $('#addForm').bootstrapValidator('validate');
       //如果表单验证正确，则请求后台添加用户
       if($("#addForm").data('bootstrapValidator').isValid()){
    	   var _info = $('#addForm').serialize();

    	  $.post(

                "/manage/user/createUserInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
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
    		 $.post("/manage/user/updateUserById",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
				    	//修改页面表单重置
				    	$('#editForm').data('bootstrapValidator').resetForm(true);
					}else{
					}
			    }
    		 ) 
    	}
    });



//重置密码事件按钮
    $('#btn_resetPsw').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.popup_de .show_msg').text('确定要重置该用户密码吗?');
        $('.popup_de').addClass('bbox');
        $('.popup_de .btn_submit').one('click',function(){

            $.post("/manage/user/resetPassword",
                {user_id:dataArr[0].user_id},
                function(data){
                    if(data){
                        $('.popup_de .show_msg').text('密码重置成功！密码为【123456】');
                        $('.popup_de .btn_cancel').css('display','none');
                        $('.popup_de').addClass('bbox');
                        $('.popup_de .btn_submit').one('click',function(){
                            $('.popup_de').removeClass('bbox');
                        });
                        $('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
                    }else{
                    }
                },"json");
        })
    });

    //删除事件按钮
    $('#btn_delete').click(function(){
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.popup_de .show_msg').text('确定要删除该用户吗?');
    	$('.popup_de').addClass('bbox');
    	$('.popup_de .btn_submit').one('click',function(){
    		var ID=[];
        	for(var i=0;i<dataArr.length;i++){
        		ID[i]=dataArr[i].user_id;
        	}

        	$.post("/manage/user/deleteUserInfos",
                {userIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
        		}else{
        		}
        	},"json");
    	})
    });

    $('#saveButton').click(function(){
        var filedata = $("input[name=upfil]")[0].files[0];

        /*var filePath = filedata.filePath();//此处需验证，如果读不到文件路径，请自行去查找获取绝对路径方法，在这里不累赘了
       var postdata = { "filePath": filePath };*/
        if (null==filedata) {
            $.messager.alert("系统提示", "未选择导入文件！", "warning");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/manage/user/importExcel",
            data: filedata,
            contentType: false,
            processData: false,//数据格式为json格式
            success: function (data) {

                alert("导入成功", " ");
                $('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
            },
            error: function (error) {
                $.messager.alert(JSON.stringify(error));
                $('#mytab').bootstrapTable('refresh', {url: '/manage/user/queryPageUser'});
            }
        })
    });
    $('#downloadExcel').click(function(){
        var url = "/manage/user/downloadExcel";

        window.location.href = url;

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
