package com.trustconnect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:trustconnect;MODE=PostgreSQL;DB_CLOSE_DELAY=-1")
class TrustConnectApplicationTests {

	@Test
	void contextLoads() {
	}

}
