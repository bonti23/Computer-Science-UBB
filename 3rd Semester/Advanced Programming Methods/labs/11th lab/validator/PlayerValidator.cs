using NBA_League.domain;
using NBA_League.exceptions;

namespace NBA_League.validator;

public class PlayerValidator : Validator<Player>
{
    public void validate(Player entity)
    {
        string errormess = "";
        if (entity.Name.Length == 0)
            errormess.Concat("Student must have a name!\n");
        if (errormess.Length > 0)
            throw new ValidatorException(errormess);

    }

}
