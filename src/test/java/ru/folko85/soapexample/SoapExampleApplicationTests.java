package ru.folko85.soapexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import ru.folko85.soapexample.feign.CbrfClient;

@SpringBootTest
public abstract class SoapExampleApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;
}
