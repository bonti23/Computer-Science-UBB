namespace NBA_League.exceptions;

public class InputException : Exception
{
    private string mess;
    public InputException(string mess)
    {
        this.mess = mess;
    }
    public string GetMessage()
    {
        return this.mess;
    }
}
