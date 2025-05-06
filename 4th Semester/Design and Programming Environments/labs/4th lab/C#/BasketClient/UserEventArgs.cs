namespace BasketClient;

public enum UserEvent
{
    NewSeats
};

public class UserEventArgs : EventArgs
{
    private readonly UserEvent userEvent;
    private readonly Object data;

    public UserEventArgs(UserEvent userEvent, Object data)
    {
        this.userEvent = userEvent;
        this.data = data;
    }

    public UserEvent UserEventType
    {
        get{return userEvent;}
    }

    public object Data
    {
        get { return data; }
    }
}
