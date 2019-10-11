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
    	url:"/manage/title/queryPageTitles",
    	height:tableHeight()-200,//高度调整
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
        	},

        	{
        		title:'创建日期',
        		field:'create_time',
                align:'center',
                valign:'middle',
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
                       title_content: {
		                   validators: {
		                       notEmpty: {
		                           message: '题目内容不能为空'
		                       }
		                   }
		               },
                       title_type_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '请选择题型'
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
                    title_content: {
                        validators: {
                            notEmpty: {
                                message: '题目内容不能为空'
                            }
                        }
                    },
                    title_type_name: {
                        validators: {
                            notEmpty: {
                                message: '请选择题型'
                            }
                        }
                    }
                }
		       });


		//创建时
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
                    $("#sel_type").append("<option value='"+data[i].title_type_id+"'>"+data[i].title_type_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }
                //这一步不要忘记 不然下拉框没有数据

            }
        });

    });

    //修改时
    $(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/title/queryTitleTypeList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#edit_type").append("<option value='"+data[i].title_type_name+"'>"+data[i].title_type_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }

                //这一步不要忘记 不然下拉框没有数据




            }
        });

    });

	//查找时
    $(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
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

                //这一步不要忘记 不然下拉框没有数据

            }
        });

    });

    //请求服务数据时所传参数
    function queryParams(params){
    	return{
            pageSize: params.limit,
            pageNumber:params.offset /params.limit+1,
    		pageIndex:params.pageNumber,
            title_content:$('#search_title').val(),
            title_type_name:$('#search_type').val()
    	}
    }
    //查询按钮事件
    $('#search_btn').click(function(){
    	$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryTitleListByParams'});
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
		$('#edit_ID').val(dataArr[0].title_id);
		$('#edit_content').val(dataArr[0].title_content);
		$('#edit_type').val(dataArr[0].title_type_name);

    });


    //查看按钮事件
    $('#btn_check').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.modal fade').css('display','block');
       $('.modal fade').addClass('animated slideInRight');
        $('#check_ID').val(dataArr[0].title_id);
        $('#check_content').val(dataArr[0].title_content);
        $('#check_typeNmae').val(dataArr[0].title_type_name);



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

                "/manage/title/createTitleInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageTitles'});
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
    		 $.post("/manage/title/updateTitleInfo",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageTitles'});
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
        		ID[i]=dataArr[i].title_id;
        	}

        	$.post("/manage/title/deleteTitleInfos",
                {titleIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageTitles'});
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
