package com.canbaylan.ticketservice.repository.es;

import com.canbaylan.ticketservice.model.es.TicketModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TicketElasticRepository extends ElasticsearchRepository<TicketModel,String> {
}
