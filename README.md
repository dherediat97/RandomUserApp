# RandomUserApp

Esta app está hecha basándose en varios patrones:

1. La estructura está hecha usando los principios SOLID y Clean Architecture, buscando la organización y usando DI(Inyección de Dependencias) para optimizar las multitud de llamadas a una misma estancia.
2. Usando MVVM como patrón maestro de desarrollo para un mejor uso de los datos que nos proporcionan la API.

La app como tal, es una agenda con datos aleatorios alimentada por esta API: https://randomuser.me, que muestra ambas pantallas basadas en los diseños proporcionados de Figma

## Tecnologías/paquetes usados:
- Kotlin
- Gradle(KTS)
- XML(Mapa)
- Coroutines(KotlinX)
- OSMdroid(Open Street Map)
- Material Components(V3) Material You
- JUnit

## Frameworks:
- Jetpack Compose bajo el marco de trabajo del SDK de Android


## Errores encontrados:
- Los datos de la localización son incorrectos(valores de coordenadas inválidos) por eso se ha metido siempre la misma ubicación.
- Para poder hacer que se listen únicamente los contactos que no están repetidos, he tenido que contemplar el id de la imagen(url de la propia imagen) que devuelve la api. Esta id, se repetía en muchos casos ya que solo hay 150 imágenes en total, y sin embargo, había 5000 registros en total. Además, ahora mismo hay imagenes repetidas pero no se pueden descartar debido a que la id es distinta.
- No se ha podido filtrar la lista de contactos usando la API. No contiene un endpoint que haga este cribado o filtrado de datos por nombre o email.
