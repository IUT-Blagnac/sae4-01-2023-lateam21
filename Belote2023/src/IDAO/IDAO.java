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
    public void add(T obj);

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete (int id);

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    public T getOne(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<T> getAll();
}
