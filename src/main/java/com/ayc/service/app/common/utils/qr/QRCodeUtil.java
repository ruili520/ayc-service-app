package com.ayc.service.app.common.utils.qr;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Hashtable;

/**
 * 二维码工具类
 */
public class QRCodeUtil {

	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 300;
	// LOGO宽度
	private static final int WIDTH = 70;
	// LOGO高度
	private static final int HEIGHT = 70;
	/**
	 * 创建二维码图片
	 * @param content	内容
	 * @param imgPath	图片物理地址
	 * @param url		网络连接图片
	 * @param needCompress	需要压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @return
	 * @throws Exception
	 */
	private static BufferedImage createImage(String content, String imgPath,URL url,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		if(null==qrSize)qrSize = QRCODE_SIZE;
		if(null==widthx)widthx = WIDTH;
		if(null==heightx)heightx = HEIGHT;
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		//物理图片地址
		if(null!=imgPath&&!"".equals(imgPath)){
			QRCodeUtil.insertImage(image, imgPath, needCompress,qrSize,widthx,heightx);
		}else if(null!=url){
			//网络图片地址
			QRCodeUtil.insertImage(image,url,needCompress,qrSize,widthx,heightx);
		}
		
		return image;
	}

	/**
	 * 根据插入图片
	 * @param source	创建的二维码空图片
	 * @param src	          需要的图片
	 * @param needCompress 压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 */
	private static void insertImage(BufferedImage source, Image src,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx){
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > widthx.intValue()) {
				width = widthx.intValue();
			}
			if (height > heightx.intValue()) {
				height = heightx.intValue();
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qrSize.intValue() - width) / 2;
		int y = (qrSize.intValue() - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
	
	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println(""+imgPath+"   该文件不存在！");
			return;
		}
		
		Image src = ImageIO.read(new File(imgPath));
		insertImage(source,src,needCompress,qrSize,widthx,heightx);
	}
	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param url
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, URL url,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		Image src = ImageIO.read(url);
		insertImage(source,src,needCompress,qrSize,widthx,heightx);
	}
	/**
	 * 解析二维码
	 * @param image 需要解析图片
	 * @return
	 */
	private static String decode(BufferedImage image)throws Exception {
		if (image == null) {
			return null;
		}
		BuffImageLSource source = new BuffImageLSource(
				image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	private static void encode(String content, String imgPath,URL url,String destPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,url,needCompress,qrSize,widthx,heightx);
		mkdirs(destPath);
		String file = System.currentTimeMillis()+".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
	}
	/**
	 * 生成二维码
	 * @param content	内容
	 * @param imgPath	物理路径
	 * @param destPath	保存到那个夹中
	 * @param needCompress 是否需要压缩
     * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath,String destPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		QRCodeUtil.encode(content, imgPath, null, destPath, needCompress,qrSize,widthx,heightx);
	}
	/**
	 * 生成二维码
	 * @param content	内容
	 * @param url		网络路径
	 * @param destPath	保存到那个夹中
	 * @param needCompress 是否需要压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, URL url,String destPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		QRCodeUtil.encode(content, "", url, destPath, needCompress,qrSize,widthx,heightx);
	}
	/**
	 * 创建目录
	 * @param destPath
	 */
	public static void mkdirs(String destPath) {
		File file =new File(destPath);    
		//当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}
	/**
	 * 生成二维码包含LOGO
	 * @param content 内容
	 * @param imgPath 中间图片地址
	 * @param destPath 保存的地址
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 */
	public static void encode(String content,String imgPath,String destPath,Integer qrSize,Integer widthx,Integer heightx)throws Exception {
		QRCodeUtil.encode(content, imgPath,null,destPath, false,qrSize,widthx,heightx);
	}
	/**
	 * 生成二维码包含LOGO
	 * @param content 内容
	 * @param url 网络连接地址
	 * @param destPath 保存的地址
     * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 */
	public static void encode(String content,URL url,String destPath,Integer qrSize,Integer widthx,Integer heightx)throws Exception {
		QRCodeUtil.encode(content, null,url,destPath, false,qrSize,widthx,heightx);
	}
	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @param needCompress
	 *            是否压缩LOGO
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, String destPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		QRCodeUtil.encode(content, null,null,destPath, needCompress,qrSize,widthx,heightx);
	}
	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, String destPath,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		QRCodeUtil.encode(content, null, null,destPath, false,qrSize,widthx,heightx);
	}
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param output
	 *            输出流
	 * @param needCompress
	 *            是否压缩LOGO
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath,OutputStream output, boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,null,needCompress,qrSize,widthx,heightx);
		ImageIO.write(image, FORMAT_NAME, output);
	}
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param url 网络图片地址
	 * @param output
	 *            输出流
	 * @param needCompress
	 *            是否压缩LOGO
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content, URL url,OutputStream output, boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content,null,url,needCompress,qrSize,widthx,heightx);
		ImageIO.write(image, FORMAT_NAME, output);
	}
	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param output
	 *            输出流
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @throws Exception
	 */
	public static void encode(String content,OutputStream output,Integer qrSize,Integer widthx,Integer heightx)
			throws Exception {
		QRCodeUtil.encode(content,"", output, false,qrSize,widthx,heightx);
	}
	/**
	 * 根据物理图片地址解析二维码
	 * @param imagePath 物理地址
	 * @return
	 * @throws Exception
	 */
	public static String decode(String imagePath) throws Exception{
		File file = new File(imagePath);
		if(file.exists()&&file.isFile()){
			BufferedImage image = ImageIO.read(file);
			return QRCodeUtil.decode(image);
		}
		return "";
	}
	/**
	 * 解析二维码
	 * @param url 网络地址图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(URL url) throws Exception{
		BufferedImage image = ImageIO.read(url);
		return QRCodeUtil.decode(image);
	}
	
	/**
	 * 返回图片字节数组(内嵌LOGO)
	 * @param content 二维码内容
	 * @param imgPath 中间图片物理地址
	 * @param needCompress 是否压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @return byte[]
	 * @throws Exception 
	 */
	public static byte[] encodeByte(String content, String imgPath,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception{
		//字节流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,null,needCompress,qrSize,widthx,heightx);
		
		boolean flag = ImageIO.write(image, FORMAT_NAME, out);
		
		if(flag)return out.toByteArray();
		
		else return null;
	}
	/**
	 * 返回图片字节数组(内嵌LOGO)
	 * @param content 二维码内容
	 * @param imgPath 中间图片物理地址
	 * @param needCompress 是否压缩
	 * @param qrSize	二维码宽高为null默认
	 * @param widthx	中间图片宽度为null默认
	 * @param heightx	中间图片高度为null默认
	 * @return byte[]
	 * @throws Exception 
	 */
	public static byte[] encodeByte(String content, URL url,boolean needCompress,Integer qrSize,Integer widthx,Integer heightx) throws Exception{
		//字节流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage image = QRCodeUtil.createImage(content, null,url,needCompress,qrSize,widthx,heightx);
		
		boolean flag = ImageIO.write(image, FORMAT_NAME, out);
		
		if(flag)return out.toByteArray();
		
		out.close();
		return null;
	}
	/**
	 * 将字节数组保存为图片<br/>
	 * 返回图片名称
	 * @param bytex		字节数组
	 * @param destPath	最终保存文件夹
	 * @return
	 */
	public static String saveImage(byte[] bytex,String destPath) throws Exception{
		mkdirs(destPath);
		String file = System.currentTimeMillis()+".jpg";
		
		FileOutputStream fout = new FileOutputStream(destPath+"/"+file);
        //将字节写入文件
        fout.write(bytex);
        fout.close();
        
        return destPath+"/"+file;
	}
	
	/**
	 * 将字节输出到输出流中
	 * @param bytex			字节数组
	 * @param outputStream	输出流
	 * @throws Exception
	 */
	public static void wirteOut(byte[] bytex,OutputStream outputStream) throws Exception{
		ByteArrayInputStream in = new ByteArrayInputStream(bytex);
		BufferedImage image = ImageIO.read(in);
		in.close();
		ImageIO.write(image, FORMAT_NAME, outputStream);
	}
	/*
	public static void main(String[] args) throws Exception {
		String text = "我的测试数据";
		//QRCodeUtil.encode(text, "c:/a.jpg", "c:/a/", true);
		//QRCodeUtil.encode(text,new URL("http://image11.m1905.cn/uploadfile/2009/0910/858/20090910052316910.jpg"), "c:/a/", false);
		//String x = QRCodeUtil.decode(new URL("http://www.jf258.com/touxiang/img_cqcbepaper.cqnews.net/cqcb/res/1/20111121/78381321816855078.jpg"));
		//System.out.println(x);
		
		byte[] bytex = QRCodeUtil.encodeByte(text, new URL("http://g.hiphotos.baidu.com/image/pic/item/a8ec8a13632762d0b1d17507a2ec08fa503dc69f.jpg"), true,null,null,null);
		System.out.println(bytex.length);
		//QRCodeUtil.saveImage(bytex,"c:/a/");
		
		FileOutputStream fout = new FileOutputStream("c:/a"+"/"+"aaaaa.jpg");
		QRCodeUtil.wirteOut(bytex, fout);
		String s = UUID.randomUUID().toString();
		s = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		System.out.println(s.length()+","+s);
		//QRCodeUtil c = new QRCodeUtil();
		//System.out.println(c.getClass().getResource("/").getPath().getPath()); 
		
	}*/
}
