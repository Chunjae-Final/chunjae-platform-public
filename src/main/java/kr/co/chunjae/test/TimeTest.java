package kr.co.chunjae.test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeTest {
    public static void main(String[] args) throws InterruptedException {
        LocalDateTime now1 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("now = " + now1);
        Thread.sleep(5000);
        LocalDateTime now2 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("now2 = " + now2);
        Duration duration = Duration.between(now1, now2);

        System.out.println("duration = " + duration.getSeconds());
    }
}
