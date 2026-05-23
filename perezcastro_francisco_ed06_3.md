# Informe de la tarea

En el siguiente documento se describen todos los *code smells* detectados e identificados junto con su refactorización. Estos serán clasificados conforme al orden en el que se presentan en los apuntes de la asignatura.

Empezaremos a identificar los code smells siguiendo el orden siguiente:

1. Clase `Hotel`
2. Clase `Reserva`
3. Clase `Habitacion`
4. Clase `Cliente`
5. Clase `Main`

## Inspección del proyecto con IntelliJ IDEA

Para detectar los code smells haremos uso de la opción `Analyze > Inspect Code...` del menú asociado al click derecho del ratón.

![Opción de inspección de código](img/inspeccion/analyze-inspect_code-option.png)

Una vez seleccionada la opción, le saldrá la siguiente ventana. Deberá seleccionar la opción `Whole project` y pulsar con el ratón sobre `Configure...`.

![Especificando el ámbito de análisis](img/inspeccion/specify-inspection-scope.png)

Una vez haya pulsado sobre `Configure...`, le aparecerá la siguiente ventana. 

![Ventana de Inspección](img/inspeccion/inspection-window.png)

Deberá clickar sobre la barra de búsqueda para buscar *magic numbers* y seleccionará el ítem `Magic Number`. Luego pulse el botón `Apply`y posteriormente el botón `OK` para guardar el cambio y regresar a la ventana anterior. Una vez vea la ventana anterior, pulse el botón `Analyze` para comenzar el análisis.

![Inspección de los magic numbers activa](img/inspeccion/magic-number-inspection-activate.png)

> [!NOTE]
> El ejemplo del ítem `Magic Number` es aplicable a otros ítems, tales como `Feature envy`, `'switch' statement`. Estos últimos también se usarán en la realización de esta tarea.

Una vez termine el análisis, obtendremos la información mostrada en la siguiente imagen. Dicha información nos será útil para detectar code smells en el código del proyecto.

![Análsis finalizado](img/inspeccion/analisis-finalizado.png)

## 1. Clase `Cliente`

### Visibilidad de los atributos

Los atributos de la clase son públicos. Hay que cambiar su visibilidad a privado para cumplir con los estándares del **Clean Code** y los principios de la encapsulación, y añadir el getter correspondiente con modificador `public`.

- **Antes de refactorizar:**

![Atributos antes de refactorizar](img/aptdo1/before_atributos-publicos.png)

- **Refactorización:** la refactorización debe realizarse atributo a atributo.

![Refactorización de los atributos](img/aptdo1/refactor_atributo-publico.png)

Le saldrá la siguiente ventana. Marcamos todos los atributos públicos (menos `esVip`) y generamos únicamente la opción del `Get access`. Luego pulsaremos el botón `Refactor`.

![Paso dos de la refactorización](img/aptdo1/refactor_atributo-publico_2.png)

Para el caso del atributo `esVip` será necesario crear también su setter.

![Paso dos de la refactorización - esVip](img/aptdo1/refactor_atributo-publico_3.png)

- **Después de refactorizar:**

![Atributos refactorizados](img/aptdo1/after_atributos-publicos.png)

Se muestra a continuación el código generado al refactorizar.

```java
public int getId() { 
    return id;
}
public String getNombre() {
    return nombre;
}
public String getDni() {
    return dni;
}
public String getEmail() {
    return email;
}
public boolean isEsVip() {
    return esVip;
}
public void setEsVip(boolean esVip) {
    this.esVip = esVip;
}
```

### Magic number

Se ha detectado un magic number en el método `validarNombre`: el número 3 en la condición de la sentencia `if`, que define el tamaño mínimo (sin espacios) que un nombre debe tener para ser válido.

- **Antes de refactorizar:**

![Método validarNombre sin refactorizar](img/aptdo1/before_magic-number.png)

- **Refactorización:**

![Refactorización del método validarNombre](img/aptdo1/refactor_magic-number.png)

- **Después de refactorizar:**

![Método validarNombre refactorizado](img/aptdo1/after_magic-number.png)

Si vamos a lod métodoe `validarEmail` y `validarDni`, encontramos una cadena de texto utilizada para validar el email y el DNI (respectivamente) del cliente que podemos considerar *magic numbers*. Procedemos a extraerlas como constantes al igual que antes.

- **Antes de refactorizar:**

![Métodos validarEmail y validarDni sin refactorizar](img/aptdo1/before_validarEmail_validarDni.png)

- **Refactorización:**

![Refactorización del método validarEmail](img/aptdo1/refactor_validarEmail.png)
![Refactorización del método validarDni](img/aptdo1/refactor_validarDni.png)

- **Después de refactorizar:**

![Método validarEmail refactorizado](img/aptdo1/after_validarEmail.png)
![Método validarDni refactorizado](img/aptdo1/after_validarDni.png)

### Eliminando redundancias

El constructor de la clase `Cliente` es el siguiente:

```java
public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
    this.id = id;
    if(validarNombre(nombre)) {
        this.nombre = nombre;
    }
    if(validarDni(dni)) {
        this.dni = dni;
    }
    if(validarEmail(email)) {
        this.email = email;
    }
    this.setEsVip(esVip);
}
```

Si echamos un vistazo al código, podemos ver que los tres métodos de validación solo pueden hacer una de dos cosas:

- Devolver `true` si el dato es válido
- Lanzar una excepción informando al usuario de que el dato no es válido

En resumen, resulta redundante encerrar la llamada al método en la condición de una sentencia `if`, pues lo que haya después de la ejecución de la llamada se ejecutará si y sólo si el dato es válido. Por tanto, se elimina dicha redundancia, siendo el siguiente fragmento el código refactorizado.

```java
public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
    this.id = id;
    validarNombre(nombre);
    this.nombre = nombre;
    validarDni(dni);
    this.dni = dni;
    validarEmail(email);
    this.email = email;
    this.setEsVip(esVip);
}
```

## 2. Clase `Reserva`

## 3. Clase `Habitacion`

Se ha detectado un code smell de tipo Object-Oriented Abuser en la línea 36. El siguiente fragmento de código muestra un switch statement que indica que el código no está utilizando correctamente la herencia o el polimorfismo.

```java
// Método que usa un switch para determinar el número máximo de huéspedes
public double obtenerNumMaxHuespedes() {
    return switch (tipo) {
        case "SIMPLE" -> 1;
        case "DOBLE" -> 3;
        case "SUITE" -> 4;
        case "LITERAS" -> 8;
        default -> 1;
    };
}
```

Además, este método no se utiliza nunca, tal y como se puede ver en la imagen siguiente (está marcado con `no usages`).

![Método obtenerNumMaxHuespedes](img/aptdo3/metodo-obtenerNumMaxHuespedes.png)

Finalmente, tenemos el método `reservar()`, cuyo aspecto es un tanto sospechoso. Sion embargo, en el mismo código hay un comentario que nos indica que la forma de festionar la disponibilidad de una habitación está pendiente de modificaciones. Por ahora, no vamos a considerar acciones sobre dicho método.

```java
public void reservar() {
    if (disponible) {
        System.out.println("Habitación #" + numero + " ya reservada");
    }
    disponible = true;
}
```

## 4. Clase `Hotel`

### Código inalcanzable

El IDE, nada más abrir el archivo `Hotel.java`, nos indica que hay un error en la línea 112 debido a un `Unreachable statement`. El propio IDE nos indica que el `return 0;` es inalcanzable, por lo que se procede a su eliminación.

- **Antes de refactorizar:**

![Error línea 112](img/aptdo4/before_linea-112.png)

- **Después de refactorizar:**

![Línea 112 refactorizada](img/aptdo4/after_linea-112.png)

A mayores, se detecta que el método mostrado a continuación no es utilizado. En este caso optamos por dejarlo, asumiendo que se trata de una deuda técnica a futuro, es decir, asumiendo que se trata de una funcionalidad del programa que no se ha terminado de integrar al software.

```java
public void registrarHabitaciones(List<String> tipos, List<Double> preciosBase) {
    for(int i = 0; i < tipos.size(); i++) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipos.get(i), preciosBase.get(i));
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }
}
```

### Duplicación de código

Analizando más en detalle el método `registrarHabitaciones(List<String> tipos, List<Double> preciosBase)`, nos damos cuenta que el contenido del bucle `for` está duplicado, pues equivale al método `registrarHabitacion(String tipo, double precioBase)`.

- **Antes de refactorizar:**

![Métodos registrarHabitacion y registrarHabitaciones](img/aptdo4/before_registrarHabitaciones.png)

- **Refactorización:** reescritura a mano.
- **Después de refactorizar:**

![Método registrarHabitaciones refactorizado](img/aptdo4/after_registrarHabitaciones.png)

## 5. Clase `Main`
