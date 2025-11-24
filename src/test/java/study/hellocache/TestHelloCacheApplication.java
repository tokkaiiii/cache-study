package study.hellocache;

import org.springframework.boot.SpringApplication;

public class TestHelloCacheApplication {

    public static void main(String[] args) {
        SpringApplication.from(HelloCacheApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
