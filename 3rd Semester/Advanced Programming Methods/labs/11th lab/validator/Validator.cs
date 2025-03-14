namespace NBA_League.validator;

interface Validator<E>
{
    public void validate(E entity);
}
