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
    	url:"/manage/barcode/queryPageBarcodes",
    	height:tableHeight()-70,//高度调整
    	toolbar: '#toolbar',
    	striped: true, //是否显示行间隔色
    	dataField: "records",
    	pageNumber: 1, //初始化加载第一页，默认第一页
    	pagination:true,//是否分页
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
        		title:'条码编号',
        		field:'barcode_id',
                align:'center',
                valign:'middle',
                sortable:true
        	},
            {
                title:'对应部件编号',
                field:'part_id',
                align:'center',
                valign:'middle',
                sortable:true
            },

            {
                title:'部件名称',
                field:'part_name',
                align:'center',
                valign:'middle',
                sortable:true
            },
        	{
        		title:'资源类别',
        		field:'category_name',
                align:'center',
                valign:'middle',
        		sortable:true
        	},
        	{
        		title:'所属科目',
        		field:'sub_name',
                align:'center',
                valign:'middle',
        		sortable:true
        	},
            {
                title:'应用项目',
                field:'proName',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'应用项目内容',
                field:'contentName',
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
                       category_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '资源类别不能为空'
		                       }
		                   }
		               },
                       barcode_id: {
		                   validators: {
		                       notEmpty: {
		                           message: '条码不能为空'
		                       }
		                   }
		               },
		               sub_name: {
	                        validators: {
	                            notEmpty: {
	                                message: '请选择科目'
	                            }
	                        }
	                    },

                       barcode_url:{
		               	validators:{
		               		notEmpty:{
		               			message:'存储名称不能为空'
		               		},
                            regexp: {
                                regexp: /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/,
                                message: '请填写正确的格式'
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
                    category_name: {
                        validators: {
                            notEmpty: {
                                message: '资源类别不能为空'
                            }
                        }
                    },
                    barcode_id: {
                        validators: {
                            notEmpty: {
                                message: '条码不能为空'
                            }
                        }
                    },
                    sub_name: {
                        validators: {
                            notEmpty: {
                                message: '请选择科目'
                            }
                        }
                    },

                    barcode_url:{
                        validators:{
                            notEmpty:{
                                message:'存储名称不能为空'
                            },
                            regexp: {
                                regexp: /(^.+\\.(jpg|jpeg|bmp|gif|png)$)/,
                                message: '请填写正确的格式'
                            }
                        }
                    }
                }
		       });

    //生成用户数据
    $('#myparttab').bootstrapTable({
        method: 'get',
        contentType: "application/x-www-form-urlencoded",
        url:"/manage/parts/queryPageParts",
        height:tableHeight(),//高度调整
        striped: true, //是否显示行间隔色
        dataField: "records",
        pageNumber: 1, //初始化加载第一页，默认第一页
        pagination:true,//是否分页
        sortable: true,            //是否启用排序
        sortOrder: "asc",
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
        singleSelect : true,
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
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'部件名称',
                field:'part_name',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'部件位置',
                field:'part_position',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'规格型号',
                field:'part_spec',
                align:'center',
                valign:'middle',
                sortable:true
            }
        ],
        locale:'zh-CN',//中文支持,
    });

    //请求服务数据时所传参数
    function queryParams(params){
        return{
            pageSize: params.limit,
            pageNumber:(params.offset / params.limit) + 1,
            part_name:$('#search_name').val()
        }
    }

    //查询按钮事件
    $('#search_btn').click(function(){
        $('#myparttab').bootstrapTable('refresh', {url: '/manage/parts/queryPagePartsInfoByParams'});
    });


    //选择事件按钮,内容添加部件
    $('#btn_padd').click(function(){
        var pdataArr=$('#myparttab').bootstrapTable('getSelections');
        $('.popup_de .show_msg').text('确定添加吗?');
        $('.popup_de').addClass('bbox');

        $('.popup_de .btn_submit').one('click',function(){

            $('#add_part').val(pdataArr[0].part_id);
            $('.popup_de .show_msg').text('添加成功！');
            $('.popup_de .btn_cancel').css('display','none');
            $('.popup_de').addClass('bbox');

            $('.popup_de .btn_submit').one('click',function(){
                $('.popup_de').removeClass('bbox');
                $('#mypartModal').modal('hide');
            });
        })
    });


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

            }
        });

    });


    $(function(){
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/category/queryCategoryList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#sel_category").append("<option value='"+data[i].category_id+"'>"+data[i].category_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }
                //这一步不要忘记 不然下拉框没有数据

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



            }
        });

    });

    $(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        //初始化下拉框
        //动态加载
        $.ajax({
            type: 'get',
            url: '/manage/category/queryCategoryList',
            dataType: "json",
            success: function (data) {
                //拼接下拉框
                for(var i=0;i<data.length;i++){
                    $("#edit_category").append("<option value='"+data[i].category_name+"'>"+data[i].category_name+"</option>");
                    //$("#sel_role").val(dataArr[i].role_name);
                }

                //这一步不要忘记 不然下拉框没有数据



            }
        });

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
    	if(dataArr.length>=1&&dataArr.length<=30){
            $('#btn_print').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
        }else if(dataArr.length>30){
    	    alert("一次至多打印20个！")
            $('#btn_print').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_print').css('display','none');
            },400);
        }else{
            $('#btn_print').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_print').css('display','none');
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
		$('#edit_ID').val(dataArr[0].barcode_id);
		$('#edit_url').val(dataArr[0].barcode_url);
		$('#edit_sub').val(dataArr[0].sub_name);
        $('#edit_category').val(dataArr[0].category_name);
		$('#edit_part').val(dataArr[0].part_id);
        var ares =dataArr[0].barcode_url
        document.getElementById('codeImg').src ="BAR/"+ares;
        //$('#edit_islogin').val(dataArr[0].islogin);



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
        $('#check_positionUrl').val(dataArr[0].position_url);

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
        var poadres =dataArr[0].position_url
        document.getElementById('positionImgView').src ="OTA/"+poadres;

        $('#positionImgView').click(function () {
            $(this).toggleClass("min");
            $(this).toggleClass("max");
        });
    });



    $('#printDeal').click(function(){
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->";
        eprnstr="<!--endprint-->";
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        window.document.body.innerHTML=prnhtml;
        window.print();
        window.location.reload();

    })

    $('#btn_print').click(function(){
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.printBody').css('display','block');
        $('.printBody').addClass('animated slideInRight');
        var printStr ="";
        for(var i=0;i<dataArr.length;i++){
            var badres =dataArr[i].barcode_url

            printStr += "<img src='BAR/"+badres+"' style='width: 13%;margin-top: 25px;margin-left: 30px; float: left'  id='codePrintImg"+i+"'  class='min'/>"
                         ;

        }
        $("#printDiv").html(printStr);

    });

    $('#print_saveBtn').click(function(){
        //判断iframe是否存在，不存在则创建iframe
        var iframe=document.getElementById("print-iframe");
        if(!iframe){
            var el = document.getElementById("printDiv");

            iframe = document.createElement('IFRAME');
            var doc = null;
            iframe.setAttribute("id", "print-iframe");
            iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
            document.body.appendChild(iframe);
            doc = iframe.contentWindow.document;
            //这里可以自定义样式
            //doc.write("<LINK rel="stylesheet" type="text/css" href="css/print.css">");

            doc.write('<div style="text-align:left">' + el.innerHTML + '</div>');
            doc.close();
            iframe.contentWindow.focus();
        }
        iframe.contentWindow.print();
        if (navigator.userAgent.indexOf("MSIE") > 0){
            document.body.removeChild(iframe);
        }

    })

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

                "/manage/barcode/createBarcodeInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/barcode/queryPageBarcodes'});
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

    //修改页面回退按钮事件
    $('#print_backBtn').click(function(){
        $('.printBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.printBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.tableBody').css('display','block').addClass('animated slideInRight');
    });


    //修改页面保存按钮事件
    $('#edit_saveBtn').click(function(){
    	$('#editForm').bootstrapValidator('validate');
    	if($("#editForm").data('bootstrapValidator').isValid()){
    		 $.post("/manage/barcode/updateBarcodeInfoById",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/barcode/queryPageBarcodes'});
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
        		ID[i]=dataArr[i].barcode_id;
        	}

        	$.post("/manage/barcode/deleteBarcodeInfos",
                {barcodeIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/barcode/queryPageBarcodes'});
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
