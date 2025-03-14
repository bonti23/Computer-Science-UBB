namespace NBA_League.exceptions;

public class ServiceException : Exception
{
    private string mess;
    public ServiceException(string mess)
    {
        this.mess = mess;
    }
    public string GetMessage()
    {
        return this.mess;
    }
}
