package com.epam.hw1.oxm;

import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.TicketsBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @author Yevhen_Larikov
 */
@Service
public class OxmDao {
    private static final Logger LOG = Logger.getLogger(OxmDao.class);
    private static final String FILE_NAME = "./tickets.xml";

    private Unmarshaller unmarshaller;

    @Autowired
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public List<Ticket> unmarshalTickets() {
        URL resource = getClass().getClassLoader().getResource(FILE_NAME);
        if(resource==null){
            LOG.error("Cannot find file " + FILE_NAME);
            return Collections.emptyList();
        }
        try (FileInputStream fis = new FileInputStream(resource.getFile())) {
            TicketsBean tickets = (TicketsBean) this.unmarshaller.unmarshal(new StreamSource(fis));
            List<TicketBean> ticketsList = tickets.getTickets();
        } catch (IOException e) {
            LOG.error(e);
        }
        return Collections.emptyList();
    }

}
