package com.ioteam.order_management_platform.utils.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

	String userId() default "d2ed72d8-090a-4efb-abe4-7acbdce120e1";

	String username() default "testCustomer";

	String nickname() default "Rob Winch";

	String role() default "CUSTOMER";
}
