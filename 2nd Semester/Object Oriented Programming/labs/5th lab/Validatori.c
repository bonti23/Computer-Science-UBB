#include <string.h>
#include <assert.h>

int validateZiua(int ziua)
{
    if (ziua <= 0 || ziua > 31)
        return 1;
    return 0;
}

int validateSuma(int suma)
{
    if (suma < 0)
        return 1;
    return 0;
}

int validateTip(char tip[])
{
    if (strcmp(tip, "mancare") != 0 && strcmp(tip, "transport") != 0 && strcmp(tip, "internet") != 0 && strcmp(tip, "telefon") != 0 && strcmp(tip, "imbracaminte") != 0 && strcmp(tip, "altele") != 0)
        return 1;
    return 0;
}

void testValidate()
{
    // test pentru validarea zilei
    assert(validateZiua(45) == 1);
    assert(validateZiua(-12) == 1);
    assert(validateZiua(23) == 0);

    // test pentru validarea sumei
    assert(validateSuma(-131) == 1);
    assert(validateSuma(400) == 0);

    // test pentru validarea tipului
    assert(validateTip("mancare") == 0);
    assert(validateTip("internet") == 0);
    assert(validateTip("altele") == 0);
    assert(validateTip("telefon") == 0);
    assert(validateTip("transport") == 0);
    assert(validateTip("imbracaminte") == 0);
    assert(validateTip("chirie") == 1);
}
