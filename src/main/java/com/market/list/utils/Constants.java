package com.market.list.utils;

public class Constants {

    // ======== ACCOUNT ========
    public static final String ACCOUNT_CREATED = "La cuenta se ha creado exitosamente!";
    public static final String ACCOUNT_FOUND = "La cuenta se ha encontrado exitosamente";
    public static final String ACCOUNT_MODIFIED = "La cuenta se ha modificado exitosamente";
    public static final String ACCOUNT_DELETED = "La cuenta ha sido eliminada, espero nos volvamos a ver!";

    public static String ACCOUNT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error al crear la cuenta: \n" + errors;
    }

    // ======== PRODUCT ========
    public static final String PRODUCT_CREATED = "El producto se ha creado exitosamente";

    public static final String PRODUCT_FOUND = "El producto se ha encontrado exitosamente";

    public static final String PRODUCT_MODIFIED = "El producto se ha modificado exitosamente";

    public static final String PRODUCT_DELETED = "El producto se ha modificado";
    public static final String PRODUCT_ADDED_TO_LIST = "El producto ha sido añadido a la lista";

    public static final String PRODUCT_REMOVED_FROM_LIST = "El producto ha sido eliminado de la lista";
    public static String PRODUCT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con el producto: \n" + errors;
    }

    // ======== LISTING ========
    public static final String LISTING_CREATED = "La lista se ha creado exitosamente";

    public static final String LISTING_FOUND = "La lista se ha encontrado exitosamente";
    public static final String LISTING_MODIFIED = "La lista se ha modificado exitosamente";
    public static final String LISTING_DELETED = "La lista se ha eliminado exitosamente";

    public static String LISTING_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con la lista: \n" + errors;
    }


    // ======== AUXILIARIES ========
    public static final String NOT_FOUND = "El elemento solicitado no ha sido encontrado";

    public static final String NO_PARAMS = "No se han introducido parámetros de búsqueda, reintente";

}
