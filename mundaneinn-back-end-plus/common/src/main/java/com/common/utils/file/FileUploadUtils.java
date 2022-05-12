package com.common.utils.file;

import com.common.constant.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传工具类
 *
 * @author
 */
public class FileUploadUtils {

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws IOException                    比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException {
        //assertAllowed(file, allowedExtension);
        //获取文件编码名
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        //String pathFileName = getPathFileName(baseDir, fileName);
        String pathFileName = Constants.RESOURCE_UPLOAD + "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件下载
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws IOException                    比如读写文件出错时
     */
    public static final String download(String baseDir, MultipartFile file)
            throws IOException {
        //获取文件编码名
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        //String pathFileName = getPathFileName(baseDir, fileName);
        String pathFileName = Constants.RESOURCE_DOWNLOAD + "/" + fileName;
        return pathFileName;
    }


    /**
     * 编码文件名  日期路径 即年/月/日 如2018/08/08 + 时间戳 + .文件后缀名
     */
    public static final String extractFilename(MultipartFile file) {
        String extension = getExtension(file);
        String fileName = DateFormatUtils.format(new Date(), "yyyy/MM/dd") + "/" + System.currentTimeMillis() + "." + extension;
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);
        desc.setWritable(true, true);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }
}
