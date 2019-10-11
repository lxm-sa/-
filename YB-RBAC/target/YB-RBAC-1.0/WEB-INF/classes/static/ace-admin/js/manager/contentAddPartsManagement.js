$(function(){
	//根据窗口调整表格高度
    $(window).resize(function() {
        $('#myparttab').bootstrapTable('resetView', {
            height: tableHeight()

        })
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
    /*
     * 用户管理首页事件
     */




    //请求服务数据时所传参数
    function queryParams(params){
    	return{
    		pageSize: params.limit,
    		pageIndex:params.pageNumber,
            part_name:$('#search_name').val()

    	}
    }

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
    return $(window).height() - 140;
}
