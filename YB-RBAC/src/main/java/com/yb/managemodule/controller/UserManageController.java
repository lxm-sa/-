package com.yb.managemodule.controller;



import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yb.base.pojo.RoleEntity;
import com.yb.base.pojo.UserEntity;
import com.yb.base.vo.Result;
import com.yb.base.vo.UserVo;
import com.yb.managemodule.service.IUserService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制接口类
 * Created by Administrator on 2019/7/25.
 */

@RestController
@Log4j
@RequestMapping(value = "/manage/user")
public class UserManageController {
    private final static Logger logger = Logger.getLogger(UserManageController.class);
    @Autowired
    private IUserService userService;

    /**
     * 分页查询用户信息
     * @return
     */
    @GetMapping("/queryPageUser")
    @ResponseBody
    public Object queryPageUserInfo(HttpServletRequest request,Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page) {
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        RoleEntity role =(RoleEntity) usermap.get("role");
        int order_number = role.getOrder_number();
        List<UserVo> userVos = userService.queryPageUserInfo(order_number);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (userVos.size()<a){
            a=userVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(userVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(userVos.size());
        return page;
    }

    /**
     * 模糊查询，支持分页
     * @param page
     * @param userName
     * @param telphone
     * @return
     */
    @GetMapping("/queryPageUserInfoByParams")
    @ResponseBody
    public Object queryPageUserInfoByParams(Integer pageSize, @RequestParam(defaultValue="1")Integer pageNumber,Page page, @RequestParam(value = "user_name") String userName, @RequestParam(value = "telphone") String telphone) {

        List<UserVo> userVoList = userService.queryPageUserInfoByParams(userName, telphone);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (userVoList.size()<a){
            a=userVoList.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(userVoList.get(i));
        }
        page.setRecords(list);
        page.setTotal(userVoList.size());
        return page;
    }


    /**
     * 修改用户信息
     * @param userId
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/updateUserById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserById(@RequestParam("user_id")int userId, UserVo userVo){

        Result result = userService.updateUserById(userId, userVo);

        return result;
    }

    /**
     * 审核用户登录状态
     * @param userId
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateUserStatusById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserStatusById(@RequestParam("user_id")Integer userId,@RequestParam("status") int status) {

        Result result = userService.updateUserStatusById(userId, status);

        return result;
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/createUserInfo", method = RequestMethod.POST )
    @ResponseBody
    public Result createUserInfo(UserVo userVo) {

        Result result = userService.createUserInfo(userVo);

        return result;
    }

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */

    @RequestMapping(value = "/deleteUserInfos", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserInfos(@RequestParam("userIds[]") Integer[] userIds) {

        Result result = userService.deleteUserInfos(userIds);

        return result;
    }
    /**
     * 重置用户密码
     * @param userId
     * @return
     */

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result resetPassword(@RequestParam("user_id")int userId) {

        Result result = userService.resetPassword(userId);

        return result;
    }

    /**
     * 查询教师列表
     * @return
     */
    @GetMapping("/queryTeacherList")
    @ResponseBody
    public List<UserVo> queryTeacherList(){

        List<UserVo> userVos = userService.queryTeacherList();

        return userVos;
    }

    @GetMapping("/queryPageStudent")
    @ResponseBody
    public Object queryStudentList(Page page,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber,HttpServletRequest request) {
        PageHelper.setPagination(page);
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();
        List<UserVo> userVos = userService.queryStudentList(teachId);
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (userVos.size()<a){
            a=userVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(userVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(userVos.size());//获取总数并释放资源 也可以 PageHelper.getTotal()
        return page;
    }

    @GetMapping("/queryPageStudentInfoByParams")
    @ResponseBody
    public Object queryPageStudentInfoByParams(Page page,@RequestParam(value = "user_name") String userName,@RequestParam(value = "telphone") String telphone,HttpServletRequest request) {
        PageHelper.setPagination(page);
        HttpSession session = request.getSession();
        Map<String,Object> usermap = ( Map<String,Object>)session.getAttribute("user");
        UserEntity user =(UserEntity) usermap.get("user");
        int teachId = user.getUser_id();
        List<UserVo> userVoList = userService.queryPageStudentInfoByParams(userName, telphone,teachId);

        page.setRecords(userVoList);

        page.setTotal(userVoList.size());
        return page;
    }
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public List<Result> importExcel(InputStream  inp) throws IOException,
            InvalidFormatException {

        Workbook book =  new XSSFWorkbook(inp);

        Sheet sheet = book.getSheetAt(0);

        List<Result> results = new ArrayList<>();
        List<UserVo> userVos=new ArrayList<UserVo>();
        /**
         * 通常第一行都是标题，所以从第二行开始读取数据
         */
        for(int i=1; i<sheet.getLastRowNum()+1; i++) {
            Row row = sheet.getRow(i);
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);

            String user_name = row.getCell(0).getStringCellValue();
            String account_name = row.getCell(1).getStringCellValue();
            String password = row.getCell(2).getStringCellValue();
            String identity_id= row.getCell(3).getStringCellValue();
            String birthday= row.getCell(4).getStringCellValue();
            String sex= row.getCell(5).getStringCellValue();
            String hometowm= row.getCell(6).getStringCellValue();
            String school= row.getCell(7).getStringCellValue();
            String url= row.getCell(8).getStringCellValue();
            String telphone= row.getCell(9).getStringCellValue();
            String role_id= row.getCell(10).getStringCellValue();

            UserVo userVo=new UserVo();
            if(!Strings.isNullOrEmpty(user_name)){
                userVo.setUser_name(user_name);
            }
            if(!Strings.isNullOrEmpty(account_name)){
                userVo.setAccount_name(account_name);
            }
            userVo.setPassword(password);
            userVo.setIdentity_id(identity_id);
            if(!Strings.isNullOrEmpty(birthday)){
                //将String类型转换为Date类型
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
                try {
                    Date birday = formatter.parse(birthday);
                    userVo.setBirthday(birday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!Strings.isNullOrEmpty(sex)){
                userVo.setSex(Integer.parseInt(sex));
            }

            userVo.setHometowm(hometowm);
            userVo.setSchool(school);
            userVo.setUrl(url);
            userVo.setTelphone(telphone);
            if(!Strings.isNullOrEmpty(role_id)){
                userVo.setRole_id(Integer.parseInt(role_id));
            }

            userVos.add(userVo);
        }

        for (UserVo userVo : userVos) {
           // System.out.println(userVo);
            if(null!=userVo){
                Result result = userService.createUserInfo(userVo);
                results.add(result);
            }
        }
        return results;
    }

    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        //直接下载路径下的文件模板
        try {
            //获取要下载的模板名称
            String fileName = "user_example.xlsx";
            //设置要下载的文件的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            //通知客服文件的MIME类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            //获取文件的路径
            String filePath = getClass().getResource("/templates/excel/" + fileName).getPath();
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
            //return Response.ok("应用导入模板下载完成");
        } catch (Exception ex) {
            logger.error("getApplicationTemplate :", ex);
            //return Response.ok("应用导入模板下载失败！");
        }
    }
    @GetMapping("/queryClassPageStudent")
    @ResponseBody
    public Object queryClassStudentList(Page page,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNumber) {
        PageHelper.setPagination(page);

        List<UserVo> userVos = userService.queryClassStudentList();
        List list=new ArrayList();
        int a=(pageNumber)*pageSize;
        if (userVos.size()<a){
            a=userVos.size();
        }
        for (int i=(pageNumber-1)*pageSize;i<a;i++){
            list.add(userVos.get(i));
        }
        page.setRecords(list);
        page.setTotal(userVos.size());//获取总数并释放资源 也可以 PageHelper.getTotal()
        return page;
    }

    @GetMapping("/queryPageClassStudentInfoByParams")
    @ResponseBody
    public Object queryPageClassStudentInfoByParams(Page page,@RequestParam(value = "user_name") String userName,@RequestParam(value = "telphone") String telphone) {
        PageHelper.setPagination(page);

        List<UserVo> userVoList = userService.queryPageClassStudentInfoByParams(userName, telphone);

        page.setRecords(userVoList);

        page.setTotal(userVoList.size());
        return page;
    }
}
