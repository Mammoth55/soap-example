package ru.folko85.soapexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import ru.folko85.soapexample.feign.CbrfClient;

@EnableFeignClients(clients = {CbrfClient.class})
@SpringBootApplication
public class SoapExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapExampleApplication.class, args);
    }

}
