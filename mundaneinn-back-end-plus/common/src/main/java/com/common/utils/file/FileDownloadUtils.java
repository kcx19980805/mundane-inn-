package com.common.utils.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件下载
 *
 * @author song
 * @date 2020/12/25
 */
public class FileDownloadUtils {

    /**
     * 下载文件 zip格式 客户浏览器弹出下载弹窗
     *
     * @param fileList fileUrl:文件路径及 fileName自定义文件名 一一对应 文件名不要重复 (若fileName为空 则直接使用原文件名)
     * @param zipName  压缩包名
     * @param response
     * @param request
     */
    public static void downloadZipFiles(List<Map<String, Object>> fileList, String zipName, HttpServletResponse response, HttpServletRequest request) {
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        String downloadName = zipName + ".zip";
        //返回客户端浏览器的版本号、类型
        String agent = request.getHeader("USER-AGENT");
        try {
            //针对IE或者以IE为内核的浏览器：
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            // --设置成这样可以不用保存在本地，再输出， 通过response流输出,直接输出到客户端浏览器中。
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //设置压缩方法
            zipos.setMethod(ZipOutputStream.DEFLATED);
            zipFiles(fileList, zipos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 压缩文件
     *
     * @param fileList fileUrl:文件路径及 fileName自定义文件名 一一对应 文件名不要重复 (若fileName为空 则直接使用原文件名)
     * @param zipos
     */
    public static void zipFiles(List<Map<String, Object>> fileList, ZipOutputStream zipos) {

        byte[] b = new byte[2048];
        for (Map<String, Object> map : fileList) {
            try {
                String fileUrl = map.get("fileUrl").toString();
                File inputFile = new File(fileUrl);
                String fileName;
                if (map.containsKey("fileName") && StringUtils.isNotBlank(map.get("fileName").toString())) {
                    //自定义文件名称
                    fileName = map.get("fileName").toString();
                } else {
                    //原文件名称
                    fileName = inputFile.getName();
                }

                //判断文件是否存在
                if (inputFile.exists()) {
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
                    //将文件写入zip内，即将文件进行打包
                    zipos.putNextEntry(new ZipEntry(fileName));
                    //写入文件的方法，同上
                    int size = 0;
                    //设置读取数据缓存大小
                    while ((size = bis.read(b)) > 0) {
                        zipos.write(b, 0, size);
                    }
                    //关闭输入输出流
                    zipos.closeEntry();
                    bis.close();
                } else {
                    //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    File[] files = inputFile.listFiles();
                    List<Map<String, Object>> filePathsTem = new ArrayList<>();
                    for (File fileTem : files) {
                        Map<String, Object> m = new HashMap<>(16);
                        m.put("fileUrl", fileTem.toString());
                        filePathsTem.add(m);
                    }
                    zipFiles(filePathsTem, zipos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != zipos) {
                    try {
                        zipos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param fileList fileUrl:文件路径及 fileName自定义文件名 一一对应 文件名不要重复 (若fileName为空 则直接使用原文件名)
     */
    public static String zipFiles2(List<Map<String, Object>> fileList, String baseUrl) {
        ZipOutputStream zipos = null;

        String zipName = DateFormatUtils.format(new Date(), "yyyy/MM/dd") + "/" + System.currentTimeMillis() + ".zip";
        String filePath = baseUrl + zipName;
        System.out.println(filePath);
        File desc = new File(filePath);
        desc.setWritable(true, true);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            try {
                desc.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        zipos = new ZipOutputStream(new BufferedOutputStream(out));
        byte[] b = new byte[2048];

        for (Map<String, Object> map : fileList) {
            System.out.println(map);
            try {
                String fileUrl = map.get("fileUrl").toString();
                File inputFile = new File(fileUrl);
                String fileName;
                if (map.containsKey("fileName") && StringUtils.isNotBlank(map.get("fileName").toString())) {
                    //自定义文件名称
                    fileName = map.get("fileName").toString();
                } else {
                    //原文件名称
                    fileName = inputFile.getName();
                }

                //判断文件是否存在
                if (inputFile.exists()) {
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
                    //将文件写入zip内，即将文件进行打包
                    zipos.putNextEntry(new ZipEntry(fileName));
                    //写入文件的方法，同上
                    int size = 0;
                    //设置读取数据缓存大小
                    while ((size = bis.read(b)) > 0) {
                        zipos.write(b, 0, size);
                    }
                    //关闭输入输出流
                    zipos.closeEntry();
                    bis.close();
                } else {
                    //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    File[] files = inputFile.listFiles();
                    List<Map<String, Object>> filePathsTem = new ArrayList<>();
                    if (files != null && files.length > 0) {
                        for (File fileTem : files) {
                            Map<String, Object> m = new HashMap<>(16);
                            m.put("fileUrl", fileTem.toString());
                            filePathsTem.add(m);
                        }
                        zipFiles(filePathsTem, zipos);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != zipos) {
            try {
                zipos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipName;
    }
}
