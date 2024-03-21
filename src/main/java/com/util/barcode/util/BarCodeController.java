package com.util.barcode.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program:barcode
 * @description:
 * @author:shihongkai
 * @create:2022-08-10
 **/
@RestController
@RequestMapping("/BarCode")
public class BarCodeController {
    @GetMapping("/generate")
    public void getBarCode(
            HttpServletResponse response,
            @RequestParam(value="data") String data,
            @RequestParam(value="width",required = false) String width,
            @RequestParam(value="height",required = false) String height,
            @RequestParam(value="hideText",required = false) Boolean hideText){
        if (StringUtils.isEmpty(width)){
            width = "0.42";
        }
        if (StringUtils.isEmpty(height)){
            height = "8";
        }
        if (hideText==null){
            hideText = true;
        }
        //设置返回的数据类型
        response.setContentType("image/jpeg;charset=utf-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = BarCodeUtils.genBarCode128(data, new Double(height), new Double(width), hideText);
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/brCode")
    public void brCode(
            HttpServletResponse response,
            @RequestParam(value="data") String data,
            @RequestParam(value="width",required = false) Integer width,
            @RequestParam(value="height",required = false) Integer height){
        //设置返回的数据类型
        response.setContentType("image/jpeg;charset=utf-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            QrCodeUtils.encode(data,"",outputStream,false,width,height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
