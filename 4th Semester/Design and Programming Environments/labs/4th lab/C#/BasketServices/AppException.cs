namespace BasketServices;

public class AppException : Exception
{
    public AppException() : base()
    {
    }

    public AppException(String msg) : base(msg)
    {
    }

    public AppException(String msg, Exception ex) : base(msg, ex)
    {
    }
}
