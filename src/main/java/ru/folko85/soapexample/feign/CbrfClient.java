package ru.folko85.soapexample.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ru.cbr.web.GetCursOnDate;
import ru.cbr.web.GetCursOnDateResponse;
import ru.folko85.soapexample.config.SOAPConfig;

import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@FeignClient(name = "CbrfClient", url = "http://www.cbr.ru/", configuration = {SOAPConfig.class})
public interface CbrfClient {

    @PostMapping(value = "/DailyInfoWebServ/DailyInfo.asmx?WSDL",
            consumes = TEXT_XML_VALUE, produces = TEXT_XML_VALUE,
            headers = {"SOAPAction=http://web.cbr.ru/GetCursOnDate"})
    GetCursOnDateResponse getCurses(@RequestBody GetCursOnDate getCursOnDate);
}
