package com.vanguard.trade.service;

import com.vanguard.trade.exception.EventProcessingException;
import com.vanguard.trade.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventFileReaderService {
    Logger logger = LoggerFactory.getLogger(EventFileReaderService.class);

    @Value("${event.files.base.path:input}")
    private String inputDirectory;

    @Value("${event.files.is.archive:false}")
    private boolean isArchive;

    @Value("${event.files.archive.path:archive}")
    private String archiveDirectory;

    @Autowired
    private ApplicationContext ctx;

    public List<Event> readFiles() {
        List<Event> events = new ArrayList<>();
        try {
            Resource resource = ctx.getResource("classpath:" + inputDirectory);
            File folder = resource.getFile();
            File[] listOfFiles = folder.listFiles();

            assert listOfFiles != null;
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Event event = readFile(file);
                    if (null != event) {
                        events.add(event);
                    }
                    if (isArchive) {
                        archiveFile(file);
                    }
                }
            }
        } catch (IOException exception) {
            logger.error("Event processing failed");
            throw new EventProcessingException("Event processing failed");
        }
        return events;
    }

    private void archiveFile(File file) throws IOException {
        Resource resource = ctx.getResource("classpath:" + archiveDirectory);
        File folder = resource.getFile();
        Path sourcepath = Paths.get(file.toURI());
        Path destinationepath = Paths.get(folder.getAbsolutePath() + "/" + file.getName());
        Files.move(sourcepath, destinationepath, StandardCopyOption.COPY_ATTRIBUTES);
    }

    private Event readFile(File file) {

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();
            Event event = new Event();
            event.setBuyerParty((String) xPath.evaluate("//buyerPartyReference/@href", document, XPathConstants.STRING));
            event.setSellerParty((String) xPath.evaluate("//sellerPartyReference/@href", document, XPathConstants.STRING));
            event.setPremiumAmount((Double) xPath.evaluate("//paymentAmount/amount", document, XPathConstants.NUMBER));
            event.setPremiumCurrency((String) xPath.evaluate("//paymentAmount/currency", document, XPathConstants.STRING));
            return event;
        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

