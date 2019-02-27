
package com.ayc.service.app.security;

import com.ayc.framework.security.SessionUser;
import com.ayc.service.uc.entity.AycUserEntity;

/**
 * Author:  ysj
 * Date:  2019/2/15 15:03
 * Description:APP端用户会话
 */
public class AppSessionUser extends SessionUser {

    private static final long serialVersionUID = 912248378761651150L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}