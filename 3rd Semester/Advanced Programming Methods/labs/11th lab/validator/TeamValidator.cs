using NBA_League.domain;
using NBA_League.exceptions;

namespace NBA_League.validator;

public class TeamValidator : Validator<Team>
{
    public void validate(Team entity)
    {
        if (entity.Name.Length == 0)
            throw new ValidatorException("Team must have a name!\n");
    }
}
