package IDAO;
import java.util.List;

/**
 * The interface Idao.
 *
 * @param <T> the type parameter
 */
public interface IDAO<T> {
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
