package com.common.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

/**
 * 生成二维码工具
 *
 * @author: song
 * @data: 2020/10/20 23:14
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "png";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 200;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;


    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //边距
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param outPutPath   二维码保存路径
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String outPutPath, boolean needCompress)
            throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, needCompress);

        File newFile = new File(outPutPath);
        newFile.setWritable(true, true);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        ImageIO.write(image, FORMAT, newFile);
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String outPutPath = "D:/QrCode/" + System.currentTimeMillis() + "_" + (int) (1 + Math.random() * 10) + ".JPG";
            QrCodeUtils.encode("https://qr.alipay.com/bax088841lq9tlmt9fkg0078" , outPutPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
