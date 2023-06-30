package models;

public class UserModel {
    public String first_name;
    public String last_name;
    public String id;
    public boolean is_closed;
    public String deactivated;

    @Override
    public String toString() {
        return "%s (%s %s) -> %s".formatted(id, last_name, first_name, (is_closed) ? "Закрытый": "Открытый");
    }
}
