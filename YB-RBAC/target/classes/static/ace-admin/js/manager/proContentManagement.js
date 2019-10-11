function aaa() {
    window.location.reload();
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
    	url:"/manage/content/queryPageProContentInfo",
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
        		title:'实训内容编号',
        		field:'content_id',
        		visible:false
        	},
        	{
        		title:'实训内容名称',
        		field:'content_name',
        		sortable:true
        	},
            {
                title:'所属实训项目',
                field:'pro_name',
                sortable:true
            },
        	{
        		title:'说明',
        		field:'remark',
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
		       	feedbackIcons: {
		               valid: 'glyphicon glyphicon-ok',
		               invalid: 'glyphicon glyphicon-remove',
		               validating: 'glyphicon glyphicon-refresh'
		           },
		           fields: {
                       content_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '实训内容名称不能为空'
		                       }
		                   }
		               },
                       pro_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '所属项目为必填'
		                       }
		                   }
		               },
                       partIds: {
                           validators: {
                               notEmpty: {
                                   message: '请至少选择一项实训部件'
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
                    content_name: {
                        validators: {
                            notEmpty: {
                                message: '实训内容名称不能为空'
                            }
                        }
                    },
                    pro_name: {
                        validators: {
                            notEmpty: {
                                message: '所属项目为必填'
                            }
                        }
                    },
                    partIds: {
                        validators: {
                            notEmpty: {
                                message: '请至少选择一项实训部件'
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
    	var dataArr=$('#mytab').bootstrapTable('getSelections');
    	$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function(){
			$('.tableBody').removeClass('animated slideOutLeft').css('display','none');
		},500);
		$('.changeBody').css('display','block');
		$('.changeBody').addClass('animated slideInRight');
		$('#edit_ID').val(dataArr[0].content_id);
		$('#edit_Name').val(dataArr[0].content_name);
		$('#edit_pro').val(dataArr[0].pro_name);
        $('#edit_remark').val(dataArr[0].remark);


    });


    //查看按钮事件
    $('#btn_check').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.modal fade').css('display','block');
        $('.modal fade').addClass('animated slideInRight');
        $('#check_ID').val(dataArr[0].part_id);
        $('#check_Name').val(dataArr[0].part_name);
        $('#check_position').val(dataArr[0].part_position);
        $('#check_present').val(dataArr[0].part_present);
        $('#check_principle').val(dataArr[0].part_principle);
        $('#check_number').val(dataArr[0].part_number);
        $('#check_spec').val(dataArr[0].part_spec);
        $('#check_url').val(dataArr[0].part_url);
        var adres =dataArr[0].part_url
		alert(adres)
        document.getElementById('imgView').src ="OTA/"+adres;
        $('#imgView').click(function () {
            $(this).toggleClass("min");
            $(this).toggleClass("max");
        });
    });

    //内容部件管理按钮事件
    $('#btn_partManage').click(function(){
        $("#parttab").bootstrapTable('destroy');
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.partBody').css('display','block');
        $('.partBody').addClass('animated slideInRight');
        $('#content_ID').val(dataArr[0].content_id);
        var str = dataArr[0].content_name +'—部件管理';
        $('#content_Name').val(str);
        //生成部件数据
        $('#parttab').bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url:"/manage/content/queryPartInfosByParams",
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
                    title:'部件介绍',
                    field:'part_present',
                    sortable:true
                },
                {
                    title:'部件位置',
                    field:'part_position',
                    sortable:true
                },

                {
                    title:'部件数量',
                    field:'part_number',
                    sortable:true
                }
            ],
            locale:'zh-CN',//中文支持,
        });



        //生成用户数据
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
                    title:'全选',
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
                content_id:$('#content_ID').val(),
                part_name:$('#search_name').val()
            }
        }
        //查询按钮事件
        $('#search_btn').click(function(){
            $('#myparttab').bootstrapTable('refresh', {url: '/manage/parts/queryPagePartsInfoByParams'});
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
                    ID[i]=dataArrp[i].part_id;
                }
              var contentId =dataArr[0].content_id;
                $.post("/manage/content/deleteContentParts",
                    {contentId:contentId,partIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('删除成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/content/queryPartInfosByParams'});
                        }else{
                        }
                    },"json");
            })
        });

        //添加事件按钮,内容添加部件
        $('#btn_padd').click(function(){
            var pdataArr=$('#myparttab').bootstrapTable('getSelections');
            $('.popup_de .show_msg').text('确定添加吗?');
            $('.popup_de').addClass('bbox');
            $('.popup_de .btn_submit').one('click',function(){
                var ID=[];
                for(var i=0;i<pdataArr.length;i++){
                    ID[i]=pdataArr[i].part_id;
                }
                var contentId =dataArr[0].content_id;
                $.post("/manage/content/createContentPartInfo",
                    {contentId:contentId,partIds:ID},
                    function(data){
                        if(data){
                            $('.popup_de .show_msg').text('添加成功！');
                            $('.popup_de .btn_cancel').css('display','none');
                            $('.popup_de').addClass('bbox');
                            $('.popup_de .btn_submit').one('click',function(){
                                $('.popup_de').removeClass('bbox');
                                $('#mypartModal').modal('hide');
                            });
                            $('#parttab').bootstrapTable('refresh', {url: '/manage/content/queryPartInfosByParams'});
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
            $('#btn_partManage').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');

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
        //$('#editForm').data('bootstrapValidator').resetForm(true);
    });
    /**新增****/
    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/projectTrain/queryProjectTrainList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#add_pro").append("<option value='"+data[i].pro_id+"'>"+data[i].pro_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }
                //这一步不要忘记 不然下拉框没有数据
                $("#add_pro").selectpicker("refresh");

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
            url: '/manage/projectTrain/queryProjectTrainList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#edit_pro").append("<option value='"+data[i].pro_name+"'>"+data[i].pro_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }

                //这一步不要忘记 不然下拉框没有数据
                $("#edit_pro").selectpicker("refresh")



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
                "/manage/content/createProContentInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
                        $('#myModal').modal('hide');
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/content/queryPageProContentInfo'});
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
    		 $.post("/manage/content/updateProContentInfo",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/content/queryPageProContentInfo'});
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
        		ID[i]=dataArr[i].content_id;
        	}

        	$.post("/manage/content/deleteProContentInfo",
                {contentIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/content/queryPageProContentInfo'});
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
