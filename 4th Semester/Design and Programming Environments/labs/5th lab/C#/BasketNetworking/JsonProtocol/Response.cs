namespace BasketNetworking.JsonProtocol;

public enum ResponseType {
    OK,
    ERROR,
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    SIGNUP_SUCCESS,
    SIGNUP_FAILED,
    LOGOUT_SUCCESS,
    LOGOUT_FAILED,
    FILTERED_GAMES,
    PURCHASE_SUCCESS,
    PURCHASE_FAILED,
    PURCHASES_FOUND,
    UPDATE_SEATS_SUCCESS,
    UPDATE_SEATS_FAILED,
    USERNAME_EXISTS,
    USERNAME_NOT_FOUND,
    UPDATE
}
[Serializable]
public class Response
{
    public ResponseType Type { get; }
    public Object Data { get; set; }
    
    public Response(ResponseType type, Object data)
    {
        this.Type = type;
        this.Data = data;
    }
    

    
}