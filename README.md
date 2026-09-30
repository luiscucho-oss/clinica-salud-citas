# Clínica Salud+ — Reserva de citas médicas

Curso: Programación en Móviles — Tecsup  
Docente: Juan León S.  
Alumno: Luis Cucho

Opción A — Reserva de citas médicas con navegación secundaria por menú lateral.

## Descripción

Aplicación Android para reservar citas médicas. Integra layouts y controles,
listas eficientes, navegación secuencial con paso de parámetros y navegación
secundaria mediante un menú lateral desplegable.

El estado se maneja únicamente con `remember`, `rememberSaveable`,
`mutableStateOf`, `mutableIntStateOf` y `mutableStateListOf`.
**No se utiliza ViewModel ni MVVM.**

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose 2.7.7
- Minimum SDK: 24

## Requisitos funcionales cumplidos

| # | Requisito del documento | Implementación |
|---|---|---|
| 1 | Inicio: LazyRow con chips de especialidad (mínimo 2) y LazyColumn con médicos (mínimo 3), cada tarjeta con nombre, especialidad y calificación | `InicioScreen`: `LazyRow` con `FilterChip` para Cardiología y Pediatría; `LazyColumn` con los médicos filtrados; `TarjetaMedico` muestra nombre, especialidad y calificación con estrella |
| 2 | Perfil del médico: recibe los datos por parámetro de navegación; botón "Agendar cita" | `PerfilMedicoScreen` recibe `medicoId` como `NavType.IntType` y recupera el médico con `buscarMedicoPorId` |
| 3 | Agendar cita: selección de fecha (mínimo 3) y hora (mínimo 3), ambas de selección única | `AgendarCitaScreen` con dos grupos de `FilterChip` dentro de `selectableGroup`, cada uno con su propio estado `Int` |
| 4 | Confirmación: resumen de la cita (médico, fecha, hora); botón para volver al inicio | `ConfirmacionScreen` muestra los tres datos y ofrece "Ver mis citas" y "Volver al inicio" |
| 5 | Menú lateral: ícono ≡ en la topBar; mínimo 3 destinos | `ModalNavigationDrawer` con 4 destinos: Inicio, Mis citas, Historial médico y Perfil |
| 6 | Mis citas: LazyColumn con las citas agendadas, cada una con su estado diferenciado visualmente | `MisCitasScreen` con píldora de color: "Confirmada" en `primaryContainer`, "Completada" en `surfaceVariant` |

### Requisitos adicionales de la rúbrica

| Criterio | Implementación |
|---|---|
| Scaffold correcto con padding aplicado en todas las pantallas | Cada pantalla tiene su `Scaffold` (directo o a través de `ContenedorConMenu`) y aplica el `PaddingValues` |
| Navegación secuencial con paso de parámetros | Inicio → Perfil del médico (`medicoId`) → Agendar cita (`medicoId`) → Confirmación (`medicoId` + `fechaIndex` + `horaIndex`) |
| Selección de opción única antes de confirmar | Dos selecciones independientes, fecha y hora, cada una con un solo `Int` como estado |

## Estructura del proyecto

com.cucho.clinicasalud
├── model
│   ├── Medico.kt                Modelo de un medico con fechas y horas disponibles
│   └── Cita.kt                  Modelo de una cita agendada
├── data
│   ├── DatosMedicos.kt          Lista de medicos y busqueda por id
│   └── DatosCitas.kt            Cita inicial de ejemplo
├── navigation
│   ├── Screen.kt                7 rutas definidas con sealed class
│   ├── AppNavigation.kt         NavHost y estado compartido de citas
│   ├── MenuLateral.kt           Contenido del drawer con 4 destinos
│   └── ContenedorConMenu.kt     Envuelve el Scaffold con el ModalNavigationDrawer
├── screens
│   ├── InicioScreen.kt          LazyRow de especialidades + LazyColumn de medicos
│   ├── PerfilMedicoScreen.kt    Datos del medico elegido
│   ├── AgendarCitaScreen.kt     Seleccion unica de fecha y de hora
│   ├── ConfirmacionScreen.kt    Resumen de la cita agendada
│   ├── MisCitasScreen.kt        Citas con estado Confirmada o Completada
│   ├── HistorialScreen.kt       Registros medicos anteriores
│   └── PerfilScreen.kt          Datos del paciente
└── MainActivity.kt

## Navegación

### Flujo secuencial

Inicio → Perfil del médico → Agendar cita → Confirmación → Mis citas

- Inicio a Perfil del médico: pasa `medicoId` como `Int`
- Perfil a Agendar cita: pasa `medicoId`
- Agendar a Confirmación: pasa `medicoId`, `fechaIndex` y `horaIndex`, los tres `Int`
- Confirmación usa `popUpTo` para que el botón atrás no regrese al formulario

### Navegación secundaria

Menú lateral con 4 destinos: Inicio, Mis citas, Historial médico y Perfil.

Se abre con el ícono ≡ de la barra superior. El destino activo se determina
leyendo la ruta actual con `currentBackStackEntryAsState`, de modo que el
resaltado siempre coincide con la pantalla visible.

Cada cambio de destino usa `launchSingleTop`, `popUpTo` con `saveState` y
`restoreState` para evitar que el back stack crezca.

## Decisiones técnicas

**Por qué el drawer envuelve al Scaffold y no es un parámetro suyo**  
`ModalNavigationDrawer` no es una zona del `Scaffold`: es un contenedor que ocupa
toda la pantalla y dibuja el panel deslizante por encima de todo, oscureciendo el
fondo. En Material 2 el `Scaffold` sí tenía parámetros de drawer, pero Material 3
los separó a propósito. Si el drawer estuviera dentro del `Scaffold`, el panel
quedaría atrapado en el área de contenido y no podría cubrir la barra superior.

**Por qué existe ContenedorConMenu**  
Las cuatro pantallas del menú necesitan el mismo `ModalNavigationDrawer`, la misma
`TopAppBar` con el ícono ≡ y el mismo `Scaffold`. En vez de repetir ese bloque
cuatro veces, se extrajo a un composable que recibe un título y un `content` del
mismo tipo que el de `Scaffold`, `@Composable (PaddingValues) -> Unit`.

**Por qué abrir el menú necesita una corrutina**  
`drawerState.open()` y `.close()` son funciones `suspend` porque animan el
deslizamiento del panel. No pueden llamarse desde un `onClick` normal, así que se
lanzan con `rememberCoroutineScope`.

**Por qué se pasan índices y no los textos de fecha y hora**  
Textos como "9:00 am" contienen espacios y dos puntos, que rompen la ruta de
navegación. Se pasan las posiciones como `Int` y los textos se recuperan desde las
listas del médico.

**Por qué la selección de fecha y hora funciona como RadioButton**  
Cada una guarda un solo entero. Cada chip calcula su `selected` comparándose con
ese número, por lo que solo uno puede estar activo dentro de su grupo. Son dos
estados independientes porque son dos decisiones distintas del usuario. Se aplica
`Modifier.selectableGroup()` en ambos grupos.

**Dónde vive el estado de las citas**  
La lista se declara con `mutableStateListOf` dentro de `AppNavigation`, el punto
común más cercano entre la pantalla que agrega la cita y la que la muestra.

## Capturas

**Inicio**

<img width="443" height="929" alt="image" src="https://github.com/user-attachments/assets/c53008cc-8f72-4bd3-860e-0f868fb0cdfb" />


**Menú lateral abierto**

<img width="454" height="922" alt="image" src="https://github.com/user-attachments/assets/966adc11-0d6d-47d9-af2c-485d13e985eb" />

**Perfil del médico**

<img width="497" height="919" alt="image" src="https://github.com/user-attachments/assets/c2591eb4-cab9-43d1-b51b-b46b18f6e825" />

**Agendar cita**

<img width="540" height="924" alt="image" src="https://github.com/user-attachments/assets/77f113e5-2f1b-4b9f-9af1-e628bb56b75d" />

**Confirmación**

<img width="469" height="927" alt="image" src="https://github.com/user-attachments/assets/ad4b30a3-0c6d-4a41-92e8-ba06274f2b1c" />

**Mis citas**

<img width="461" height="923" alt="image" src="https://github.com/user-attachments/assets/05dc8cc4-9310-49fc-bcfd-44e0e6b0151f" />

**Historial médico**

<img width="478" height="923" alt="image" src="https://github.com/user-attachments/assets/d64e0413-82f3-4089-b780-85422674dbe1" />

**Perfil**

<img width="465" height="927" alt="image" src="https://github.com/user-attachments/assets/f13355e2-384b-4cd6-97ad-1d1e9c7779d4" />

## Mejora con IA

Esta rama (`mejora-ia`) parte de `main` e incorpora una mejora funcional y un
rediseño visual, ambos realizados con **Gemini**, integrado en Android Studio.

### Mejora funcional

Se agregó la posibilidad de **cancelar una cita** con un `AlertDialog` de
confirmación:

- Las citas con estado "Confirmada" muestran un botón "Cancelar cita"
- Las citas con estado "Completada" no lo muestran, porque no tiene sentido
  cancelar una consulta que ya ocurrió
- El diálogo muestra el médico, la fecha y la hora antes de confirmar
- Solo al confirmar se elimina la cita de la lista compartida

El estado del diálogo guarda **la cita seleccionada** (`Cita?`) y no un booleano,
de modo que el mismo valor sirve para saber si el diálogo está abierto, qué datos
mostrar y qué elemento eliminar. El diálogo se cierra poniendo ese estado en
`null` en los tres caminos posibles: confirmar, cancelar y tocar fuera.

En Compose un diálogo no se abre con una función imperativa: se incluye o se
excluye del árbol de UI según el estado, y la recomposición hace el resto.

### Mejora visual

Rediseño de las siete pantallas con un mismo criterio: cabeceras con degradado
construidas con `Brush.verticalGradient`, tarjetas con esquinas redondeadas y
elevación, avatares e iconos dentro de círculos de color y etiquetas tipo píldora.
Todos los colores provienen de `MaterialTheme.colorScheme`, sin valores
hexadecimales fijos.

Las pantallas del menú conservan `ContenedorConMenu`, de modo que el menú lateral
sigue accesible desde la barra superior en Inicio, Mis citas, Historial médico y
Perfil.

### Documentación de los prompts

Los tres prompts utilizados, junto con lo que hubo que verificar y corregir de
cada respuesta, están documentados en [PROMPTS.md](PROMPTS.md).

## Capturas de la mejora

**Inicio**

<img width="398" height="765" alt="image" src="https://github.com/user-attachments/assets/3833970f-df0d-4516-8d7d-39007e837a00" />

**Menú lateral abierto**

<img width="399" height="772" alt="image" src="https://github.com/user-attachments/assets/e9921b95-3027-47a6-8853-87cf3e99fc69" />

**Perfil del médico**

<img width="386" height="768" alt="image" src="https://github.com/user-attachments/assets/40fd8bcb-8da3-4611-89c5-45e889768557" />

**Agendar cita**

<img width="395" height="773" alt="image" src="https://github.com/user-attachments/assets/155ef781-fbb7-4619-aebd-8cb2c21871b2" />

**Confirmación**

<img width="387" height="773" alt="image" src="https://github.com/user-attachments/assets/276b3b61-791c-4fa9-b9e4-1b89cc2df439" />

**Mis citas**

<img width="378" height="767" alt="image" src="https://github.com/user-attachments/assets/da7aab52-615e-45f6-8bcd-9a6c416573e9" />

**AlertDialog de cancelación**

<img width="378" height="773" alt="image" src="https://github.com/user-attachments/assets/ae6396e5-a9af-4c84-bcbd-8794b3dea2e3" />

**Historial médico**

<img width="387" height="764" alt="image" src="https://github.com/user-attachments/assets/ce68d330-62c2-4ef8-a952-408040fd2090" />

**Perfil**

<img width="376" height="780" alt="image" src="https://github.com/user-attachments/assets/c094a65e-686f-4f0c-a295-ba2e1dddc6ce" />


## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar Gradle
4. Ejecutar en emulador o dispositivo con API 24 o superior

## Ramas

- `main` — desarrollo sin asistentes de IA
- `mejora-ia` — mejora visual y funcional realizada con IA, documentada en PROMPTS.md
