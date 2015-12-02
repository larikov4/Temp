package com.epam.hw1.oxm;

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
 * Class is used to unmarshal beans from files using OXM.
 *
 * @author Yevhen_Larikov
 */
@Service
public class OxmManager {
    private static final Logger LOG = Logger.getLogger(OxmManager.class);

    private Unmarshaller unmarshaller;

    @Autowired
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    /**
     * Unmarshals tickets from passed file.
     *
     * @param filename the file name
     * @return list of tickets
     */
    public List<TicketBean> unmarshalTickets(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            TicketsBean ticketsBean = (TicketsBean) this.unmarshaller.unmarshal(new StreamSource(fis));
            return ticketsBean.getTickets();
        } catch (IOException e) {
            LOG.error(e);
            return Collections.emptyList();
        }
    }

}
