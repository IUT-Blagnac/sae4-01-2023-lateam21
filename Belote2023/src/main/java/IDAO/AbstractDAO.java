package IDAO;

import main.Belote;

import java.sql.Connection;

/**
 * The type Abstract dao.
 */
public class AbstractDAO {
    /**
     * The Connection.
     */
    protected Connection connection = Belote.getUniqueInstanceBelote();
}
