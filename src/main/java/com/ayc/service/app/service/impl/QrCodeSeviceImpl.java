
package com.ayc.service.app.service.impl;

import com.ayc.service.app.dto.GenroScanQrRequest;
import com.ayc.service.app.common.utils.qr.QRCodeUtil;
import com.ayc.service.app.configs.AppGlobalConfigs;
import com.ayc.service.app.service.QrCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Author:  ysj
 * Date:  2019/1/15 17:44
 * Description:
 */
@Service("qrCodeService")
public class QrCodeSeviceImpl implements QrCodeService {

    @Autowired
    private AppGlobalConfigs appGlobalConfigs;//当前应用的具体URL地址

    @Override
    public String createQrCodeProp(GenroScanQrRequest request, String methodName) throws Exception{
        String baseUrl = appGlobalConfigs.getUrl();
        return baseUrl + methodName +"?"+ parseURLPair(request);
    }

    private String parseURLPair(Object o) throws Exception{
        Class<? extends Object> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> map = new TreeMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(o);
            if(value != null)
                map.put(name, value);
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    @Override
    public void printGenroQr(GenroScanQrRequest request, HttpServletResponse response) {
        try{
            String baseUrl = appGlobalConfigs.getWebUrl();
            //设置协议内容二维码
            request.setScanQrValue(baseUrl + "/register?uid=" + request.getUid());
            byte[] bytex = qrBytes(request);
            QRCodeUtil.wirteOut(bytex, response.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //生成二维码数组
    private byte[] qrBytes(GenroScanQrRequest request) throws Exception{
        byte[] bytex = null;
        if (StringUtils.isBlank(request.getUrl())) {
            bytex = QRCodeUtil.encodeByte(request.getScanQrValue(), "", request.getNeedIoc(), request.getQrSize(),
                    request.getWidth(), request.getHeight());
        } else {
            bytex = QRCodeUtil.encodeByte(request.getScanQrValue(), new URL(request.getUrl()), request.getNeedIoc(),
                    request.getQrSize(), request.getWidth(), request.getHeight());
        }
        return bytex;
    }
}