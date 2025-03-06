package com.ioteam.order_management_platform.global;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.ioteam.order_management_platform.utils.DatabaseCleanUp;

@Import(DatabaseCleanUp.class)
@SpringBootTest
public class IntegrationBaseTest extends BaseTest {

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.execute();
	}
}
