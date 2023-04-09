package resources;

/**
 * La classe Tools contient des méthodes utilitaires.
 */
public class Tools {

    /**
     * Échappe les caractères spéciaux dans une chaîne de caractères pour éviter les injections SQL.
     *
     * @param str : la chaîne de caractères à échapper
     * @return la chaîne de caractères échappée
     */
    public static String mysql_real_escape_string(String str) {
        if (str == null) {
            return null;
        }
        if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
            return str;
        }
        String clean_string = str;
        clean_string = clean_string.replaceAll("\\n","\\\\n");
        clean_string = clean_string.replaceAll("\\r", "\\\\r");
        clean_string = clean_string.replaceAll("\\t", "\\\\t");
        clean_string = clean_string.replaceAll("\\00", "\\\\0");
        clean_string = clean_string.replaceAll("'", "''");
        return clean_string;
    }


}
