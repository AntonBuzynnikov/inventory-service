package ru.buzynnikov.inventory_service.controllers.dto;

// Импортируем аннотации Jackson для поддержки полиморфной десериализации JSON
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Базовый класс DTO-запроса для создания ингредиента.
 *
 * Класс помечен аннотациями {@link JsonTypeInfo} и {@link JsonSubTypes}, позволяющими определить
 * конкретный подкласс при десериализации JSON-данных. Это позволяет поддерживать разные типы запросов
 * на создание ингредиентов ("base" — базовый запрос, "full" — расширенный).
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Тип определяется именем поля type
        property = "type"           // Имя поля в JSON, определяющее тип запроса
)
@JsonSubTypes({                     // Перечисляем возможные типы классов для данной иерархии
        @JsonSubTypes.Type(value = IngredientCreateRequest.class, name = "base"),       // Базовый тип
        @JsonSubTypes.Type(value = FullIngredientCreateRequest.class, name = "full")    // Расширенный тип
})
public class IngredientCreateRequest {

    /**
     * Название ингредиента.
     */
    protected String name;

    /**
     * Геттер для имени ингредиента.
     *
     * @return имя ингредиента
     */
    public String getName() {
        return name;
    }

    /**
     * Сеттер для установки имени ингредиента.
     *
     * @param name новое значение имени ингредиента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Переопределённый метод toString(), возвращающий строковое представление класса.
     *
     * @return строковое представление объекта с указанием значения свойства name
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IngredientCreateRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
