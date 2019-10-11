
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
    	url:"/manage/test/queryPageStuTestInfos",
    	height:tableHeight(),//高度调整
    	toolbar: '#toolbar',
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
        smartDisplay: false,
    	showRefresh:true,//刷新按钮
    	showColumns:true,
    	clickToSelect: true,//是否启用点击选中行
    	toolbarAlign:'right',
    	buttonsAlign:'right',//按钮对齐方式
        singleSelect : true, //单选
    	toolbar:'#toolbar',//指定工作栏
    	columns:[
        	{
        		title:'选择',
        		field:'select',
        		checkbox:true,
        		width:25,
        		align:'center',
        		valign:'middle'
        	},
        	{
        		title:'试卷编号',
        		field:'test_id',
                align:'center',
                valign:'middle',
        		visible:false
        	},
        	{
        		title:'试卷',
        		field:'test_title',
                align:'center',
                valign:'middle',
        		sortable:true
        	},
            {
                title:'考试科目',
                field:'sub_name',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'科目老师',
                field:'teach_name',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'考试起始日期',
                field:'start_time',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'考试截止时间',
                field:'end_time',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'考试时长',
                field:'test_time',
                align:'center',
                valign:'middle',
                sortable:true
            },
            {
                title:'剩余考试次数',
                field:'test_count',
                align:'center',
                valign:'middle',
                sortable:true
            }

    	],
    	locale:'zh-CN',//中文支持,
    });


    //请求服务数据时所传参数
   // var dataArr=$('#mytab').bootstrapTable('getSelections');
    function queryParams(params){
    	return{
            pageSize: params.limit,
            pageNumber:(params.offset / params.limit) + 1,
    	}
    }

    //增加按钮事件
    $('#btn_add').click(function(){

        $('.modal fade').css('display','block');
        $('.modal fade').addClass('animated slideInRight');
    });


    //删除按钮与修改,重置按钮的出现与消失
    $('.bootstrap-table').change(function(){
        var dataArr=$('#mytab .selected');

        if(dataArr.length==1){
            $('#btn_exam').css('display','block').removeClass('fadeOutRight').addClass('animated fadeInRight');
        }else{

            $('#btn_exam').addClass('fadeOutRight');
            setTimeout(function(){
                $('#btn_exam').css('display','none');
            },400);
        }

    });

    //开始考试按钮事件

    $('#btn_exam').click(function(){
        $('.tableBody').addClass('animated slideOutLeft');
        setTimeout(function(){
            $('.tableBody').removeClass('animated slideOutLeft').css('display','none');
        },500);
        $('.changeBody').css('display','block');
        $('.changeBody').addClass('animated slideInRight');
        var dataArr=$('#mytab').bootstrapTable('getSelections');
        $("#test").load("/examPage .exam");

        document.getElementById("testTitle").innerText=dataArr[0].test_title;

        document.getElementById("subName").innerText=dataArr[0].sub_name;

        document.getElementById("testTime").innerText=dataArr[0].test_time +" 分钟";

        document.getElementById("totalScore").innerText=dataArr[0].total_score +" 分";
        var n = dataArr[0].test_count;
        var testTime ;
        var testM;
        var testS;
        var testH;
        var testHH;
        var testMM;
        var testSS;
        var timer = null;
        $(function(){
            //定义倒计时的时间
            var minutes = dataArr[0].test_time;
            var seconds = 0;
            function show(){
                //判断时间到了没
                if(seconds==0&&minutes==0){
                    clearInterval(timer);//清除定时器
                    alert("考试时间到,系统自动提交试卷!");
                    var testId = dataArr[0].test_id;
                    var testTime = testHH+":"+testMM+":"+testSS;
                    var scoreNumber = (n-1);
                    $.post("/manage/test/createStuScoreInfo",
                        {testId:testId,testTime:testTime,scoreNumber:scoreNumber},
                        function(data){
                            if(data){
                                if(scoreNumber==1){
                                    alert("考试次数："+scoreNumber)
                                    $('.popup_de .show_msg').text('当前为第一次考试，你还有一次考试机会，是否进行第二次考试?');
                                    $('.popup_de').addClass('bbox');
                                    $('.popup_de .btn_submit').one('click',function(){
                                        $('.popup_de .show_msg').text('请准备进行第二次考试！');
                                        $('.popup_de .btn_cancel').css('display','none');
                                        $('.popup_de').addClass('bbox');
                                        $('.popup_de .btn_submit').one('click',function(){
                                            $('.popup_de').removeClass('bbox');
                                            $('.changeBody').addClass('animated slideOutLeft');
                                            setTimeout(function(){
                                                $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                            },500);
                                            $('.tableBody').css('display','block').addClass('animated slideInRight');
                                        });
                                    });
                                    $('.popup_de .btn_cancel').one('click',function(){
                                        $('.popup_de').removeClass('bbox');
                                        $('.changeBody').addClass('animated slideOutLeft');
                                        setTimeout(function(){
                                            $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                        },500);
                                        $('.tableBody').css('display','block').addClass('animated slideInRight');
                                    })
                                    $('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageStuTestInfos'});
                                }else if(scoreNumber==0){

                                    layer.alert('考试完成，考试机会已用完', {icon: 6});

                                    $('.changeBody').addClass('animated slideOutLeft');
                                    setTimeout(function(){
                                        $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                    },500);
                                    $('.tableBody').css('display','block').addClass('animated slideInRight');

                                    $('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageStuTestInfos'});

                                }

                            }else{
                            }
                        }
                    )

                }
                seconds--;
                if(seconds<0){
                    seconds = 59;
                    minutes--;
                }

                minutes = (minutes+"").length==1?"0"+minutes:minutes;//(minutes+"")是将其数据类型转换成字符串类型
                seconds = (seconds+"").length==1?"0"+seconds:seconds;
                document.getElementById("RemainingTime").innerText = minutes+":"+seconds;

                testM = (dataArr[0].test_time-1) -minutes;
                if(testM>=120){
                    testM = testM - 120;
                    testH = 2;
                    testHH = (testH+"").length==1?"0"+testH:testH;
                    testS = (59-seconds);
                    testMM = (testM+"").length==1?"0"+testM:testM;//(minutes+"")是将其数据类型转换成字符串类型
                    testSS = (testS+"").length==1?"0"+testS:testS;
                }else if(testM>=60){
                    testM = testM - 60;
                    testH = 1;
                    testHH = (testH+"").length==1?"0"+testH:testH;
                    testS = (59-seconds);
                    testMM = (testM+"").length==1?"0"+testM:testM;//(minutes+"")是将其数据类型转换成字符串类型
                    testSS = (testS+"").length==1?"0"+testS:testS;
                }else{
                    testH = 0;
                    testHH = (testH+"").length==1?"0"+testH:testH;
                    testS = (59-seconds);
                    testMM = (testM+"").length==1?"0"+testM:testM;//(minutes+"")是将其数据类型转换成字符串类型
                    testSS = (testS+"").length==1?"0"+testS:testS;
                }
            }
            //开启定时器
            timer = setInterval(show,1000);
        })
        var QuestionJosn =[];
        var activeQuestion=0; //当前操作的考题编号
        var questioned = 0; //
        var title_id;
        var  questions;
        questions = QuestionJosn;
        //展示考卷信息
        function showQuestion(id){
            $(".questioned").text(id+1);
            questioned = (id+1)/questions.length
            if(activeQuestion!=undefined ){
                $("#ques"+activeQuestion).removeClass("question_id").addClass("active_question_id");
            }
            activeQuestion = id;
            $(".question").find(".question_info").remove();
            var question = questions[id];
            title_id=question.questionId;
            //alert(question);
            $(".question_title").html("<strong>第 "+(id+1)+" 题 、</strong>"+question.questionTitle+
                "<li style='margin-left: 25px;margin-top: 50px'><strong>答：</strong>"+"<input type='text' name='stu_answer_barcode' placeholder='请将鼠标选中该区域' id='answer'></li>");

            $(".question").attr("id","question"+id);
            $("#ques"+id).removeClass("active_question_id").addClass("question_id");

            progress();
        }

        /*答题卡*/
        function answerCard(){
            $(".question_sum").text(questions.length);
            var questionId="";
            for(var i=0;i<questions.length;i++){
                    questionId +="<li id='ques"+i+"' class='questionId'>"+(i+1)+"</li>";
            }
            $("#answerCard ul").html(questionId);

        };

        /*设置进度条*/
        function progress(){
            var prog =0;
            var pro =0;
            prog = ($(".active_question_id").length+1)/questions.length;
            pro = $(".progress").parent().width() * prog;
            $(".progres").text((prog*100).toString().substr(0,5)+"%")
            $(".progress").animate({
                width: pro,
                opacity: 0.5
            }, 1000);
        }

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

                    for(var i=0;i<data.length;i++){

                        var questionId =data[i].title_id;
                        var questionTitle = data[i].title_content
                        QuestionJosn.push({"questionId":questionId,"questionTitle":questionTitle})
                       //
                    }

                    $(".middle-top-left").width($(".middle-top").width()-$(".middle-top-right").width())
                    $(".progress-left").width($(".middle-top-left").width()-200);
                    progress();
                    answerCard();
                    showQuestion(0);

                    /*答题卡的切换*/
                    $("#openCard").click(function(){
                        $("#closeCard").show();
                        $("#answerCard").slideDown();
                        $(this).hide();
                    })
                    $("#closeCard").click(function(){
                        $("#openCard").show();
                        $("#answerCard").slideUp();
                        $(this).hide();
                    })

                    //提交试卷
                    $("#submitQuestions").click(function(){
                        layer.msg('是否确定提交试卷？', {
                            time: 0 ,//不自动关闭
                            icon:6,
                            btn: ['确定', '取消'],
                            yes: function(index){
                                layer.close(index);
                                var testId = dataArr[0].test_id;
                                var testTime =testHH+":"+testMM+":"+testSS;
                                var scoreNumber = (3-n);
                                var count = (n-1);
                                console.log(QuestionJosn)
                                console.log("aaa")
                                var array=[]
                                for(var i=0;i<QuestionJosn.length;i++){
                                    array[i]=QuestionJosn[i].questionId
                                    console.log(array[i])
                                }
                                $.post("/manage/test/createStuScoreInfo",
                                    {testId:testId,testTime:testTime,scoreNumber:scoreNumber,array:array},
                                    function(data){
                                        if(data){
                                            if(count==1){

                                                $('.popup_de .show_msg').text('当前为第1次考试，你还有1次考试机会，是否进行第2次考试?');
                                                $('.popup_de').addClass('bbox');
                                                $('.popup_de .btn_submit').one('click',function(){
                                                    $('.popup_de .show_msg').text('请准备进行第2次考试！');
                                                    $('.popup_de .btn_cancel').css('display','none');
                                                    $('.popup_de').addClass('bbox');
                                                    $('.popup_de .btn_submit').one('click',function(){

                                                        $('.popup_de').removeClass('bbox');
                                                        $('.changeBody').addClass('animated slideOutLeft');
                                                        setTimeout(function(){
                                                            $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                                        },500);
                                                        $('.tableBody').css('display','block').addClass('animated slideInRight');
                                                        location.reload();
                                                    });
                                                });
                                                $('.popup_de .btn_cancel').one('click',function(){
                                                    $('.popup_de').removeClass('bbox');
                                                    $('.changeBody').addClass('animated slideOutLeft');
                                                    setTimeout(function(){
                                                        $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                                    },500);
                                                    $('.tableBody').css('display','block').addClass('animated slideInRight');
                                                    location.reload();
                                                })
                                                $('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageStuTestInfos'});
                                            }else if(count==0){

                                                layer.alert('考试完成，考试机会已用完', {icon: 6});

                                                $('.changeBody').addClass('animated slideOutLeft');
                                                setTimeout(function(){
                                                    $('.changeBody').removeClass('animated slideOutLeft').css('display','none');
                                                },500);
                                                $('.tableBody').css('display','block').addClass('animated slideInRight');
                                                document.getElementById("btn_exam").disabled=true;
                                                $('#mytab').bootstrapTable('refresh', {url: '/manage/test/queryPageStuTestInfos'});
                                                location.reload();
                                            }
                                        }else{
                                        }
                                    },'json')
                                clearInterval(timer);
                            }
                        });

                    });
                    //进入下一题
                    $("#nextQuestion").click(function(){
                        var testId = dataArr[0].test_id;
                        var titleId=title_id;
                        var count = (3-n);
                        var stuAnswerId =$("#answer").val()
                        $.post("/manage/test/createStuAnswerInfo",
                            {testId:testId,titleId:titleId,stuAnswerId:stuAnswerId,count:count}
                        )
                            if((activeQuestion+2)==questions.length){
                                $('#sureAnswer').css('display','block');
                                $('#nextQuestion').css('display','none');
                            }
                            if((activeQuestion+1)!=questions.length) {showQuestion(activeQuestion+1)}


                            });

                    $("#sureAnswer").click(function(){
                        var testId = dataArr[0].test_id;
                        var titleId=title_id;
                        var count = (3-n);
                        var stuAnswerId =$("#answer").val()
                        $.post("/manage/test/createStuAnswerInfo",
                            {testId:testId,titleId:titleId,stuAnswerId:stuAnswerId,count:count}
                        )
                    });

                    //答题卡列表
                        var oli = document.getElementById("navlist").getElementsByTagName("li");
                        for (var i = 0; i < oli.length; i++) {
                            (function (j) {
                                oli[j].onclick = function () {

                                    showQuestion(j);

                                    var testId = dataArr[0].test_id;
                                    var titleId=title_id;
                                    var count =(3-n);
                                    $.get("/manage/test/queryStuAnswerInfo",
                                        {testId:testId,titleId:titleId,count:count},
                                        function(data){
                                            if(data){
                                                $("#answer").val(data.stu_answer_barcode);
                                            }else{

                                            }
                                        },"json")
                                };
                            })(i)
                        }
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
