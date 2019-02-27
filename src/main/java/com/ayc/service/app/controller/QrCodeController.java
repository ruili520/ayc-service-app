package com.ayc.service.app.controller;

import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.dto.GenroScanQrRequest;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.QrCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/qr")
public class QrCodeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private QrCodeService qrCodeService;

    @RequestMapping("/index")
    public String index(){
        return "share";
    }

    /**
     * 生成推广二维码
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @RequestMapping(value = "getQrCode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public AjaxResult getQrCode(@RequestHeader String token, @RequestBody GenroScanQrRequest request){
        String qrCode = "";
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            qrCode = qrCodeService.createQrCodeProp(request, "/qr/promoteQrCode");
        } catch (Exception e) {
            logger.info("生成扫码内容异常,{}",e);
        }
        return AjaxResult.success().putObj(qrCode);
    }

    /**
     * 生成推广二维码
     * @param request
     * @param response
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @RequestMapping(value = "promoteQrCode", method = { RequestMethod.GET, RequestMethod.POST })
    public void generoQrCodeKey(@RequestHeader String token, GenroScanQrRequest request, HttpServletResponse response){
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            // 打印二维码内容
            qrCodeService.printGenroQr(request, response);
        } catch (Exception e) {
            logger.info("生成扫码内容异常,{}",e);
        }
    }
}
