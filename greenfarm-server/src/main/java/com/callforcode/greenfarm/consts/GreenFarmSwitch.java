package com.callforcode.greenfarm.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GreenFarmSwitch {

    private static boolean sessionAuthentication;

    @Value("${green-farm.session.authentication.enable}")
    private void setSessionAuthentication(boolean sessionSwitch) {
        sessionAuthentication = sessionSwitch;
    }

    public static boolean isSessionAuthenticationOn() {
        return sessionAuthentication;
    }

}
