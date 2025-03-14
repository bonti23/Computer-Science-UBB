using NBA_League.domain;
using NBA_League.exceptions;

namespace NBA_League.validator;

public class GameValidator : Validator<Game>
{
    public void validate(Game entity)
    {
        string errrorMess = "";
        if (entity.Date.CompareTo(DateTime.Today) > 0)
            errrorMess += "Invalid date!";
        if (errrorMess.Length > 0)
            throw new ValidatorException(errrorMess);
    }
}
