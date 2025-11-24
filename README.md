# MultiplatformIoT 🌐

Una aplicación multiplataforma moderna para el control y monitoreo de dispositivos IoT basados en Arduino, desarrollada con Kotlin Multiplatform (KMP). Esta solución permite gestionar tus dispositivos Arduino y ESP32 de manera eficiente a través de diferentes plataformas: Android, iOS, Desktop y Web.

## 🚀 Características Principales

- 📱 Conexión multiplataforma con dispositivos Arduino y ESP32
- 🔄 Sincronización en tiempo real 
- 🌐 Soporte para conexiones wifi local, Internet, Bluetooth classic, Blurtooth LE
- 🔐 Gestión segura de dispositivos
- 📊 Monitoreo en tiempo real
- 🎨 Interfaz de usuario moderna y atractiva

## 🛠️ Tecnologías Principales

### Kotlin Multiplatform
- **Compose Multiplatform**: Framework UI moderno para crear interfaces de usuario consistentes en todas las plataformas
- **Kotlin Coroutines**: Para manejo asíncrono y concurrente
- **Kotlin Serialization**: Serialización de datos eficiente y type-safe

### Arquitectura y Patrones
- **Clean Architecture**: Separación clara de responsabilidades en capas (Data, Domain, Presentation) MultiModule
- **MVVM**: Patrón de arquitectura para una mejor separación de la lógica de UI y negocio
- **Repository Pattern**: Para abstracción de fuentes de datos
- **Navigation Component**: Sistema de navegación type-safe y predecible

### Inyección de Dependencias
- **Koin**: Framework ligero de inyección de dependencias para Kotlin
  - Configuración sencilla
  - Soporte multiplataforma nativo
  - Integración con ViewModel

### Networking
- **Ktor**: Cliente HTTP multiplataforma
  - Soporte para comunicación asíncrona
  - Configuración flexible
  - Integración nativa con Kotlin Coroutines

### Gestión de Imágenes
- **Coil**: Biblioteca de carga de imágenes moderna
  - Carga eficiente de imágenes
  - Caché inteligente
  - Soporte para Compose

## 🏗️ Estructura del Proyecto Multi-Modulo

```
MultiplatformIoT/
├── composeApp/        # Implementación de UI compartida
├── iosApp/           # Código específico de iOS
├── data/            # Capa de datos
├── domain/          # Lógica de negocio
├── feature/         # Módulos de características
└── ui/              # Componentes UI compartidos
```

## 🌟 Características por Plataforma

### 📱 Android
- Interfaz nativa con Jetpack Compose
- Integración con servicios de Google
- Soporte para conexión Bluetooth

### 🍎 iOS
- Integración con servicios de Apple
- Optimización para dispositivos iOS

### 🖥️ Desktop
- Interfaz adaptada para escritorio
- Soporte para múltiples ventanas
- Atajos de teclado personalizados
- 

## 📱 Capturas de Pantalla

<table>
  <tr>
    <td align="center"><strong>Home con Video</strong></td>
    <td align="center"><strong>Cart</strong></td>
  </tr>
  <tr>
    <td width="50%">
      <video src="https://github.com/user-attachments/assets/caad5e71-6b95-4a8c-96fb-fb21f5754f02.mp4" autoplay loop muted playsinline width="100%"></video>
    </td>
    <td width="50%">
      <img src="https://github.com/user-attachments/assets/517de1c7-a34a-4d06-af34-8a906d2c3aad" width="100%">
    </td>
  </tr>
  <tr>
    <td align="center"><strong>Orders</strong></td>
    <td align="center"><strong>Sesion</strong></td>
  </tr>
  <tr>
    <td width="50%">
      <img src="https://github.com/user-attachments/assets/da4e2f96-cc5f-4d05-b949-abbb73b6bfea" width="100%">
    </td>
    <td width="50%">
      <img src="https://github.com/user-attachments/assets/08cff33a-dcb6-4bf1-a5c7-8a18dd6a5fdf" width="100%">
    </td>
  </tr>
   <tr>
    <td align="center"><strong>Date</strong></td>
    <td align="center"><strong>Detail</strong></td>
  </tr>
  <tr>
    <td width="50%">
      <img src="https://github.com/user-attachments/assets/3a653c70-e90f-46bf-9b6a-214ada60d06a" width="100%">
    </td>
    <td width="50%">
      <img src="https://github.com/user-attachments/assets/6cc53ecb-d1e3-45a5-8ac0-e73d675eefa3" width="100%">
    </td>
  </tr>
</table>








