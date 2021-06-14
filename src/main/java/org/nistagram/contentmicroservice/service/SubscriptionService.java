package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.SubscriptionDto;

public interface SubscriptionService {
    void subscribe(SubscriptionDto dto);

    void unsubscribe(SubscriptionDto dto);
}
