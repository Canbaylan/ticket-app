package com.canbaylan.notification;

import com.canbaylan.messaging.TicketNotification;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class NotificationDistributionService {
    @StreamListener(Sink.INPUT)
    public void onNotification(TicketNotification ticketNotification){
        System.out.println("Notif alindi, gonderiliyor");
        System.out.println("Notif -> "+ticketNotification.toString());
    }
}
