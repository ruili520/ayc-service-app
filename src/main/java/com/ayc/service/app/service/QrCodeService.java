package com.ayc.service.app.service;

import com.ayc.service.app.dto.GenroScanQrRequest;

import javax.servlet.http.HttpServletResponse;

public interface QrCodeService {

    /**
     * 生成二维码图片链接
     * @param request
     * @return
     */
    String createQrCodeProp(GenroScanQrRequest request, String methodName) throws Exception;

    /**
     * 打印二维码
     * @param request
     * @param response
     */
    void printGenroQr(GenroScanQrRequest request, HttpServletResponse response);

}
