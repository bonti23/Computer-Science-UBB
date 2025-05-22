namespace BasketNetworking.JsonProtocol;


public enum RequestType
{
    LOGIN,
    LOGOUT,
    SIGNUP,
    FILTER_GAMES,
    ADD_PURCHASE,
    FIND_BY_CLIENT,
    UPDATE_SEATS,
    CHECK_USERNAME
}

[Serializable]
public class Request
{
    public RequestType Type { get; }
    public Object Data { get; }

    public Request(RequestType type, Object data)
    {
        this.Type = type;
        this.Data = data;
    }
    
    
}