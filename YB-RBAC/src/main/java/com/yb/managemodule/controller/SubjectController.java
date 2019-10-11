package com.yb.managemodule.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yb.base.vo.Result;
import com.yb.base.vo.SubjectVo;
import com.yb.managemodule.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/12.
 */

@RestController
@RequestMapping(value = "/manage/subject")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;


    /**
     * 分页查询科目列表
     * @return
     */

    @GetMapping("/queryPageSubjectList")
    @ResponseBody
    public Object queryPageSubjectList(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page){

        List<SubjectVo> subjectVos = subjectService.queryPageSubjectInfo();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (subjectVos.size()<a){
            a=subjectVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(subjectVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(subjectVos.size());
        return page;
    }

    /**
     * 查询科目列表
     * @return
     */
    @GetMapping("/queryPageSubject")
    @ResponseBody
    public List<SubjectVo> queryPageSubjectInfo(){


        List<SubjectVo> subjectVos = subjectService.queryPageSubjectInfo();

        return subjectVos;
    }
    /**
     * 创建科目
     * @param subjectVo
     * @return
     */
    @RequestMapping(value = "/createSubjectInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createSubjectInfo(SubjectVo subjectVo){

        Result result = subjectService.createSubjectInfo(subjectVo);

        return result;
    }


    @RequestMapping(value = "/deleteSubjects", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSubjects(@RequestParam("subIds[]")Integer[] subIds){

        Result result = subjectService.deleteSubjects(subIds);

        return result;
    }

    @RequestMapping(value = "/updateSubjectById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSubjectById(@RequestParam("sub_id")int subId, SubjectVo subjectVo){

        Result result = subjectService.updateSubjectById(subId, subjectVo);

        return result;
    }

}
