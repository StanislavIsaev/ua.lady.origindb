package ua.lady.origindb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void contextLoads() {
		log.info(Long.toString(productRepository.count()));
	}

}
