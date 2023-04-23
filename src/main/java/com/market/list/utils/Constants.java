package com.market.list.utils;

public class Constants {
    // ======== ACTIONS ========
    private static final String CREATED = "se ha creado";

    private static final String FOUND = "se ha encontrado";

    private static final String MODIFIED = "se ha modificado";

    private static final String DELETED = "se ha eliminado";

    private static final String ADDED_TO_GROUP = "se ha añadido al grupo";

    private static final String REMOVED_FROM_GROUP = "se ha eliminado del grupo";

    private static final String ADDED_TO_LIST = "se ha añadido a la lista";

    private static final String REMOVED_FROM_LIST = "se ha eliminado de la lista";



    // ======== ACCOUNT ========

    private static final String ACCOUNT_ENTITY = "La cuenta";

    public static final String ACCOUNT_CREATED = successMessage(ACCOUNT_ENTITY, CREATED);

    public static final String ACCOUNT_FOUND = successMessage(ACCOUNT_ENTITY, FOUND);

    public static final String ACCOUNT_MODIFIED = successMessage(ACCOUNT_ENTITY, MODIFIED);

    public static final String ACCOUNT_DELETED = successMessage(ACCOUNT_ENTITY, DELETED) + ", espero nos volvamos a ver!";

    public static final String ACCOUNT_ADDED_TO_GROUP = successMessage(ACCOUNT_ENTITY, ADDED_TO_GROUP);

    public static final String ACCOUNT_REMOVED_FROM_GROUP = successMessage(ACCOUNT_ENTITY, REMOVED_FROM_GROUP);

    public static String ACCOUNT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con la cuenta: \n" + errors;
    }


    // ======== GROUP ========

    private static final String GROUP_ENTITY = "El grupo";

    public static final String GROUP_CREATED = successMessage(GROUP_ENTITY, CREATED);

    public static final String GROUP_FOUND = successMessage(GROUP_ENTITY, FOUND);

    public static final String GROUP_MODIFIED = successMessage(GROUP_ENTITY, MODIFIED);

    public static final String GROUP_DELETED = successMessage(GROUP_ENTITY, DELETED);

    public static final String NEW_OWNER = "El nuevo dueño se ha asignado exitosamente";

    public static String GROUP_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con el grupo: \n" + errors;
    }


    // ======== PRODUCT ========

    private static final String PRODUCT_ENTITY = "El producto";

    public static final String PRODUCT_CREATED = successMessage(PRODUCT_ENTITY, CREATED);

    public static final String PRODUCT_FOUND = successMessage(PRODUCT_ENTITY, FOUND);

    public static final String PRODUCT_MODIFIED = successMessage(PRODUCT_ENTITY, MODIFIED);

    public static final String PRODUCT_DELETED = successMessage(PRODUCT_ENTITY, DELETED);

    public static final String PRODUCT_ADDED_TO_LIST = successMessage(PRODUCT_ENTITY, ADDED_TO_LIST);

    public static final String PRODUCT_REMOVED_FROM_LIST = successMessage(PRODUCT_ENTITY, REMOVED_FROM_LIST);

    public static String PRODUCT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con el producto: \n" + errors;
    }


    // ======== LISTING ========

    private static final String LISTING_ENTITY = "La lista";

    public static final String LISTING_CREATED = successMessage(LISTING_ENTITY, CREATED);

    public static final String LISTING_FOUND = successMessage(LISTING_ENTITY, FOUND);

    public static final String LISTING_MODIFIED = successMessage(LISTING_ENTITY, MODIFIED);

    public static final String LISTING_DELETED = successMessage(LISTING_ENTITY, DELETED);

    public static final String LISTING_ADDED_TO_GROUP = successMessage(LISTING_ENTITY, ADDED_TO_GROUP);

    public static final String LISTING_REMOVED_FROM_GROUP = successMessage(LISTING_ENTITY, REMOVED_FROM_GROUP);

    public static String LISTING_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con la lista: \n" + errors;
    }


    // ======== AUXILIARIES ========
    private static final String SUCCESSFULLY = "exitosamente!";

    private static String successMessage(String entity, String action) {
        return entity + " " + action + " " + SUCCESSFULLY;
    }

    public static final String NOT_FOUND = "El elemento solicitado no ha sido encontrado";

    public static final String NO_PARAMS = "No se han introducido parámetros de búsqueda, reintente";

    public static final String PASSWORD_REQUIREMENTS = "La contraseña debe tener al menos ocho caracteres, una mayúscula y un número";

    public static final String FORBIDDEN = "No tienes los permisos necesarios";
}
