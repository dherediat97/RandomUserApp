# RandomUserApp

Esta app esta hecha basándose en varios patrones, primero en estructura esta hecho usando los principios SOLID y Clean Architecture, desde el principio bien organizado y usando DI para optimizar las multitud de llamadas a una misma estancia. Luego usando MVVM como patrón maestro de desarrollo para un mejor uso de los datos que nos proporcionan la API.

La app como tal, es una agenda con datos aleatorios alimentada por esta API: https://randomuser.me, que muestra ambas pantallas(idénticas o casi) que se indicaron en los diseños de Figma

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
- Los datos de a localización, casi siempre se iba fuera de los limítes de la tierra, es tan aleatorio que pasaba eso, por eso se ha metido siempre la misma localización
- Para poder hacer que se listen únicamente los contactos que no están repetidos, he tenido que contemplar el id de la imagen que devuelve la api, este id, se repetía en muchos casos, ya que solo hay 150 imágenes en total, pero habia 5000 registros en total, asi que si se repetía la misma imagen con mismo id, solo se mostrará uno, ahora si se repite y son ids diferentes, no se puede hacer nada.
- No se ha podido filtrar la lista de contactos usando la API, no contiene un EndPoint que haga este cribado de datos por nombre o email.
