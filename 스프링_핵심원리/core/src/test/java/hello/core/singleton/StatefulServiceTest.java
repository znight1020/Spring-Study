package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 10000원 주문
        statefulService1.order("userA", 10000);

        // ThreadB: B 사용자 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: 사용자 A 주문 금액 조회
        // 사용자 A가 주문을 하고 조회하는 사이 사용자 B가 주문을 한 순간 가격이 바뀌어버린 상황
        int userAPrice = statefulService1.order("userA", 10000);
        System.out.println("price = " + userAPrice);

        Assertions.assertThat(statefulService1.order("userA", 10000)).isEqualTo(10000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}