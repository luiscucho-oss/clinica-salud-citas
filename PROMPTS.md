# PROMPTS.md — Fase 2: Mejora con IA

Rama: `mejora-ia`  
Asistente utilizado: **Gemini**, integrado en Android Studio  
Alumno: Luis Cucho

Los archivos se adjuntaron como contexto con `@`, por lo que no fue necesario
pegar el código dentro de los prompts.

---

## Prompt 1 — Mejora funcional: AlertDialog para cancelar una cita

### Qué le pedí

```
@MisCitasScreen.kt @AppNavigation.kt @Cita.kt

Agrega una mejora funcional a mi app de citas médicas en Jetpack Compose con Material 3: quiero poder cancelar una cita con un AlertDialog de confirmación.

Comportamiento esperado:
- Cada tarjeta de cita con estado "Confirmada" muestra un botón "Cancelar cita"
- Las citas con estado "Completada" NO muestran ese botón
- Al tocar el botón aparece un AlertDialog con título, un icono de advertencia, y un mensaje que incluya el nombre del médico, la fecha y la hora
- El diálogo tiene dos botones: "Si, cancelar" y "No, volver"
- Si confirma, la cita se elimina de la lista y el diálogo se cierra
- Si cancela o toca fuera del diálogo, no se elimina nada y el diálogo se cierra

Restricciones:
- No uses ViewModel ni MVVM. El estado se maneja con remember y mutableStateOf
- El estado debe guardar la cita seleccionada (Cita?) y no solo un booleano
- Cambia la firma a citas: MutableList<Cita> para poder usar remove
- La lista ya existe como mutableStateListOf en AppNavigation
- No modifiques Screen.kt, AppNavigation.kt ni el menú lateral
- Mantén el ContenedorConMenu y el PaddingValues aplicado
- Usa solo colores de MaterialTheme.colorScheme, sin hexadecimales fijos
- No agregues librerías externas

Devuélveme el archivo completo con sus imports.
```

### Qué tuve que corregir

<!-- COMPLETAR con lo que realmente haya fallado. Si no falló nada, escribirlo
     y explicar por qué: las restricciones estaban declaradas desde el inicio. -->

### Qué verifiqué antes de aceptarlo

- La firma cambió a `citas: MutableList<Cita>`, necesaria para usar `remove`
- El estado guarda **la cita seleccionada** (`mutableStateOf<Cita?>(null)`) y no
  solo un booleano, de modo que el diálogo sabe qué datos mostrar y qué elemento
  eliminar
- `= null` aparece en los tres caminos de cierre
- La pantalla sigue usando `ContenedorConMenu` y aplicando el `PaddingValues`
- Ningún color hexadecimal fijo

---

## Prompt 2 — Rediseño visual del flujo principal

### Qué le pedí

```
@InicioScreen.kt @PerfilMedicoScreen.kt @AgendarCitaScreen.kt @ConfirmacionScreen.kt

Rediseña por completo la interfaz de estas cuatro pantallas de mi app de citas médicas. No quiero un ajuste de espaciados: quiero que parezca un producto terminado, al nivel de una app publicada en Play Store. El diseño actual es demasiado plano.

Identidad visual:
- Nombre: Clinica Salud+
- Estilo: moderno, limpio, con cabeceras destacadas y tarjetas con profundidad
- Todos los colores desde MaterialTheme.colorScheme, sin hexadecimales fijos
- Los degradados con Brush.verticalGradient

Rediseña cada pantalla así:

InicioScreen
- Cabecera con degradado, esquinas inferiores redondeadas, con el saludo "Hola, Juan" y el subtitulo "Encuentra a tu especialista"
- Los chips de especialidad dentro de la cabecera o justo debajo, bien destacados
- Cada medico como una Card mas alta: avatar circular con sus iniciales a la izquierda, nombre en negrita, especialidad debajo, calificacion con estrella a la derecha y una flecha

PerfilMedicoScreen
- Cabecera con degradado y el avatar circular del medico superpuesto, centrado
- Nombre, especialidad y una fila de datos con iconos pequenos: calificacion, resenas y anios de experiencia
- La descripcion dentro de una Card
- El boton "Agendar cita" ancho completo, fijo en la parte inferior

AgendarCitaScreen
- Una Card arriba con los datos del medico
- Los grupos de fecha y hora con titulos claros y chips mas grandes
- El boton "Confirmar cita" ancho completo, fijo abajo

ConfirmacionScreen
- Circulo de exito grande
- El resumen dentro de una Card con filas de informacion: cada fila con un icono pequeno, una etiqueta chica y gris arriba y el valor en negrita debajo
- Los dos botones al final

Restricciones que NO puedes romper:
- No cambies los nombres de las funciones composables ni sus parametros
- Manten toda la navegacion tal como esta, incluidos los popBackStack y los popUpTo
- En AgendarCitaScreen manten citas.add(...) antes del navigate
- Manten enabled = fechaSeleccionada >= 0 && horaSeleccionada >= 0
- Manten Modifier.selectableGroup() en los dos grupos de chips
- InicioScreen debe seguir usando ContenedorConMenu, no un Scaffold suelto
- No modifiques Screen.kt, AppNavigation.kt, MenuLateral.kt ni ContenedorConMenu.kt
- No agregues librerias externas, ViewModel ni dependencias nuevas

Devuelveme los cuatro archivos completos con sus imports.
```

### Qué tuve que corregir

<!-- COMPLETAR. Lo más probable que revises:
     - Que InicioScreen siga usando ContenedorConMenu y no un Scaffold suelto
     - Que no haya quitado la TopAppBar con el icono del menu
     - Que citas.add siga antes del navigate -->

### Qué verifiqué antes de aceptarlo

- `enabled = fechaSeleccionada >= 0 && horaSeleccionada >= 0` sigue presente
- `Modifier.selectableGroup()` sigue en los dos grupos de chips
- `citas.add(...)` se ejecuta **antes** del `navigate`, no después
- Los `popUpTo` de la confirmación quedaron intactos
- `InicioScreen` sigue usando `ContenedorConMenu`, así que el menú lateral
  sigue accesible desde la barra superior
- Ningún color hexadecimal fijo

---

## Prompt 3 — Rediseño visual de las pantallas del menú

### Qué le pedí

```
@MisCitasScreen.kt @HistorialScreen.kt @PerfilScreen.kt

Rediseña la interfaz de estas tres pantallas de mi app de citas médicas. Ya rediseñé el flujo principal (Inicio, Perfil del médico, Agendar cita y Confirmación) con cabeceras en degradado, tarjetas con esquinas de 20dp, iconos dentro de círculos de color y etiquetas tipo píldora. Quiero que estas tres queden en el mismo estilo.

Criterio visual:
- Todos los colores desde MaterialTheme.colorScheme, sin hexadecimales fijos
- Los degradados con Brush.verticalGradient
- Tarjetas con RoundedCornerShape de 20dp y elevación
- Iconos dentro de círculos con fondo de color

Rediseña cada pantalla así:

MisCitasScreen
- Cada cita como una Card más alta, con un icono de calendario dentro de un círculo a la izquierda, el nombre del médico en negrita, la especialidad debajo, y la fecha y hora con iconos pequeños
- La píldora de "Confirmada" y la de "Completada" deben verse claramente distintas
- El estado vacío con un icono grande dentro de un círculo y texto centrado, más trabajado

HistorialScreen
- Cada registro como una Card con un icono dentro de un círculo, el nombre del procedimiento en negrita, la fecha debajo, y una etiqueta de estado "Atendido"

PerfilScreen
- Cabecera con degradado ocupando la parte superior, con esquinas inferiores redondeadas
- El avatar circular con las iniciales "JP" superpuesto sobre esa cabecera
- Nombre y rol debajo del avatar
- Las tarjetas de datos con el número grande, la etiqueta debajo y un icono pequeño dentro de un círculo

Restricciones que NO puedes romper:
- No cambies los nombres de las funciones composables ni sus parámetros
- En MisCitasScreen conserva EXACTAMENTE la funcionalidad del AlertDialog de cancelar cita: el estado que guarda la cita seleccionada, el botón solo en las citas con estado "Confirmada", los tres puntos donde se pone en null (confirmButton, dismissButton y onDismissRequest), y el citas.remove(cita)
- Conserva la firma citas: MutableList<Cita>
- Las tres pantallas deben seguir usando ContenedorConMenu, no un Scaffold suelto, y aplicar el PaddingValues que entrega
- No modifiques Screen.kt, AppNavigation.kt, MenuLateral.kt ni ContenedorConMenu.kt
- No agregues librerías externas, ViewModel ni dependencias nuevas

Devuélveme los tres archivos completos con sus imports.
```

### Qué tuve que corregir

<!-- COMPLETAR. Lo más probable que revises:
     - Que las tres sigan usando ContenedorConMenu y no un Scaffold suelto,
       porque si no se pierde el menú lateral en esas pantallas
     - Que el AlertDialog del prompt 1 haya sobrevivido intacto -->

### Qué verifiqué antes de aceptarlo

Lo primero que revisé fue que el `AlertDialog` del prompt 1 hubiera sobrevivido
sin cambios, porque era el mayor riesgo de este prompt. Después, que las tres
pantallas siguieran usando `ContenedorConMenu`: si lo hubiera reemplazado por un
`Scaffold` suelto, el menú lateral habría desaparecido de esas pantallas y con él
la navegación secundaria completa.

---

## Conclusiones sobre el uso de IA

**Lo que más influyó en la calidad de las respuestas fueron las restricciones.**
Cuando el prompt listaba explícitamente qué no debía tocarse, no lo tocó. Los
requisitos que no declaré como restricción fueron los que corrieron riesgo de
perderse.

**Pedir cambios en bloques pequeños funcionó mejor que pedir todo junto.** Separé
las siete pantallas en dos prompts de rediseño, y cada respuesta fue manejable de
revisar. Con las siete de golpe habría sido más difícil detectar qué se rompió.

**Describir elementos visuales concretos da mejores resultados que pedir
criterios generales.** Frases como "avatar circular superpuesto sobre la cabecera"
o "fila de datos con iconos pequeños" produjeron un cambio real de diseño, mientras
que pedir solo "mejor tipografía y espaciado" habría devuelto el mismo diseño
apenas retocado.

**La mejora funcional conviene pedirla por separado de la visual.** El AlertDialog
se pidió primero y solo, con su propio prompt. Así, cuando los prompts de rediseño
tocaron la misma pantalla, pude verificar contra una versión que ya funcionaba.