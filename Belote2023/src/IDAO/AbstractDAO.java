package IDAO;

import main.Belote;

import java.sql.Connection;

public class AbstractDAO {protected Connection connection = Belote.getUniqueInstanceBelote();

}
