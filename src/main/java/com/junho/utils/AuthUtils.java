package com.junho.utils;

import com.junho.config.security.CustomUserDetails;
import com.junho.homepage.member.Member;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new ApiException(ErrorCode.AUTHENTICATION_INVALID);
        }

        return ((CustomUserDetails) authentication.getPrincipal()).getMember();
    }

}
