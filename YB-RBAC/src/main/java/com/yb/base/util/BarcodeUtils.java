package com.yb.base.util;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/8/19.
 */
public class BarcodeUtils {
    // 设置条形码的高度
    private static final int BARCODE_HEIGHT = 65;
    // 设置条形码的分辨率
    private static final int BARCODE_DPI = 300;

    public static void createBarcode(String message, String fileName)
    {
        try
        {
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
            //生成. 欧洲商品条码(=European Article Number)
            //这里我们用作图书条码
            BufferedImage localBufferedImage = localJBarcode.createBarcode(message);
            saveToGIF(localBufferedImage, fileName);
            localJBarcode.setEncoder(Code39Encoder.getInstance());
            localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
            localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
            localJBarcode.setBarHeight(BARCODE_HEIGHT);
            localJBarcode.setShowCheckDigit(false);


        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    static void saveToJPEG(BufferedImage paramBufferedImage, String paramString)
    {
        saveToFile(paramBufferedImage, paramString, "jpeg");
    }

    static void saveToPNG(BufferedImage paramBufferedImage, String paramString)
    {
        saveToFile(paramBufferedImage, paramString, "png");
    }

    static void saveToGIF(BufferedImage paramBufferedImage, String paramString)
    {
        saveToFile(paramBufferedImage, paramString, "gif");
    }

    static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2)
    {
        try
        {
            FileOutputStream localFileOutputStream = new FileOutputStream("D:/A/" + paramString1);
            ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, BARCODE_DPI, BARCODE_DPI);
            localFileOutputStream.close();
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    //测试
    public static void main(String[] args){
        String message="788515004012";
        String fileName ="788515004012.jpg";
        BarcodeUtils.createBarcode(message, fileName);
    }


}
