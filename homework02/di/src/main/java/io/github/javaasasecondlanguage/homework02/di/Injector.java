package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Агент внедрения зависимостей.
 */
public class Injector {

    /**
     * Получить объект внедряемого класса.
     * <p>
     * Объект внедряемого класса должен быть зарегистрирован в {@link Context}.
     * Допускается преждевременное получение объекта внедряемого класса
     * до его регистрации в {@link Context}, если класс является интерфейсом
     * и до регистрации объекта не было вызвано ни одного метода инъектированного объекта.
     * <p>
     * При получении объекта внедряемого класса в первую очередь ищется зарегистрированный объект
     * с полным совпадением по классу.
     * Если таковой не нашелся то производится поиск среди объектов подклассов нужного класса.
     * Порядок получения объектов подклассов не определен.
     * <p>
     * Для получения объекта точно определенного класса можно воспользоваться квалификаторами.
     * См. {@link Injector#inject(Class, String)}.
     *
     * @param clazz класс объекта
     * @return объект внедряемого класса.
     * Может быть null, если объект не был зарегистрирован ранее с помощью {@link Context}
     * и класс объекта не является интерфейсом.
     */
    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, null);
    }

    /**
     * Получить объект внедряемого класса с указанным квалификатором.
     * <p>
     * Объект внедряемого класса должен быть зарегистрирован в {@link Context}.
     * Допускается преждевременное получение объекта внедряемого класса
     * до его регистрации в {@link Context}, если класс является интерфейсом
     * и до регистрации объекта не было вызвано ни одного метода инъектированного объекта.
     * <p>
     * При получении объекта внедряемого класса в первую очередь ищется зарегистрированный объект
     * с полным совпадением по классу.
     * Если таковой не нашелся то производится поиск среди объектов подклассов нужного класса.
     * Порядок получения объектов подклассов не определен.
     *
     * @param clazz     класс объекта
     * @param qualifier квалификатор зависимости. Может быть null.
     *                  В этом случае вызов метода эквивалентен вызову
     *                  метода {@link Injector#inject(Class)}
     * @return объект внедряемого класса.
     * Может быть null, если объект не был зарегистрирован ранее с помощью {@link Context}
     * и класс объекта не является интерфейсом.
     */
    public static <T> T inject(Class<T> clazz, String qualifier) {
        T dependency = getDependency(clazz, qualifier);
        if (dependency != null) {
            return dependency;
        }
        if (!clazz.isInterface()) {
            return null;
        }
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new DiInvocationHandler(clazz, qualifier));
    }

    /**
     * Получить инжектируемый объект нужного класса с указанным квалификатором(опционально).
     * Сначала ищется объект требуемого класса,
     * если таковой не нашелся, ищется любой подходящий объект подкласса переданного класса.
     *
     * @param clazz     класс зависимости.
     * @param qualifier квалификатор зависимости, может быть null
     * @return объект зависимости.
     */
    protected static <T> T getDependency(Class<T> clazz, String qualifier) {
        T dependency = null;
        List<Object> qualifiedDependencies;
        if (qualifier != null) {
            qualifiedDependencies = Context.getDependencies().get(qualifier);
        } else {
            qualifiedDependencies = Context.getDependencies().values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
        if (qualifiedDependencies != null) {
            dependency = (T) getExactInstance(
                    qualifiedDependencies,
                    clazz);
            if (dependency == null) {
                dependency = (T) getAnySubclassObject(
                        qualifiedDependencies,
                        clazz);
            }
        }
        return dependency;
    }

    /**
     * Найти в списке объект определенного класса, с точностью до equals().
     *
     * @param objects список объектов
     * @param clazz   класс искомого объекта
     * @return объект требуемого класса.
     */
    private static Object getExactInstance(List<Object> objects, Class<?> clazz) {
        return objects.stream()
                .filter(o -> o.getClass().equals(clazz))
                .findAny().orElse(null);
    }

    /**
     * Найти в списке объект являющийся экземпляром переданного класса.
     * Например
     *
     * <pre>{@code
     *      getAnySubclassObject(List.of(3, "asdfa", new int[3]), Number.class) == 3
     * }</pre>
     *
     * @param objects    список объектов, среди которых производится поиск
     * @param superClass класс, объекты  которого необходимо найти.
     * @return объект класса или null в случае отсутствия подходящего объекта в переданном списке.
     */
    private static Object getAnySubclassObject(List<Object> objects, Class<?> superClass) {
        return objects.stream()
                .filter(superClass::isInstance)
                .findAny().orElse(null);
    }
}
