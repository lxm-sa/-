package com.yb.assessmentmodule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yb.assessmentmodule.service.ITestService;
import com.yb.base.mapper.*;
import com.yb.base.pojo.*;
import com.yb.base.vo.*;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2019/8/26.
 */

@Service
public class TestService implements ITestService {

    public static final String MESSAGE_PARAM_UPDATE_FAIL = "修改失败";
    public static final String MESSAGE_PARAM_UPDATE_SUCCESS = "修改成功";
    public static final String MESSAGE_PARAM_OBJ_NULL = "参数【BarcodeVo】不能为空";
    public static final String MESSAGE_CREATE_SUCCESS = "创建成功";
    public static final String MESSAGE_CREATE_FAIL = "创建失败";
    public static final String MESSAGE_PARAM_NULL = "参数不能为空";
    public static final String MESSAGE_DELETE_SUCCESS = "删除成功";
    public static final String MESSAGE_DELETE_FAIL = "删除失败";
    public static final String MESSAGE_GET_SUCCESS = "查询成功";
    public static final String MESSAGE_GET_FAIL = "查询失败";
    public static final String MESSAGE_TEST_NUMBER = "考试次数已用完";

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private StuTestMapper stuTestMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TestTitleMapper testTitleMapper;
    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private StuAnswerMapper stuAnswerMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private BarcodeMapper barcodeMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
     private  SubjectMapper subjectMapper;
    /**
     * 试卷列表查询，适用分页
     * @return
     */
    @Override
    public List<TestVo> queryPageTestInfo(int teachId) {
        List<TestVo> testVos = new ArrayList<>();
        List<TestEntity> testList = testMapper.selectTestList(teachId);
        for (TestEntity testEntity : testList) {
            TestVo testVo = new TestVo();
            BeanUtils.copyProperties(testEntity, testVo);
            testVos.add(testVo);
        }
        return testVos;
    }

    /**
     * 根据试卷Id查询学员列表
     * @param testId
     * @return
     */
    @Override
    public List<UserVo> queryUsersByParams(int testId) {
        List<UserVo> userVos = new ArrayList<>();
        List<StuTestEntity> stuTestEntities = stuTestMapper.selectStuTestByParams(testId);
        for (StuTestEntity stuTestEntitie : stuTestEntities) {
            UserVo userVo = new UserVo();
            UserEntity user = userMapper.selectById(stuTestEntitie.getStu_id());

            BeanUtils.copyProperties(user, userVo);
            userVos.add(userVo);
        }

        return userVos;
    }

    /**
     * 创建试卷信息
     * @param testVo
     * @return
     */
    @Override
    public Result createTestInfo(TestVo testVo,int teachId) {
        Result result = new Result();
        //验证参数
        if (null == testVo) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_OBJ_NULL);
            return result;
        }

        //构建资源类别模型
        TestEntity testEntity = new TestEntity();

        //复制属性赋值给titleTypeEntity对象
        BeanUtils.copyProperties(testVo, testEntity);

        try {
            testEntity.setTeacher_id(teachId);
            testEntity.setUpdate_time(new Date());
            testEntity.setCreate_time(new Date());
            //创建题型表信息
            testMapper.insert(testEntity);

            result.setMessage(MESSAGE_CREATE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    /**
     * 修改试卷信息
     * @param testId
     * @param testVo
     * @return
     */
    @Override
    public Result updateTestInfo(int testId, TestVo testVo,int teachId) {
        Result result = new Result();
        //验证参数
        if (Strings.isNullOrEmpty(Integer.toString(testId))) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        TestEntity testEntity = testMapper.selectById(testId);
        testEntity.setTest_title(testVo.getTest_title());

        SubjectEntity subjectEntity = subjectMapper.selectSubjectOne(testVo.getSub_name());
        testEntity.setSub_id(subjectEntity.getSub_id());

        testEntity.setStart_time(testVo.getStart_time());
        testEntity.setEnd_time(testVo.getEnd_time());
        testEntity.setTotal_score(testVo.getTotal_score());
        testEntity.setTeacher_id(teachId);
        testEntity.setTest_time(testVo.getTest_time());
        testEntity.setTest_rule(testVo.getTest_rule());
        testEntity.setTitle_count(testVo.getTitle_count());
        testEntity.setUpdate_time(new Date());

        try{
            testMapper.updateById(testEntity);
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }


        return result;
    }
    /**
     * 删除试卷信息，支持批量删除
     * @param testIds
     * @return
     */
    @Override
    public Result deleteTestInfos(int[] testIds) {
        Result result = new Result();
        // 验证参数
        if (testIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int testId : testIds) {

                TestEntity testEntity = testMapper.selectById(testId);

                testEntity.setIsdelete(1);
                testMapper.updateById(testEntity);

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }

    public Result deleteStuTestInfo(int testId, int[] stuIds) {
        Result result = new Result();
        // 验证参数
        if (stuIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int stuId : stuIds) {
                Map<String, Object> params = new HashMap<>();
                params.put("test_id", testId);
                params.put("stu_id", stuId);
                //删除学员试卷关联表信息
                StuTestEntity stuTestEntity = stuTestMapper.selectStuTestInfoByParams(testId, stuId);
                if(stuTestEntity.getStatus()!=1){
                    stuTestMapper.deleteByMap(params);
                }else{
                    //学员已经完成了考试则不删除
                }

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }

    /**
     * 创建学员试卷关联表信息
     * @param testId
     * @param stuIds
     * @return
     */
    @Override
    public Result createStuTestInfos(int testId, int[] stuIds) {
        Result result = new Result();
        // 验证参数
        if (stuIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {
                for (int stuId : stuIds) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("test_id", testId);
                    params.put("stu_id", stuId);
                    List<StuTestEntity> stuTestEntityList = stuTestMapper.selectByMap(params);
                    //若集合没有元素，则直接创建
                    if (0 == stuTestEntityList.size()) {
                        StuTestEntity stuTestEntity = new StuTestEntity();
                        stuTestEntity.setTest_id(testId);
                        stuTestEntity.setStu_id(stuId);
                        stuTestEntity.setTest_count(2);
                        //创建学员试卷关联表信息
                        stuTestMapper.insert(stuTestEntity);
                        result.setSuccess(true);
                        result.setMessage(MESSAGE_CREATE_SUCCESS);
                    } else {

                        //若集合存在元素。则直接忽略
                    }
                }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }

    /**
     * 根据试卷编号查询试卷题目
     * @param testId
     * @return
     */

    @Override
    public List<TestTitleVo> queryTitleByTestId(int testId) {

             List<TestTitleVo> testTitleVos = new ArrayList<>();
              //
            List<TestTitleEntity> testTitleEntityList = testTitleMapper.selectTestTitleByTestId(testId);
            for (TestTitleEntity testTitleEntity : testTitleEntityList) {
                TestTitleVo testTitleVo = new TestTitleVo();

                BeanUtils.copyProperties(testTitleEntity, testTitleVo);
                testTitleVos.add(testTitleVo);
        }
        return testTitleVos;
    }
    /**
     * 根据出题规则查询试卷题目
     * @param testId
     * @param testRule
     * @return
     */
    @Override
    public List<TitleAnswerVo> queryTitleByParams(int testId,int testRule) {

        List<TitleAnswerVo> titleAnswerVos = new ArrayList<>();
        TestEntity testEntity = testMapper.selectById(testId);
        int titleCount =testEntity.getTitle_count();
        if(1==testRule ){  //随机出题
            List<TestTitleEntity> testTitleEntityList = testTitleMapper.selectTestTitleByRand(testId,titleCount);
            for (TestTitleEntity testTitleEntity : testTitleEntityList) {
                TitleAnswerVo titleAnswerVo = new TitleAnswerVo();
                //定义StringBuffer类型的字符接收部件和部件Id
                StringBuffer partBuf = new StringBuffer();
                List<TitleEntity> titleEntityList = titleMapper.selectTitleById(testTitleEntity.getTitle_id());
                for (TitleEntity titleEntity : titleEntityList) {

                    partBuf.append(titleEntity.getPart_id()+" "+titleEntity.getPart_name()+"； &nbsp;&nbsp;");
                    titleAnswerVo.setTitle_id(titleEntity.getTitle_id());
                    titleAnswerVo.setTitle_content(titleEntity.getTitle_content());
                    titleAnswerVo.setTitle_type_id(titleEntity.getTitle_type_id());
                    titleAnswerVo.setTitle_type_name(titleEntity.getTitle_type_name());
                    titleAnswerVo.setTitleAnswer(partBuf.toString());


                }
                if(0 != titleAnswerVo.getTitle_id()){
                    titleAnswerVos.add(titleAnswerVo);
                }else{
                    //
                }

            }
        }else if(0==testRule){         //顺序出题
            List<TestTitleEntity> testTitleEntityList = testTitleMapper.selectTestTitleByParams(testId,titleCount);
            for (TestTitleEntity testTitleEntity : testTitleEntityList) {
                TitleAnswerVo titleAnswerVo = new TitleAnswerVo();
                //定义StringBuffer类型的字符接收部件和部件Id
                StringBuffer partBuf = new StringBuffer();
                List<TitleEntity> titleEntityList = titleMapper.selectTitleById(testTitleEntity.getTitle_id());
                for (TitleEntity titleEntity : titleEntityList) {

                    partBuf.append(titleEntity.getPart_id()+" "+titleEntity.getPart_name()+"； &nbsp;&nbsp;");
                    titleAnswerVo.setTitle_id(titleEntity.getTitle_id());
                    titleAnswerVo.setTitle_content(titleEntity.getTitle_content());
                    titleAnswerVo.setTitle_type_id(titleEntity.getTitle_type_id());
                    titleAnswerVo.setTitle_type_name(titleEntity.getTitle_type_name());
                    titleAnswerVo.setTitleAnswer(partBuf.toString());


                }
                if(0 != titleAnswerVo.getTitle_id()){
                    titleAnswerVos.add(titleAnswerVo);
                }else{
                    //
                }

            }


        }




        return titleAnswerVos;
    }


    /**
     * 删除试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    @Override
    public Result deleteTestTitleInfos(int testId, int[] titleIds) {
        Result result = new Result();
        // 验证参数
        if (titleIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {

            for (int titleId : titleIds) {
                Map<String, Object> params = new HashMap<>();
                params.put("test_id", testId);
                params.put("title_id", titleId);
                //删除试卷题目关联表信息
                testTitleMapper.deleteByMap(params);

            }
            result.setSuccess(true);
            result.setMessage(MESSAGE_DELETE_SUCCESS);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_DELETE_FAIL);
        }

        return result;
    }


    /**
     * 创建试卷题目关联表信息
     * @param testId
     * @param titleIds
     * @return
     */
    @Override
    public Result createTestTitleInfos(int testId, int[] titleIds) {
        Result result = new Result();
        // 验证参数
        if (titleIds.length == 0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("test_id", testId);
            List<TestTitleEntity> testTitleEntities = testTitleMapper.selectByMap(params);
            //若为空，则直接新建
            if(0 ==testTitleEntities.size()){
                for (int titleId : titleIds) {

                            TestTitleEntity testTitle = new TestTitleEntity();
                            testTitle.setTest_id(testId);
                            testTitle.setTitle_id(titleId);

                            //创建学员试卷关联表信息
                            testTitleMapper.insert(testTitle);
                            result.setSuccess(true);
                            result.setMessage(MESSAGE_CREATE_SUCCESS);
                }

            }else{//不为空时，则需要进行是否已经存在的判断

                //定义一个集合接收testTitleEntities里面的titleId
                List<Integer> titleIdList = new ArrayList<>();
                for (TestTitleEntity testTitleEntity : testTitleEntities) {
                    titleIdList.add(testTitleEntity.getTitle_id());
                }
                for (int titleId : titleIds) {
                    if(titleIdList.contains(titleId)){

                        //若已经存在，则忽略
                    }else{

                        TestTitleEntity testTitle = new TestTitleEntity();
                        testTitle.setTest_id(testId);
                        testTitle.setTitle_id(titleId);

                        //创建学员试卷关联表信息
                        testTitleMapper.insert(testTitle);
                        result.setSuccess(true);
                        result.setMessage(MESSAGE_CREATE_SUCCESS);
                    }
                }
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }

    @Override
    public List<StuTestVo> queryPageStuTestInfos(int stuId) {
        List<StuTestVo> stuTestVos = new ArrayList<>();

        List<StuTestEntity> stuTestEntityList = stuTestMapper.selectStuTestByStuId(stuId);
        for (StuTestEntity stuTestEntity : stuTestEntityList) {
            StuTestVo stuTestVo = new StuTestVo();
            BeanUtils.copyProperties(stuTestEntity, stuTestVo);
            stuTestVos.add(stuTestVo);
        }
        return stuTestVos;
    }

    @Override
    public Result updateStuTestInfo(int stuId, int testId, int testCount) {
        Result result = new Result();
        // 验证参数
        if (stuId == 0 && testId== 0 && testCount >2 ) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {
            StuTestEntity stuTestEntity = stuTestMapper.selectStuTestInfoByParams(testId, stuId);
            stuTestEntity.setTest_count(testCount);
            stuTestEntity.setStatus(1);
            stuTestMapper.updateById(stuTestEntity);

            result.setSuccess(true);
            result.setMessage(MESSAGE_PARAM_UPDATE_SUCCESS);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_UPDATE_FAIL);
        }
        return result;
    }


    @Override
    public Result createStuAnswerInfo(int stuId, int testId, int titleId, String stuAnswerId,int count) {
        Result result = new Result();
        // 验证参数
        if (testId==0 && titleId==0) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        try {
            String[]str=stuAnswerId.split(" ");
            if (str.length>1){
                stuAnswerId=str[1];
                stuAnswerId = stuAnswerId.substring(0, 12);
            }else {
                stuAnswerId=str[0];
                if (stuAnswerId.length()>12){
                    stuAnswerId = stuAnswerId.substring(0, 12);
                }
            }

            //查询表中是否已经含有该条数据
            StuAnswerEntity stuAnswer = stuAnswerMapper.selectOneByMap(stuId, testId, titleId,count);
            //若为空，则直接新建
            if(null ==stuAnswer){

                StuAnswerEntity stuAnswerEntity = new StuAnswerEntity();
                stuAnswerEntity.setUser_id(stuId);
                stuAnswerEntity.setTest_id(testId);
                stuAnswerEntity.setTitle_id(titleId);
                stuAnswerEntity.setStu_answer_barcode(stuAnswerId);
                stuAnswerEntity.setCount(count);
                //判断答案
                if(Strings.isNullOrEmpty(stuAnswerId)){
                    stuAnswerEntity.setStatus(0);
                }else{
                    //判断答案是否正确
                    List<String> barcodeIdList = new ArrayList<>();
                    Map<String, Object> param = new HashMap<>();
                    param.put("title_id", titleId);
                    List<AnswerEntity> answerEntityList = answerMapper.selectByMap(param);
                    for (AnswerEntity answerEntity : answerEntityList) {
                        Map<String, Object> partIdprama = new HashMap<>();
                        partIdprama.put("part_id", answerEntity.getPart_id());
                        List<BarcodeEntity> barcodeEntityList = barcodeMapper.selectByMap(partIdprama);
                        for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                            barcodeIdList.add(barcodeEntity.getBarcode_id());
                        }
                    }
                    //如果学生答案与答案表有相同，则为正确答案
                    if(barcodeIdList.contains(stuAnswerId)){
                        stuAnswerEntity.setStatus(2);

                    }else{
                        stuAnswerEntity.setStatus(1);
                    }
                }
                stuAnswerEntity.setCreate_time(new Date());
                stuAnswerEntity.setUpdate_time(new Date());

                    //创建学员答案信息
                stuAnswerMapper.insert(stuAnswerEntity);
                    result.setSuccess(true);
                    result.setMessage(MESSAGE_CREATE_SUCCESS);
                }
                else{//不为空时，则对学生答案进行修改即可

                stuAnswer.setStu_answer_barcode(stuAnswerId);
                //判断答案
                if(Strings.isNullOrEmpty(stuAnswerId)){
                    stuAnswer.setStatus(0);
                }else{
                    //判断答案是否正确
                    List<String> barcodeIdList = new ArrayList<>();
                    Map<String, Object> param = new HashMap<>();
                    param.put("title_id", titleId);
                    List<AnswerEntity> answerEntityList = answerMapper.selectByMap(param);
                    for (AnswerEntity answerEntity : answerEntityList) {
                        Map<String, Object> partIdprama = new HashMap<>();
                        partIdprama.put("part_id", answerEntity.getPart_id());
                        List<BarcodeEntity> barcodeEntityList = barcodeMapper.selectByMap(partIdprama);
                        for (BarcodeEntity barcodeEntity : barcodeEntityList) {
                            barcodeIdList.add(barcodeEntity.getBarcode_id());
                        }
                    }
                    //如果学生答案与答案表有相同，则为正确答案
                    if(barcodeIdList.contains(stuAnswerId)){
                        stuAnswer.setStatus(2);

                    }else{
                        stuAnswer.setStatus(1);
                    }


                }

                stuAnswerMapper.updateById(stuAnswer);

            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }
        return result;
    }

    @Override
    public Result createStuScoreInfo(int userId, int testId, String testTime,int scoreNumber,int[]  array) {
        Result result = new Result();
        Boolean isSuccess = false;
        // 验证参数
        if (testId==0 && scoreNumber > 2 && scoreNumber==0 ) {
            result.setSuccess(false);
            result.setMessage(MESSAGE_PARAM_NULL);
            return result;
        }
        Wrapper wrapper= new EntityWrapper();
        wrapper.eq("test_id",testId);
        wrapper.eq("count",scoreNumber);
        wrapper.eq("user_id",userId);
        List<StuAnswerEntity> list = stuAnswerMapper.selectList(wrapper);

        for (int i=0;i<array.length;i++){
            Boolean flag=false;
            StuAnswerEntity stuAnswer=new StuAnswerEntity();
            for (StuAnswerEntity stuAnswerEntity:list){
                if (stuAnswerEntity.getTitle_id()==array[i]){

                       flag=true;
                       break;
                }


            }
            if (!flag){
                stuAnswer.setTest_id(testId);
                stuAnswer.setCount(scoreNumber);
                stuAnswer.setStatus(0);
                stuAnswer.setUpdate_time(new Date());
                stuAnswer.setCreate_time(new Date());
                stuAnswer.setTitle_id(array[i]);
                stuAnswer.setUser_id(userId);
                Integer insert = stuAnswerMapper.insert(stuAnswer);
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("test_id", testId);
        param.put("user_id", userId);
        param.put("score_number", scoreNumber);
        try {
            //判断学员考试成绩是否已经存在

            List<ScoreEntity> scoreEntityList = scoreMapper.selectByMap(param);
            if (0 == scoreEntityList.size()) {
                //若表中没有数据，则直接新建考试成绩
                ScoreEntity scoreEntity = new ScoreEntity();
                scoreEntity.setTest_id(testId);
               scoreEntity.setUser_id(userId);
                scoreEntity.setTest_time(testTime);
                scoreEntity.setStatus(1);
                scoreEntity.setScore_number(scoreNumber);
                scoreEntity.setCreate_time(new Date());
                scoreEntity.setUpdate_time(new Date());
                //计算考试成绩
                Map<String, Object> testParam = new HashMap<>();
                testParam.put("test_id", testId);
                TestEntity testEntity = testMapper.selectById(testId);

                List<StuAnswerEntity> stuAnswerEntityList = stuAnswerMapper.selectTrueAnswerByMap( userId,testId,scoreNumber);
                //题目总数
                int totalTitles = testEntity.getTitle_count();
                //答对数
                int trueTitles = stuAnswerEntityList.size();

                //计算得分
                Double score = (trueTitles*100.0 / totalTitles) ;
                //四舍五入处理
                int stuScore = (int) Math.round(score);
                scoreEntity.setScore(stuScore);

                scoreMapper.insert(scoreEntity);
                //修改考试剩余次数
                int testCount = (2-scoreNumber);
                updateStuTestInfo(userId,testId,testCount);
                result.setSuccess(true);
                result.setMessage(MESSAGE_CREATE_SUCCESS);
                isSuccess = true;

            }
            else{
                //
            }

        }catch (Exception e){
            if(!isSuccess){
                Map<String, Object> answerParam = new HashMap<>();
                answerParam.put("test_id", testId);
                answerParam.put("user_id", userId);
                answerParam.put("count", scoreNumber);
                stuAnswerMapper.deleteByMap(answerParam);
            }

            result.setSuccess(false);
            result.setMessage(MESSAGE_CREATE_FAIL);
        }

        return result;
    }

    /**
     * 查询学生答题的答案信息
     * @param stuId
     * @param testId
     * @param titleId
     * @param count
     * @return
     */
    @Override
    public StuAnswerVo queryStuAnswerInfo(int stuId, int testId, int titleId, int count) {
        StuAnswerVo stuAnswerVo = new StuAnswerVo();

        StuAnswerEntity stuAnswerEntity = stuAnswerMapper.selectOneByMap(stuId, testId, titleId, count);

        BeanUtils.copyProperties(stuAnswerEntity, stuAnswerVo);

        return stuAnswerVo;
    }

    /**
     * 查询学员完成的试卷成绩
     * @param stuId
     * @return
     */
    @Override
    public List<ScoreVo> queryCompleteTestScores(int stuId) {
        List<ScoreVo> scoreVos = new ArrayList<>();
        List<ScoreEntity> scoreEntities = scoreMapper.selectCompleteTestInfos(stuId);
        for (ScoreEntity scoreEntity : scoreEntities) {
            ScoreVo scoreVo = new ScoreVo();
            BeanUtils.copyProperties(scoreEntity, scoreVo);

            scoreVos.add(scoreVo);
        }

        return scoreVos;
    }

    @Override
    public List<StuAnswerVo> queryStuTsetAnswerInfos(int userId, int testId, int count) {
        List<StuAnswerVo> stuAnswerVos = new ArrayList<>();
        List<StuAnswerEntity> stuAnswerEntityList = stuAnswerMapper.selectAnswerListByMap(userId, testId, count);
        for (StuAnswerEntity stuAnswerEntity : stuAnswerEntityList) {
            StuAnswerVo stuAnswerVo = new StuAnswerVo();
            BeanUtils.copyProperties(stuAnswerEntity, stuAnswerVo);
            //若答案不为空时
            if(!Strings.isNullOrEmpty(stuAnswerEntity.getStu_answer_barcode())){
                StuAnswerEntity answerEntity = stuAnswerMapper.selectAnswerByMap(stuAnswerEntity.getStu_answer_id());
                if (answerEntity==null){
                    stuAnswerVo.setPart_id(0);
                    stuAnswerVo.setPart_name("没有该部件");
                }else {
                    stuAnswerVo.setPart_id(answerEntity.getPart_id());
                    stuAnswerVo.setPart_name(answerEntity.getPart_name());
                }
            }

            List<AnswerEntity> answerEntityList = answerMapper.selectAnswerAll();
            StringBuffer modelAnswerbuf = new StringBuffer();
            for (AnswerEntity answerEntity : answerEntityList) {
                if((stuAnswerEntity.getTitle_id())==(answerEntity.getTitle_id())){
                    modelAnswerbuf.append(answerEntity.getPart_id()+"."+answerEntity.getPart_name()+"\t   ");
                }else{
                    //
                }
            }

            stuAnswerVo.setModelAnswer(modelAnswerbuf.toString());

            stuAnswerVos.add(stuAnswerVo);
        }

        return stuAnswerVos;
    }

    @Override
    public List<StuTestVo> queryPageExpireTestInfos(int stuId) {
        List<StuTestVo> stuTestVos = new ArrayList<>();

        List<StuTestEntity> stuTestEntityList = stuTestMapper.selectExpireTestByStuId(stuId);
        for (StuTestEntity stuTestEntity : stuTestEntityList) {
            StuTestVo stuTestVo = new StuTestVo();
            BeanUtils.copyProperties(stuTestEntity, stuTestVo);
            stuTestVos.add(stuTestVo);
        }
        return stuTestVos;
    }




}
