package main;








public void menuVenta(Venta venta) {
    boolean salir = false;
    while (!salir) {
        System.out.println("\n--- MENU VENTA ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Ver productos");
        System.out.println("4. Calcular subtotal");
        System.out.println("5. Aplicar propina");
        System.out.println("6. Ver subtotal");
        System.out.println("0. Salir");
        
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                agregarProductoVenta(venta);
                break;

            case 2:
                eliminarProductoVenta(venta);
                break;

            case 3:
                mostrarProductosVenta(venta);
                break;

            case 4:
                venta.calcularPrecio();
                System.out.println("Subtotal calculado: " + venta.getSubtotal());
                break;

            case 5:
                aplicarPropinaVenta(venta);
                break;

            case 6:
                System.out.println("Subtotal actual: " + venta.getSubtotal());
                break;

            case 0:
                salir = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

public void agregarProductoVenta(Venta venta) {
    System.out.println("Ingrese id del producto:");
    int id = scanner.nextInt();
    venta.agregarProducto(producto);
    System.out.println("Producto agregado.");
}

public void eliminarProductoVenta(Venta venta) {
    System.out.println("Ingrese posición del producto a eliminar:");
    int index = scanner.nextInt();
    venta.eliminarProducto(index);
    System.out.println("Producto eliminado.");
}

public void mostrarProductosVenta(Venta venta) {
    int i = 0;
    while (true) {
        Producto p = venta.getItems(i);
        if (p == null) break;
        System.out.println(i + ". Producto ID: " + p.getId());
        i++;
    }
    if (i == 0) {
        System.out.println("No hay productos en la venta.");
    }
}

public void aplicarPropinaVenta(Venta venta) {
    System.out.println("¿Propina personalizada? (1 = sí / 0 = no)");
    int opcion = scanner.nextInt();
    if (opcion == 1) {
        System.out.println("Ingrese porcentaje (ej: 0.15):");
        double porcentaje = scanner.nextDouble();
        venta.aplicarPropina(porcentaje);
    } else {
        venta.aplicarPropina();
    }
    System.out.println("Propina aplicada. Nuevo total: " + venta.getSubtotal());
}



public void menuHistorial(Historial historial) {
    boolean salir = false;
    while (!salir) {
        System.out.println("\n MENU HISTORIAL ");
        System.out.println("1. Agregar venta");
        System.out.println("2. Agregar préstamo");
        System.out.println("3. Buscar venta");
        System.out.println("4. Buscar préstamo");
        System.out.println("5. Eliminar venta");
        System.out.println("6. Eliminar préstamo");
        System.out.println("0. Salir");

        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                agregarVentaHistorial(historial);
                break;

            case 2:
                agregarPrestamoHistorial(historial);
                break;

            case 3:
                buscarVenta(historial);
                break;

            case 4:
                buscarPrestamo(historial);
                break;

            case 5:
                eliminarVenta(historial);
                break;

            case 6:
                eliminarPrestamo(historial);
                break;

            case 0:
                salir = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

public void agregarVentaHistorial(Historial historial) {
    System.out.println("Ingrese ID de la venta: ");
    int id = scanner.nextInt();
    System.out.println("Ingrese una fecha en formato YY-MM-DD: ");
    String fecha = scanner.nextString();
    Venta venta = new Venta(id, fecha, false);
    historial.agregarVenta(venta);
    System.out.println("Venta agregada al historial.");
}

public void agregarPrestamoHistorial(Historial historial) {
    System.out.println("Ingrese ID del préstamo:");
    int id = scanner.nextInt();
    Prestamo prestamo = new Prestamo(id, null, false, null);
    historial.agregarPrestamo(prestamo);
    System.out.println("Préstamo agregado al historial.");
}

public void buscarVenta(Historial historial) {
    System.out.println("Ingrese ID de la venta:");
    int id = scanner.nextInt();
    Venta venta = historial.getVenta(id);
    if (venta != null) {
        System.out.println("Venta encontrada con ID: " + venta.getId());
        System.out.println("Subtotal: " + venta.getSubtotal());
    } else {
        System.out.println("Venta no encontrada.");
    }
}

public void buscarPrestamo(Historial historial) {
    System.out.println("Ingrese ID del préstamo:");
    int id = scanner.nextInt();
    Prestamo prestamo = historial.getPrestamo(id);
    if (prestamo != null) {
        System.out.println("Préstamo encontrado con ID: " + prestamo.getId());
    } else {
        System.out.println("Préstamo no encontrado.");
    }
}

public void eliminarVenta(Historial historial) {
    System.out.println("Ingrese ID de la venta a eliminar:");
    int id = scanner.nextInt();
    if (historial.comprobarVenta(id)) {
        historial.eliminarVenta(id);
        System.out.println("Venta eliminada.");
    } else {
        System.out.println("No existe esa venta.");
    }
}

public void eliminarPrestamo(Historial historial) {
    System.out.println("Ingrese ID del préstamo a eliminar:");
    int id = scanner.nextInt();
    if (historial.comprobarPrestamo(id)) {
        historial.eliminarPrestamo(id);
        System.out.println("Préstamo eliminado.");
    } else {
        System.out.println("No existe ese préstamo.");
    }
}



public void menuInventario(Inventario inventario) {
    boolean salir = false;

    while (!salir) {
        System.out.println("\n MENU INVENTARIO ");
        System.out.println("1. Juegos en venta");
        System.out.println("2. Juegos para préstamo");
        System.out.println("3. Productos ingeribles");
        System.out.println("0. Salir");

        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                menuJuegosVenta(inventario);
                break;

            case 2:
                menuJuegosPrestamo(inventario);
                break;

            case 3:
                menuProductosIngeribles(inventario);
                break;

            case 0:
                salir = true;
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}


public void menuJuegosVenta(Inventario inventario) {
    boolean salir = false;
    
    while (!salir) {
        System.out.println("\n JUEGOS EN VENTA ");
        System.out.println("1. Agregar juego");
        System.out.println("2. Eliminar juego");
        System.out.println("3. Ver juegos");
        System.out.println("4. Vaciar lista");
        System.out.println("0. Volver");

        int op = scanner.nextInt();
        switch (op) {
            case 1:
                System.out.println("Ingrese id del juego:");
                int id = scanner.nextInt();
                JuegoDeMesa j = new JuegoDeMesa(id, "Juego " + id);
                inventario.agregarJuegoVenta(j);
                System.out.println("Juego agregado.");
                break;

            case 2:
                System.out.println("Ingrese índice a eliminar:");
                int index = scanner.nextInt();
                inventario.eliminarProductoJuegoVenta(index);
                System.out.println("Juego eliminado.");
                break;

            case 3:
                mostrarJuegosVenta(inventario);
                break;

            case 4:
                inventario.vaciarJuegosVenta();
                System.out.println("Lista vaciada.");
                break;

            case 0:
                salir = true;
                break;
        }
    }
}

public void menuJuegosPrestamo(Inventario inventario) {
    boolean salir = false;

    while (!salir) {
        System.out.println("\n JUEGOS PRÉSTAMO ");
        System.out.println("1. Agregar juego");
        System.out.println("2. Eliminar juego");
        System.out.println("3. Ver juegos");
        System.out.println("4. Vaciar lista");
        System.out.println("0. Volver");

        int op = scanner.nextInt();
        switch (op) {
            case 1:
                System.out.println("Ingrese id del juego:");
                int id = scanner.nextInt();
                JuegoDeMesa j = new JuegoDeMesa(id, "Juego " + id);
                inventario.agregarJuegoPrestamo(j);
                System.out.println("Juego agregado.");
                break;

            case 2:
                System.out.println("Ingrese índice a eliminar:");
                int index = scanner.nextInt();
                inventario.eliminarProductoJuegoPrestamo(index);
                System.out.println("Juego eliminado.");
                break;

            case 3:
                mostrarJuegosPrestamo(inventario);
                break;

            case 4:
                inventario.vaciarJuegosPrestamo();
                System.out.println("Lista vaciada.");
                break;

            case 0:
                salir = true;
                break;
        }
    }
}

public void menuProductosIngeribles(Inventario inventario) {
    boolean salir = false;

    while (!salir) {
        System.out.println("\n PRODUCTOS INGERIBLES ");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Ver productos");
        System.out.println("4. Vaciar lista");
        System.out.println("0. Volver");

        int op = scanner.nextInt();
        switch (op) {

            case 1:
                System.out.println("Ingrese id del producto:");
                int id = scanner.nextInt();
                ProductoIngerible p = new ProductoIngerible(id, "Producto " + id);
                inventario.agregarProductoIngerible(p);
                System.out.println("Producto agregado.");
                break;

            case 2:
                System.out.println("Ingrese índice a eliminar:");
                int index = scanner.nextInt();
                inventario.eliminarProductoIngerible(index);
                System.out.println("Producto eliminado.");
                break;

            case 3:
                mostrarProductosIngeribles(inventario);
                break;

            case 4:
                inventario.vaciarProductosIngeribles();
                System.out.println("Lista vaciada.");
                break;

            case 0:
                salir = true;
                break;
        }
    }
}

public void mostrarJuegosVenta(Inventario inventario) {
    int i = 0;
    while (true) {
        JuegoDeMesa j = inventario.getJuegosVenta(i);
        if (j == null) break;
        System.out.println(i + ". ID: " + j.getId());
        i++;
    }
    if (i == 0) System.out.println("No hay juegos.");
}

public void mostrarJuegosPrestamo(Inventario inventario) {
    int i = 0;
    while (true) {
        JuegoDeMesa j = inventario.getJuegosPrestamo(i);
        if (j == null) break;
        System.out.println(i + ". ID: " + j.getId());
        i++;
    }
    if (i == 0) System.out.println("No hay juegos.");
}

public void mostrarProductosIngeribles(Inventario inventario) {
    int i = 0;
    while (true) {
        ProductoIngerible p = inventario.getProductosIngeribles(i);
        if (p == null) break;
        System.out.println(i + ". ID: " + p.getId());
        i++;
    }
    if (i == 0) System.out.println("No hay productos.");
}

