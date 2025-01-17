package com.aleksandrgenrikhs.nivkhalphabetcompose

enum class Letters(
    val title: String,
    val isCompleted: Boolean = false,
) {
    A("Аа"),
    B("Бб"),
    V("Вв"),
    G("Гг"),
    Ng("Ӷӷ"),
    Gh("Ғғ"),
    Dj("Ӻӻ"),
    D("Дд"),
    E("Ее"),
    Yo("Ёё"),
    Zh("Жж"),
    Z("Зз"),
    I("Ии"),
    J("Йй"),
    K("Кк"),
    KK("Кʼкʼ"),
    Q("Ӄӄ"),
    QK("Ӄʼӄʼ"),
    L("Лл"),
    M("Мм"),
    N("Нн"),
    Nng("Ӈӈ"),
    O("Оо"),
    P("Пп"),
    PP("Пʼпʼ"),
    R("Рр"),
    Rr("Р̆р̆"),
    S("Сс"),
    T("Тт"),
    TT("Тʼтʼ"),
    U("Уу"),
    UU("Ўў"),
    F("Фф"),
    H("Хх"),
    Qh("Ӿӿ"),
    QQ("Ӽӽ"),
    Tsc("Цц"),
    Ch("Чч"),
    Sh("Шш"),
    Shch("Щщ"),
    HARD("Ъъ"),
    Y("Ыы"),
    MM("Ьь"),
    EE("Ээ"),
    YU("Юю"),
    Ya("Яя");

    companion object {
        fun getById(stableId: String): Letters? = entries.find { it.title == stableId }
    }
}
