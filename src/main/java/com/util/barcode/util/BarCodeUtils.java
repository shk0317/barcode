package com.util.barcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang.ObjectUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成条形码.
 * <p>
 * Company: 翌飞锐特
 * <p>
 *
 * @author liyulong
 * @version 1.0.0
 * @Date 2021-12-09 13:50
 * <p>
 **/
public class BarCodeUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static byte[] generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 2048;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
        if (width != null) {
            bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生产条形码
        bean.generateBarcode(canvas, message);
        try {
            canvas.finish();
        } catch (IOException e) {
            System.out.println("条形码生成失败："+e);
        }
        return ous.toByteArray();
    }

    public static byte[] generateBarCode39(String message, Double height, Double width, boolean withQuietZone, boolean hideText){
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        try {
            //Create the barcode bean
            Code39Bean bean = new Code39Bean();
            // 设置文本位置（包括是否显示）
            if (hideText) {
                bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            }
            final int dpi = 150;

            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar
            //width exactly one pixel
            bean.setWideFactor(2);
            bean.doQuietZone(false);

            //Open output file

            try {
                //Set up the canvas provider for monochrome JPEG output
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        ous, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                //Generate the barcode
                bean.generateBarcode(canvas, message);

                //Signal end of generation
                canvas.finish();
            } finally {
                ous.close();
            }
        } catch (Exception e) {
            System.out.println("条形码生成失败："+e);
        }
        return ous.toByteArray();
    }

    public static byte[] genBarCode128(String message,Double height, Double width){
        return genBarCode128(message,height,width,true);
    }

    public static byte[] genBarCode128(String message,Double height, Double width, Boolean hideText){
        return generateBarCode128(message, height, width, false, hideText);
    }

}
