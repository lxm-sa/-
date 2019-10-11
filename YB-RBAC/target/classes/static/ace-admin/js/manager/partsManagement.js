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
    	url:"/manage/parts/queryPageParts",
    	height:tableHeight()-180,//高度调整
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
                       part_name: {
		                   validators: {
		                       notEmpty: {
		                           message: '部件名称不能为空'
		                       }
		                   }
		               },
                       part_principle: {
		                   validators: {
		                       notEmpty: {
		                           message: '部件原理不能为空'
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
                    part_name: {
                        validators: {
                            notEmpty: {
                                message: '部件名称不能为空'
                            }
                        }
                    },
                    part_principle: {
                        validators: {
                            notEmpty: {
                                message: '部件原理不能为空'
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
    		pageIndex:params.pageNumber,
            part_name:$('#search_name').val()

    	}
    }
    //查询按钮事件
    $('#search_btn').click(function(){
    	$('#mytab').bootstrapTable('refresh', {url: '/manage/parts/queryPagePartsInfoByParams'});
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
		$('#edit_ID').val(dataArr[0].part_id);
		$('#edit_Name').val(dataArr[0].part_name);
		$('#edit_position').val(dataArr[0].part_position);
        $('#edit_present').val(dataArr[0].part_present);
		$('#edit_principle').val(dataArr[0].part_principle);
        $('#edit_number').val(dataArr[0].part_number);
        $('#edit_positionUrl').val(dataArr[0].position_url);
		//$('#edit_sex').val(dataArr[0].sex);
        $('#edit_spec').val(dataArr[0].part_spec);
        $('#edit_url').val(dataArr[0].part_url);

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
        document.getElementById('imgView').src ="OTA/"+adres;
        $('#imgView').click(function () {
            $(this).toggleClass("min");
            $(this).toggleClass("max");
        });
        $('#check_positionUrl').val(dataArr[0].position_url);
        var poadres =dataArr[0].position_url
        document.getElementById('positionImgView').src ="OTA/"+poadres;
        $('#positionImgView').click(function () {
            $(this).toggleClass("min");
            $(this).toggleClass("max");
        });
    });





   /* $('#file-fr').fileinput({
        theme: 'fa',
        language: 'zh',
        uploadAsync: true,//异步上传
        uploadUrl: '/manage/parts/updatePhoto',
        allowedFileExtensions: ['jpg', 'png', 'gif','mp4'],
        maxFileSize:0,
        maxFileCount:10
    }).on("fileuploaded", function(event,data) { //异步上传成功结果处理
        alert(data.response.src);
        $("#uploadImage").val(data.response.src);
    })
*/

	//创建信息时上传图片
    $(document).on("ready", function() {
        $("#addImage").fileinput({
            language: 'zh',　　　　　　　　//使用中文
            theme: 'fa',
            uploadAsync: false,
            uploadUrl: "/manage/parts/updatePhoto",
            dataType : 'json',
            autoUpload: false,
            allowedFileExtensions: ['jpg', 'png', 'gif', 'mp4'],
            maxFileSize: 3000000, // 1 MB
            imageMaxWidth: 100,
            imageMaxHeight: 100,
            minFileCount: 0,
            maxFileCount: 5,
            messages: {
                acceptFileTypes: '文件类型不匹配',
                maxFileSize: '文件过大',
                minFileSize: '文件过小'
            }
        }).on("filebatchselected", function(event, files) {  //选中要上传的文件后回调
            $(this).fileinput("upload"); //自动上传

        }).on("filebatchuploadsuccess", function(event,data) {
            var resp = data.response;
            $("#add_url").val(resp.message);
        }).on('fileerror', function(event, data, msg) {
            console.log('文件上传失败！'+msg);
        });

    })

	//修改信息时上传图片
    $(document).on("ready", function() {
        $("#uploadImage").fileinput({
            language: 'zh',　　　　　　　　//使用中文
            theme: 'fa',
            uploadAsync: false,
            uploadUrl: "/manage/parts/updatePhoto",
            dataType : 'json',
            autoUpload: false,
            allowedFileExtensions: ['jpg', 'png', 'gif', 'mp4'],
            maxFileSize: 3000000, // 1 MB
            imageMaxWidth: 100,
            imageMaxHeight: 100,
            minFileCount: 0,
            maxFileCount: 5,
            messages: {
                acceptFileTypes: '文件类型不匹配',
                maxFileSize: '文件过大',
                minFileSize: '文件过小'
            }
        }).on("filebatchselected", function(event, files) {  //选中要上传的文件后回调
        $(this).fileinput("upload"); //自动上传

    }).on("filebatchuploadsuccess", function(event,data) {
          var resp = data.response;
            $("#edit_url").val(resp.message);
        }).on('fileerror', function(event, data, msg) {
            console.log('文件上传失败！'+msg);
        });

    })
//创建信息时上传位置图片
    $(document).on("ready", function() {
        $("#addPositionImage").fileinput({
            language: 'zh',　　　　　　　　//使用中文
            theme: 'fa',
            uploadAsync: false,
            uploadUrl: "/manage/parts/updatePositionPhoto",
            dataType : 'json',
            autoUpload: false,
            showUpload: true,
            showPreview:true,
            showRemove:false,
            allowedFileExtensions: ['jpg', 'png', 'gif', 'mp4'],
            maxFileSize: 5000000, // 1 MB
            imageMaxWidth: 500,
            imageMaxHeight: 500,
            minFileCount: 0,
            maxFileCount: 1,
            previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
            maxImageWidth: 1000,//图片的最大宽度
            maxImageHeight: 1000,//图片的最大高度
            messages: {
                acceptFileTypes: '文件类型不匹配',
                maxFileSize: '文件过大',
                minFileSize: '文件过小'
            }
        }).on("filebatchselected", function(event, files) {  //选中要上传的文件后回调
            // $(this).fileinput("upload"); //自动上传

        }).on("filebatchuploadsuccess", function(event,data) {
            var resp = data.response;
            $("#add_positionUrl").val(resp.message);
        }).on('fileerror', function(event, data, msg) {
            console.log('文件上传失败！'+msg);
        });

    })

//修改信息时上传位置图片
    $(document).on("ready", function() {
        $("#editPositionImage").fileinput({
            language: 'zh',　　　　　　　　//使用中文
            theme: 'fa',
            uploadAsync: false,
            uploadUrl: "/manage/parts/updatePositionPhoto",
            dataType: 'json',
            autoUpload: false,
            showUpload: true,
            showPreview: true,
            showRemove: false,
            allowedFileExtensions: ['jpg', 'png', 'gif', 'mp4'],
            maxFileSize: 5000000, // 1 MB
            imageMaxWidth: 500,
            imageMaxHeight: 500,
            minFileCount: 0,
            maxFileCount: 1,
            enctype: 'multipart/form-data',
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            messages: {
                acceptFileTypes: '文件类型不匹配',
                maxFileSize: '文件过大',
                minFileSize: '文件过小'
            }
        }).on("filebatchselected", function(event, files) {  //选中要上传的文件后回调
            // $(this).fileinput("upload"); //自动上传

        }).on("filebatchuploadsuccess", function(event,data) {
            var resp = data.response;
            $("#edit_positionUrl").val(resp.message);
        }).on('fileerror', function(event, data, msg) {
            console.log('文件上传失败！'+msg);
        });

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

                "/manage/parts/createPartsInfo",
				$('#addForm').serialize(),
				function(data){

					//后台返回添加成功
					if(data){
						$('.addBody').addClass('animated slideOutLeft');
				    	setTimeout(function(){
							$('.addBody').removeClass('animated slideOutLeft').css('display','none');
						},500);
				    	$('.tableBody').css('display','block').addClass('animated slideInRight');
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/parts/queryPageParts'});
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
    		 $.post("/manage/parts/updatePartsInfoById",
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
				    	$('#mytab').bootstrapTable('refresh', {url: '/manage/parts/queryPageParts'});
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
        		ID[i]=dataArr[i].part_id;
        	}

        	$.post("/manage/parts/deletePartInfos",
                {partIds:ID},
				function(data){
        			if(data){
        			$('.popup_de .show_msg').text('删除成功！');
					$('.popup_de .btn_cancel').css('display','none');
					$('.popup_de').addClass('bbox');
					$('.popup_de .btn_submit').one('click',function(){
						$('.popup_de').removeClass('bbox');
					});
        			$('#mytab').bootstrapTable('refresh', {url: '/manage/parts/queryPageParts'});
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
