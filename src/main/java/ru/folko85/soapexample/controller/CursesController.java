package ru.folko85.soapexample.controller;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.folko85.soapexample.model.ValuteCurs;
import ru.folko85.soapexample.service.CbrfCursesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Контроллер получения курсов")
@RestController
@RequiredArgsConstructor
public class CursesController {

    private final CbrfCursesService cbrfCursesService;

    @GetMapping("/api/v1/getCurses")
    @Operation(description = "Получение курсов валют")
    public List<ValuteCurs> getCurses() throws DatatypeConfigurationException, XMLStreamException, JAXBException, TransformerException {
        return cbrfCursesService.getCurses();
    }
}
