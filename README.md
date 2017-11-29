# Seccion_04_Realm_Lab
Laboratorio 4 Android - CityWorld App

Objetivo
--------
Crear una app con un recycler view usando card view para sus items. El icono en la barra de opciones no se muestra. Cada card view, debe
estar comprendido de una imagen en la parte superior, con el nombre de la ciudad sobrepuesto (añádale sombra negra a la fuente blanca
para que sea mas legible). Más abajo, le sigue una descripción de la ciudad, con un máximo de 3 líneas o 10 0caracteres. Después de esa
descripción, tendremos una estrella de icono, con el número de la puntuación a su lado. Justo abajo, una línea separadora, y por último, un
botón DELETE para borrar esa ciudad. Si se clickea en cualquier parte del CardView excepto ese último botón nos dirigirá hacia la pantalla
de Añadir o Editar ciudad.

Vamos a tener sólo un Activity para añadir/editar ciudades, tendremos que usar los métodos pertinentes para ello. Caminaremos el título
del Activity dependiendo si editar o añadir ciudad (Dependiendo de si se hace click en el CardView on el Floating Action Button).

La pantalla de creación/edición está compuesta de un ImageView en el top, para hacer un preview del link, luego tenemos input para nombre,
descripción, link (link externo, recuerda añadir http://) a una imagen (Usa HD si quieres), un botón al lado del link para renderizar la preview
de la imagen en la parte superior y un Ranting Bar para puntuar, con 5 estrellas. Si es para edición, el preview debe estar cargado con la
imagen correctamente. No permitas guardar si todos los campos no están completos.

Todos los datos deben persistir en la base de datos Realm. Usa Picasso para la carga de imágenes.

Nuestro modelo ciudad, deberá tener las siguientes propiedades: nombre, descripción, imagen para el background, y estrellas para la
puntuación. El id será auto generado.

Configura Realm para usar su versión 2.1.1 dónde encontrarás una pequeña diferencia en cómo configurar la configuración por defecto de
Realm. Échale un ojo a la documentación oficial y si no encuentras la forma de llevarlo a cabo, usa la versión antigua 1.2.0

El adaptador deberá implementar dos listeners, uno para el click en el CardView y un segundo listener para el botón de borrado. Recuerda
hacerlo de forma que definamos el comportamiento de ambos en el activity y no en el adapter.
