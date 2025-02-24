package com.ioteam.order_management_platform.global.utils;

import java.util.function.Supplier;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

public class QuerydslUtil {
	public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
		try {
			return new BooleanBuilder(f.get());
		} catch (Exception e) {
			return new BooleanBuilder();
		}
	}
}
