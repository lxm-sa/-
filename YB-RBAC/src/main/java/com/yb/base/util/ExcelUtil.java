package com.yb.base.util;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/12.
 */
public class ExcelUtil {
    /**
     * @功能：手工构建一个简单格式的Excel
     */
    public static String createExcel(List<Map<String,Object>> list, String[] strArray,String[] keys) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);// 默认列宽
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        String url="";
        // 添加excel title
        HSSFCell cell = null;
        for (int i = 0; i < strArray.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(style);
        }

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到,list中字符串的顺序必须和数组strArray中的顺序一致
        int i = 0;
        for (Map<String,Object> str :list) {
            row = sheet.createRow((int) i + 1);

            // 第四步，创建单元格，并设置值
            for (int j = 0; j < strArray.length; j++) {
                row.createCell((short) j).setCellValue((String) str.get(keys[j]).toString());
            }
            // 第六步，将文件存到指定位置
            try {
                long timeMillis = System.currentTimeMillis();
                File path= new File("D:/temp-rainy/file/");
                if (!path.exists()){
                    path.mkdirs();
                }
                url="/temp-rainy/file/"+timeMillis+"Members.xls";
                File file= new File("D:"+url);
                if(!file.exists()) {
                file.createNewFile();
                }
                FileOutputStream fout = new FileOutputStream(file);
                wb.write(fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
      return url;
    }

}
