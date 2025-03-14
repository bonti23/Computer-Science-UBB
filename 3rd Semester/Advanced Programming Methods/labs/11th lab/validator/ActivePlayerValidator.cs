using NBA_League.domain;
using NBA_League.exceptions;

namespace NBA_League.validator;

 class ActivePlayerValidator : Validator<ActivePlayer>
{
    public void validate(ActivePlayer entity)
    {
        if (entity.ScoredPoints < 0)
            throw new ValidatorException("Invalid numer of points!");
    }
}
