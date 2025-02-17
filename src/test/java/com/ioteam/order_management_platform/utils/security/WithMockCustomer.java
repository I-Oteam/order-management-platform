package com.ioteam.order_management_platform.utils.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomer {

    String userId() default "d2ed72d8-090a-4efb-abe4-7acbdce120e7";
    String username() default "testId";
    String nickname() default "Rob Winch";

}
