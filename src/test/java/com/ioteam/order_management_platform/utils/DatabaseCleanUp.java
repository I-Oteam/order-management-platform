package com.ioteam.order_management_platform.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@TestComponent
@RequiredArgsConstructor
public class DatabaseCleanUp { //} implements InitializingBean {

	@PersistenceContext
	private final EntityManager entityManager;
	private List<String> tableNames;

	@PostConstruct
	public void afterPropertiesSet() {
		tableNames = entityManager.getMetamodel()
			.getEntities()
			.stream()
			.filter(entityType -> entityType
				.getJavaType()
				.getAnnotation(Entity.class) != null)
			.map(entityType -> {
				Table table = entityType
					.getJavaType()
					.getAnnotation(Table.class);

				return Objects.nonNull(table) && Objects.nonNull(table.name()) ?
					table.name() :
					convertToLowerUnderscore(entityType.getName());
			})
			.collect(Collectors.toList());
	}

	@Transactional
	public void execute() {
		// 쓰기 지연 저장소에 남은 SQL을 마저 수행
		entityManager.flush();
		// 연관 관계 매핑된 테이블이 있는 경우 참조 무결성을 해제해주고, TRUNCATE 수행
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

		for (String tableName : tableNames) {
			// 테이블 이름을 순회하면서, TRUNCATE 수행
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		}
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}

	private String convertToLowerUnderscore(String camelCase) {
		return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
	}
}