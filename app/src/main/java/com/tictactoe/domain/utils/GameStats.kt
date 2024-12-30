package domain.utils

enum class TURN(val type: Int) {
    O(-1),
    X(1),
    NONE(0)
}

enum class STATUS(val result: String) {
    X_WIN("X WON"),
    O_WIN("O WON"),
    DRAW("DRAW"),
    NONE("")
}

enum class AUTH_MESSSAGE (val text: String){
    EMPTY_FIELDS("Поля не должны быть пустыми"),
    SUCCESS_REGISTER("Успешная регистрация"),
    SUCCESS_LOGIN("Успешный вход"),
    UNSUCCESS_LOGIN("Некорректные данные входа"),
    PASSWORD_CONFLICT("Пароли не совпадают"),
    WEAK_PASSWORD("Ненадежный пароль"),
    USER_CONFLICT("Такой пользователь уже существует"),
    SERVER_REGISTRATION_ERROR("Ошибка при регистрации на стороне сервера"),
}

enum class OPPONENT(val type: String){
    COMPUTER("Computer"),
    PLAYER("Player")
}