package ru.folko85.soapexample.service;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.folko85.soapexample.SoapExampleApplicationTests;
import ru.folko85.soapexample.model.ValuteCurs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CbrfCursesServiceTest extends SoapExampleApplicationTests {

    @Autowired
    private CbrfCursesService cbrfCursesService;

    @Test
    public void testGetCurses() throws DatatypeConfigurationException, JAXBException, TransformerException, XMLStreamException {
        List<ValuteCurs> curses = cbrfCursesService.getCurses();
        Assertions.assertEquals(43, curses.size());
    }
}
