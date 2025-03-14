namespace NBA_League.exceptions;

public class ValidatorException : Exception
{
    private string mess;
    public ValidatorException(string mess)
    {
        this.mess = mess;
    }
    public string GetMessage()
    {
        return this.mess;
    }
}
