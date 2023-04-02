package com.market.list.utils;

public class Constants {

    public static final String PRODUCT_CREATED = "El producto se ha creado exitosamente";

    public static final String PRODUCT_FOUND = "El producto se ha encontrado exitosamente";

    public static final String PRODUCT_NOT_FOUND = "No se ha encontrado el producto solicitado";
    public static final String PRODUCT_MODIFIED = "El producto se ha modificado exitosamente";

    public static final String PRODUCT_DELETED = "El producto se ha modificado";

    public static String PRODUCT_HAS_ERRORS(String errors) {
        return "Ha ocurrido un error con el producto: \n" + errors;
    }
}
