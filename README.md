# ED06_3 - Refactorización de proyecto Java en IntelliJ IDEA

> [!NOTE]
> Este documento describe la tarea a realizar. En otras palabras, se trata de la descripción dada en el enunciado de la tarea.

## Introducción

En esta actividad se te proporciona un proyecto de Java que sirve para gestionar las reservas de un hotel. El objetivo es **refactorizar el código** corrigiendo problemas de diseño.

## Objetivos

- Identificar code smells en el código Java proporcionado.
- Aplicar técnicas de refactorización para mejorar la calidad del código proporcionado.

## Descripción del Proyecto

El proyecto proporcionado es un sistema de gestión de reservas de un hotel. El sistema permite la gestión de **habitaciones**, **clientes**, **facturación** y **reservas**. Puedes encontrar el proyecto en este [repositorio de Github](https://github.com/danielmartinan/ed06_3_documentacion_refactorizacion).

## Descripción de la Actividad

Debes analizar el proyecto en busca de code smells y aplicar técnicas de refactorización para mejorar la calidad del código. A continuación se detallan los pasos a seguir:

1. **Haz un fork del repositorio**: Realiza un fork del repositorio proporcionado en tu cuenta de GitHub. Esto te permitirá trabajar en una copia del proyecto sin afectar al original.
2. **Clonar el repositorio**: Clona el repositorio del que acabas de hacer fork en tu máquina local.
3. **Abrir el proyecto en IntelliJ IDEA**: Abre el proyecto clonado en IntelliJ IDEA.
    - Puedes clonar directamente el proyecto desde Intellij IDEA
4. **Identificar code smells**: Utiliza las herramientas de análisis de código de IntelliJ IDEA para identificar code smells en el proyecto. Presta atención a los siguientes aspectos:
   - Clases demasiado grandes o con demasiadas responsabilidades.
   - Métodos demasiado largos o complejos.
   - `if` anidados o complejos. Posibilidad de aplicar `Early Return`.
   - Métodos demasiado largos.
   - Nombres de variables y métodos poco descriptivos.
   - Código duplicado.
   - Código inalcanzable
   - Comentarios poco adecuados o redundantes.
   - Uso de *Magic Numbers*
   - Cualquier otros aspecto de los descritos en los apuntes de la unidad.
   - Puedes revisar los code smells más comunes en Refactoring Guru: [Code Smells](https://refactoring.guru/es/refactoring/smells).
5. **Refactorizar el código**: Aplica las técnicas de refactorización adecuadas para corregir los problemas identificados. Utiliza las facilidades proporcionadas por el IDE para realizar la refactorización de manera segura. **Realiza capturas de pantalla de los procesos de refactorización aplicados**.
6. **Subir los cambios a GitHub**: Después de realizar los cambios, sube tu código refactorizado a tu repositorio de GitHub.

## Evaluación

A continuación se detallan los criterios de evaluación:

- Identificación de code smells (4.5 puntos).
- Aplicación de técnicas de refactorización (4.5 puntos).
- Subir los cambios a un nuevo repositorio de GitHub (0.5 puntos).
- Generación del informe (0.5 puntos).

## Entrega

Deberás añadir a la entrega de la tarea el enlace a tu repositorio de GitHub con el código refactorizado, así como un informe en formato markdown con el nombre **apellidos_nombre_ed06_3.md** que contenga:

- Descripción de los cambios realizados en el código.
- Capturas de pantalla del proceso de refactorización con Intellij IDEA.

Se valorará el uso adecuado de sintaxis markdown para la presentación del informe (encabezados, imágenes, listas, fragmentos de código...).
