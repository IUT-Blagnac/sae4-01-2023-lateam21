package IDAO;
import java.util.List;

/**
 * The interface Idao.
 *
 * @param <T> the type parameter
 */
public interface IDAO<T> {
    /**
     * Add.
     *
     * @param obj the obj
     */
    void add(T obj);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete (int id);

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    T getOne(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<T> getAll();
}
