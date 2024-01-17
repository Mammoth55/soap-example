package ru.folko85.soapexample.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import ru.folko85.soapexample.model.ValuteCurs;

import generated.ValuteData;

@Service
public class ValuteCursMapper implements Function<ValuteData.ValuteCursOnDate, ValuteCurs> {

    @Override
    public ValuteCurs apply(ValuteData.ValuteCursOnDate valuteCursOnDate) {
        return new ValuteCurs(valuteCursOnDate.getVname(), valuteCursOnDate.getVcurs());
    }
}
