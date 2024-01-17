package ru.folko85.soapexample.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.cbr.web.GetCursOnDate;
import ru.cbr.web.GetCursOnDateResponse;
import ru.folko85.soapexample.feign.CbrfClient;
import ru.folko85.soapexample.mapper.ValuteCursMapper;
import ru.folko85.soapexample.model.ValuteCurs;

import generated.ValuteData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CbrfCursesService {

    private final CbrfClient cbrfClient;
    private final ValuteCursMapper valuteCursMapper;

    public List<ValuteCurs> getCurses() throws DatatypeConfigurationException, JAXBException, TransformerException, XMLStreamException {
        GetCursOnDate request = new GetCursOnDate();
        LocalDate date = LocalDate.now();
        GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        request.setOnDate(xcal);
        GetCursOnDateResponse response = cbrfClient.getCurses(request);

        Object valutesData = response.getGetCursOnDateResult().getAny();
        String xml = transformElement(valutesData);
        return unmarshallObject(xml).stream().map(valuteCursMapper).toList();
    }

    private String transformElement(Object valutesData) throws TransformerException {
        Document doc = ((Element) valutesData).getOwnerDocument();
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }

    private List<ValuteData.ValuteCursOnDate> unmarshallObject(String valutesData) throws JAXBException, XMLStreamException {
        JAXBContext jc = JAXBContext.newInstance(ValuteData.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringReader reader = new StringReader(valutesData);
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(reader);
        xsr = xif.createFilteredReader(xsr, xsr1 -> {
            if (xsr1.isStartElement() || xsr1.isEndElement()) {
                return !"urn:schemas-microsoft-com:xml-diffgram-v1".equals(xsr1.getNamespaceURI());
            }
            return true;
        });
        ValuteData valuteData = (ValuteData) unmarshaller.unmarshal(xsr);
        return valuteData.getValuteCursOnDate();
    }
}
