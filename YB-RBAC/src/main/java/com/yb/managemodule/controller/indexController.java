package com.yb.managemodule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/7/31.
 */
@Controller
public class indexController {

    @RequestMapping("/userManangePage")
    public String userManage() {
        return "userManagement";
    }

    @RequestMapping("/learnSourcesPage")
    public String learnSources() {
        return "learnSourcesManagement";
    }
    @RequestMapping("/categoryPage")
    public String categoryPage() {
        return "categoryManagement";
    }

    @RequestMapping("/partPage")
    public String parts() {
        return "partsManagement";
    }
    @RequestMapping("/proContentPage")
    public String proContent() {
        return "proContentManagement";
    }
    @RequestMapping("/projectPage")
    public String projectTrain() {
        return "projectTrainManagement";
    }
    @RequestMapping("/subjectPage")
    public String subject() {
        return "subjectManagement";
    }
    @RequestMapping("/titleTypePage")
    public String titleType() {
        return "titleTypeManagement";
    }

    @RequestMapping("/titlePage")
    public String title() {
        return "titleManagement";
    }
    @RequestMapping("/answerPage")
    public String answer() {
        return "answerManagement";
    }

    @RequestMapping("/testPage")
    public String test() {
        return "testManagement";
    }

    @RequestMapping("/examPage")
    public String exam() {
        return "examManagement";
    }
    @RequestMapping("/testExamPage")
    public String testexam() {
        return "testExamManagement";
    }

    @RequestMapping("/stuScorePage")
    public String stuScore() {
        return "stuScoreManagement";
    }

    @RequestMapping("/expireTestPage")
    public String expireTest() {
        return "expireExamManagement";
    }

    @RequestMapping("/classManagePage")
    public String classManagement() {
        return "classManagement";
    }

}
