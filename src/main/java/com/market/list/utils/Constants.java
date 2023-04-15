package com.market.list.utils;

public class Constants {

    public static final String NOT_FOUND = "El elemento solicitado no ha sido encontrado";

    // ======== PRODUCT ========
    public static final String PRODUCT_CREATED = "El producto se ha creado exitosamente";

    public static final String PRODUCT_FOUND = "El producto se ha encontrado exitosamente";

    public static final String PRODUCT_MODIFIED = "El producto se ha modificado exitosamente";

    public static final String PRODUCT_DELETED = "El producto se ha modificado";
    public static final String PRODUCT_ADDED_TO_LIST = "El producto ha sido añadido a la lista";

    public static String PRODUCT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con el producto: \n" + errors;
    }

    // ======== LISTING ========
    public static final String LISTING_CREATED = "La lista se ha creado exitosamente";

    public static final String LISTING_FOUND = "La lista se ha encontrado exitosamente";
    public static final String LISTING_MODIFIED = "La lista se ha modificado exitosamente";

    public static String LISTING_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con la lista: \n" + errors;
    }


    // ======== AUXILIARIES ========

}
