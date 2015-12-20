package com.epam.hw1.model.impl;

import com.epam.hw1.model.Ticket;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Class is used to unmarshal list of tickets.
 *
 * @author Yevhen_Larikov
 */
@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketsBean {
    @XmlElement(name = "ticket")
    private List<TicketBean> tickets;

    public List<TicketBean> getTickets() {
        return tickets;
    }
}
