package bonti.rest.client;

public record PurchaseRequest(
        String clientName,
        String address,
        int seats,
        Long gameId
) {}