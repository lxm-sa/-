

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
    	url:"/manage/title/queryPageAnswerInfo",
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
        		title:'答案编号',
        		field:'answer_id',
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
            },

            {
                title:'答案部件',
                field:'part_name',
                align:'center',
                valign:'middle',
                sortable:true
            },
        	{
        		title:'答案部件编号',
        		field:'part_id',
                align:'center',
                valign:'middle',
        		sortable:true
        	},
        	{
        		title:'答案说明',
        		field:'remark',
        		sortable:true
        	},
            {
                title:'答案创建时间',
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
                live: 'enabled',
		           fields: {
                       title_id: {

                           trigger:' mousemove',
		                   validators: {
		                       notEmpty: {
		                           message: '请选择答案对应的题目'
		                       }
		                   }
		               },
                       partId: {
                           trigger:"mousemove",
		                   validators: {
		                       notEmpty: {
		                           message: '请选择答案对应的部件'
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
                    title_id: {
                        trigger:"mousemove",
                        validators: {
                            notEmpty: {
                                message: '请选择答案对应的题目'
                            }
                        }
                    },
                    partId: {
                        trigger:"mousemove",
                        validators: {
                            notEmpty: {
                                message: '请选择答案对应的部件'
                            }
                        }
                    }
                }
		       });

    //生成部件数据
    $('#myparttab').bootstrapTable({
        method: 'get',
        contentType: "application/x-www-form-urlencoded",
        url:"/manage/parts/queryPageParts",
        height:tableHeight(),//高度调整
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
                title:'单选',
                field:'select',
                checkbox:true,

                width:25,
                align:'center',
                valign:'middle'
            },
            {
                title:'部件编号',
                field:'part_id',
                visible:false
            },
            {
                title:'部件名称',
                field:'part_name',
                sortable:true
            },
            {
                title:'部件位置',
                field:'part_position',
                sortable:true
            },
            {
                title:'规格型号',
                field:'part_spec',
                sortable:true
            }
        ],
        locale:'zh-CN',//中文支持,
    });

    //生成题目数据
    $('#mytitletab').bootstrapTable({
        method: 'get',
        contentType: "application/x-www-form-urlencoded",
        url:"/manage/title/queryPageTitles",
        height:tableHeight(),//高度调整
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
        singleSelect : true,
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
                field:'user_id',
                visible:false
            },
            {
                title:'题目',
                field:'title_content',
                sortable:true
            },
            {
                title:'题型',
                field:'title_type_name',
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

    //请求服务数据时所传参数
    function queryParams(params){
        return{
            pageSize: params.limit,
            pageNumber:params.offset /params.limit+1,
            pageIndex:params.pageNumber,
            part_name:$('#search_name').val(),
            title_content:$('#search_title').val(),
            title_type_name:$('#search_type').val()
        }
    }

    //查询按钮事件
    $('#search_btn').click(function(){
        $('#myparttab').bootstrapTable('refresh', {url: '/manage/parts/queryPagePartsInfoByParams'});
    });

    //查询按钮事件
    $('#search_tbtn').click(function(){
        $('#mytitletab').bootstrapTable('refresh', {url: '/manage/title/queryTitleListByParams'});
    });

//查找时
    $(function(){
        var titledataArr=$('#mytitletab').bootstrapTable('getSelections');
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



    //选择事件按钮,内容添加部件
    $('#btn_padd').click(function(){
        var pdataArr=$('#myparttab').bootstrapTable('getSelections');
        $('.popup_de .show_msg').text('确定添加吗?');
        $('.popup_de').addClass('bbox');

        $('.popup_de .btn_submit').one('click',function(){

            var pStr='';
            for(var i=0;i<pdataArr.length;i++){
                pStr+=pdataArr[i].part_id+',';
            }

            $('#add_part').val(pStr);
            $('.popup_de .show_msg').text('添加成功！');
            $('.popup_de .btn_cancel').css('display','none');
            $('.popup_de').addClass('bbox');

            $('.popup_de .btn_submit').one('click',function(){
                $('.popup_de').removeClass('bbox');
                $('#mypartModal').modal('hide');
            });
        })
    });

    //选择事件按钮,内容添加部件
    $('#btn_tadd').click(function(){
        var tdataArr=$('#mytitletab').bootstrapTable('getSelections');
        $('.popup_de .show_msg').text('确定添加吗?');
        $('.popup_de').addClass('bbox');

        $('.popup_de .btn_submit').one('click',function(){

            $('#add_title').val(tdataArr[0].title_id);
            $('.popup_de .show_msg').text('添加成功！');
            $('.popup_de .btn_cancel').css('display','none');
            $('.popup_de').addClass('bbox');

            $('.popup_de .btn_submit').one('click',function(){
                $('.popup_de').removeClass('bbox');
                $('#mytitleModal').modal('hide');
            });
        })
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
		$('#edit_ID').val(dataArr[0].answer_id);
		$('#edit_title').val(dataArr[0].title_content);
		$('#edit_remark').val(dataArr[0].remark);
		$('#edit_part').val(dataArr[0].part_name);


        //选择事件按钮,内容添加部件
        $('#btn_padd').click(function(){
            var pdataArr=$('#myparttab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定添加吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){

                $('#edit_part').val(pdataArr[0].part_id);

                $('.popup_de .show_msg').text('添加成功！');
                $('.popup_de .btn_cancel').css('display','none');
                $('.popup_de').addClass('bbox');

                $('.popup_de .btn_submit').one('click',function(){
                    $('.popup_de').removeClass('bbox');
                    $('#mypartModal').modal('hide');
                });
            })
        });

        //选择事件按钮,内容添加部件
        $('#btn_tadd').click(function(){
            var tdataArr=$('#mytitletab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定添加吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){

                $('#edit_title').val(tdataArr[0].title_id);

                $('.popup_de .show_msg').text('添加成功！');
                $('.popup_de .btn_cancel').css('display','none');
                $('.popup_de').addClass('bbox');

                $('.popup_de .btn_submit').one('click',function(){
                    $('.popup_de').removeClass('bbox');
                    $('#mytitletab').modal('hide');
                });
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


    //查看按钮事件
    $('#btn_check').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.modal fade').css('display','block');
       $('.modal fade').addClass('animated slideInRight');
        $('#check_ID').val(dataArr[0].barcode_id);
        $('#check_categoryId').val(dataArr[0].category_name);
        $('#check_subId').val(dataArr[0].sub_name);
        $('#check_partId').val(dataArr[0].part_id);
        $('#check_partName').val(dataArr[0].part_name);
        $('#check_partSpec').val(dataArr[0].part_spec);
        $('#check_partPresent').val(dataArr[0].part_present);
        //$('#check_sex1').val(dataArr[0].sex);

        $('#check_partPrinciple').val(dataArr[0].part_present);
        $('#check_proId').val(dataArr[0].proName);
        $('#check_contentId').val(dataArr[0].contentName);
        $('#check_parUrl').val(dataArr[0].part_url);

        $('#check_url').val(dataArr[0].barcode_url);
        var badres =dataArr[0].barcode_url
        document.getElementById('barcodeImg').src ="BAR/"+badres;

        $('#check_parUrl').val(dataArr[0].part_url);
        var padres =dataArr[0].part_url

        document.getElementById('partImg').src ="OTA/"+padres;
        $('#partImg').click(function () {
            $(this).toggleClass("min");
            $(this).toggleClass("max");
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

                "/manage/title/createAnswerInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageAnswerInfo'});
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
    		 $.post("/manage/title/updateAnswerInfo",
				$('#editForm').serialize(),
				function(data){
					if(data){
						//隐藏修改与删除按钮
						$('#btn_delete').css('display','none');
						$('#btn_edit').css('display','none');
						//回退到条码管理主页
						$('.changeBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.changeBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight'); 
				    	//刷新人员管理主页
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageAnswerInfo'});
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
        		ID[i]=dataArr[i].answer_id;
        	}

        	$.post("/manage/title/deleteAnswerInfo",
                {answerIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/title/queryPageAnswerInfo'});
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
