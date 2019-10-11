package com.yb.base.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.yb.base.pojo.PartsEntity;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by mayn on 2019/9/4.
 */
public  class VoiceUtil {


        // 程序入口
        public static void main(String[] args) {
            try {
                voice();
            } catch (IOException e) {

            }
        }

        public static synchronized void voice() throws IOException {

            // 拿到音响
            ActiveXComponent sap = new ActiveXComponent("sapi.SpVoice");
            // 找到本地要朗读的文件
            try {
                // 调节语速 音量大小
                sap.setProperty("Volume", new Variant(100));
                sap.setProperty("Rate", new Variant(0));

                Dispatch xj = sap.getObject();
                File srcFile = new File("D:/A/b.txt");

                // 从缓存区拿到数据
                FileInputStream is = new FileInputStream(srcFile);
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(reader);
                // 拿到缓冲区的数据
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    Dispatch.call(xj, "Speak", new Variant(line));

                }
                xj.safeRelease();
                bufferedReader.close();
                reader.close();
               is.close();
                // 执行朗读 没有读完就继续读


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                sap.safeRelease();
            }

        }
    public static void Write2FileByOutputStream(Map<String,Object> map) {
        File file = new File("D:/A/b.txt");
        FileOutputStream fos = null;
        //BufferedOutputStream bos = null;
        OutputStreamWriter osw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            //bos=new BufferedOutputStream(fos);
            osw = new OutputStreamWriter(fos);
            PartsEntity part =(PartsEntity) map.get("part");
            List<Map<String,Object>> list=( List<Map<String,Object>>) map.get("list");
            osw.write("部件名称是:"+part.getPart_name()+"\n");
            osw.write("部件数量是:"+part.getOrder_number()+"\n");
            osw.write("部件规格型号:"+part.getPart_spec()+"\n");
            osw.write("部件位置:"+part.getPart_position()+"\n");
            osw.write("可能产生的危险是:"+"\n");
            for (Map<String,Object> list1:list){
                String pro_name = (String) list1.get("pro_name");
                String content_name = (String) list1.get("content_name");
                osw.write(pro_name+":"+content_name+"\n");
            }
            osw.write("部件介绍:"+"\n");
            osw.write(part.getPart_present()+"\n");
            osw.write("部件原理:"+"\n");
            osw.write(part.getPart_principle()+"\n");
            // bos = new BufferedOutputStream(fos);
            // bos.write("Write2FileByOutputStream".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
