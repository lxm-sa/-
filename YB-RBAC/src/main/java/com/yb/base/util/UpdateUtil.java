package com.yb.base.util;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/8/13.
 */
public class UpdateUtil {


    public String updatePhoto(MultipartFile file, String fileUploadFolder)throws IOException {
        //将文件以 文件目录/年/月日/文件名的情况来存储
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        String monthString;
        String dayString;
        if(month < 10) {
            monthString = "0" + month;
        } else {
            monthString = String.valueOf(month);
        }
        if(day < 10) {
            dayString = "0" + day;
        } else {
            dayString = String.valueOf(day);
        }
        StringBuilder sbd = new StringBuilder();
        sbd.append(year).append("/")
                .append(monthString).append(dayString).append("/");

        //上传文件目录不存在时创建该目录
        File dir = new File(fileUploadFolder + sbd.toString());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        // 获得当前时间
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());

        //获取文件名
        String filename = file.getOriginalFilename();
        //文件名后缀
        String pexfix=filename.substring(filename.lastIndexOf("."));
        byte[] bytes = file.getBytes();
        Path path = Paths.get(fileUploadFolder + sbd.toString()+formatDate+String.valueOf(1)+pexfix);
        //文件写入磁盘
        Files.write(path, bytes);
        //返回图片储存地址
        String projectpic=sbd.toString()+formatDate+String.valueOf(1)+pexfix;
        return projectpic;
    }

}
