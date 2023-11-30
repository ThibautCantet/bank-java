package com.bank.query;

import com.bank.domain.EventStore;

public class GetBalanceQueryHandler {
    private final EventStore eventStore;

    public GetBalanceQueryHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public float handle(GetBalanceQuery query) {
        return eventStore.find(query.accountId()).getCurrentBalance();
    }
}
