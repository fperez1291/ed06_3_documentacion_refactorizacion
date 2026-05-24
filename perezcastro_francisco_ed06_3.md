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

### Magic numbers

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

### Método `calcularPrecioFinal()`

#### Nombre de variables

Se detectan varias variables con nombres ilegibles y poco o nada descriptivos.

- **Antes de refactorizar:**

![Nombres de variables ilegibles y no descriptivos](img/aptdo2/before_nombre-variables.png)

- **Refactorización:** Cambiamos el nombre de las variables a uno más legible y autodescriptivo.

![Refactorizando variables](img/aptdo2/refactor_nombre-variables.png)

- **Después de refactorizar:**

![Comentario muy largo refactorizado](img/aptdo2/after_nombre-variables.png)

#### Comentarios

Se detecta un comentario demasiado largo.

- **Antes de refactorizar:**

![Comentario muy largo](img/aptdo2/before_comentario-largo.png)

- **Refactorización:** Lo convertimos a comentario Javadoc e insertaremos algunos saltos de línea para facilitar la legibilidad.

![Refactorizando el comentario](img/aptdo2/refactor_comentario-largo.png)

- **Después de refactorizar:**

![Comentario muy largo refactorizado](img/aptdo2/after_comentario-largo.png)

También se detectan algunos comentarios redundantes, por lo que se procede a eliminarlos.

- **Antes de refactorizar:**

![Comentario redundante](img/aptdo2/before_comentario-redundante_1.png)
![Comentario redundante](img/aptdo2/before_comentario-redundante_2.png)

- **Refactorización:** Se eliminan los comentarios.
- **Después de refactorizar:**

![Comentario redundante eliminado](img/aptdo2/after_comentario-redundante_1.png)
![Comentario redundante eliminado](img/aptdo2/after_comentario-redundante_2.png)

#### Feature envy

Se detectan dos feature envys al aplicar descuentos. Se extraen ambas, ubicando el cálculo del descuento VIP en la clase `Cliente` y el cálculo del descuento adicional en la clase `Reserva`.

- **Antes de refactorizar:**

![Feature envys sin refactorizar](img/aptdo2/before_feature-envy_1_2.png)

- **Refactorización:** primero extraeremos el método y luego lo ubicaremos en la clase correspondiente.
  - `Cliente`:

![Extrayendo método](img/aptdo2/refactor_feature-envy_1-1.png)
![Método extraído](img/aptdo2/refactor_feature-envy_1-2.png)

Ahora lo movemos a la clase correcta:

![Moviendo método](img/aptdo2/refactor_feature-envy_1-3.png)

El método quedará como sigue, pero hace falta refactorizar un poco más:

![Método movido](img/aptdo2/refactor_feature-envy_1-4.png)

Debemos eliminar el parámetro `reserva`, que no sirve para nada, y resolver el magic number.

![Refactorización parámetro reserva](img/aptdo2/refactor_feature-envy_1-5.png)
![Refactorización magic number](img/aptdo2/refactor_feature-envy_1-6.png)

El método refactorizado queda como sigue:

```java
double aplicarDescuentoClienteVIP(double precioFinal) {
    // Si el cliente es VIP, aplicamos un descuento del 10%
    if (isEsVip()) {
        precioFinal *= MULTIPLICADOR_PRECIO_FINAL_CLIENTE_VIP;
    }
    return precioFinal;
}
```

  - `Reserva`: pasamos a realizar el mismo proceso con la segunda feature envy.

![Extrayendo método](img/aptdo2/refactor_feature-envy_2-1.png)
![Método extraído](img/aptdo2/refactor_feature-envy_2-2.png)

El método quedará como sigue, pero hace falta refactorizar un poco más:

![Método extraido](img/aptdo2/refactor_feature-envy_2-3.png)

Debemos resolver los magic number.

![Refactorización primer magic number](img/aptdo2/refactor_feature-envy_2-4.png)
![Primer magic number refactorizado](img/aptdo2/refactor_feature-envy_2-5.png)
![Segundo magic number refactorizado](img/aptdo2/refactor_feature-envy_2-6.png)

El método refactorizado queda como sigue:

```java
private static double aplicarDescuentoAdicional(int numDiasReserva, double precioFinal) {
    // Si el intervalo de fechas es mayor a 7 días, aplicamos un descuento adicional del 5%
    if (numDiasReserva > LIM_DIAS_RESERVA_SIN_DESCUENTO_ADICIONAL) {
        precioFinal *= MULTIPLICADOR_PRECIO_FINAL_CON_DESCUENTO_ADICIONAL;
    }
    return precioFinal;
}
```

### Método `mostrarReserva()`

#### Feature envy

En este método se detectan varias feature envys a resolver.

![Feature envys sin refactorizar](img/aptdo2/before_feature-envy_3_4.png)

Primero. extraemos el `println` de la habitación:

![Extrayendo método](img/aptdo2/refactor_feature-envy_3-1.png)
![Método extraido](img/aptdo2/refactor_feature-envy_3-2.png)
![Moviendo método](img/aptdo2/refactor_feature-envy_3-3.png)
![Método movido](img/aptdo2/refactor_feature-envy_3-4.png)

Primero. extraemos el `println` de la habitación:

![Extrayendo método](img/aptdo2/refactor_feature-envy_4-1.png)
![Método extraido](img/aptdo2/refactor_feature-envy_4-2.png)
![Moviendo método](img/aptdo2/refactor_feature-envy_4-3.png)
![Método movido](img/aptdo2/refactor_feature-envy_4-4.png)

Es necesario eliminar el parámetro `reserva`, pues no cumple ningún rol.

![Eliminando parámetro](img/aptdo2/refactor_feature-envy_4-5.png)
![Parámetro eliminado](img/aptdo2/after_feature-envy_4.png)

#### Eliminando redundancias

Al imprimir las fechas de inicio y fin se usa el método `toString()`, pero este método se puede eliminar dado que `println` invoca al método `toString()` de los objetos que recibe por defecto.

- **Antes de refactorizar:**

![Redundancia antes de refactorizar](img/aptdo2/before_eliminando-redundancias.png)

- **Después de refactorizar:**

![Redundancia refactorizada](img/aptdo2/after_eliminando-redundancias.png)

### Dead Code

En la clase se detectan varios métodos que nunca se utilizan, pero se asume que han sido implementados como deuda técnica a futuro.

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
