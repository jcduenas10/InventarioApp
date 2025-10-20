¿Qué es un Managed Bean y en qué se diferencia de un JavaBean?
Un JavaBean es una clase Java simple (POJO) con constructor sin argumentos, propiedades privadas y métodos getter/setter. No tiene relación con el contenedor, por lo tanto no se inyecta ni se gestiona automáticamente.
Un Managed Bean (CDI/Jakarta) es una clase gestionada por el contenedor, que puede:

-Definir scopes (alcances de vida) como @RequestScoped, @SessionScoped, @ApplicationScoped o @Dependent.
-Ser inyectada con @Inject en otras clases.
-Exponerse a la vista JSF con @Named para usarse con EL (#{bean}).
-Participar en eventos CDI, interceptores y productores de recursos.

Por ejemplo en este taller:
Producto es un JavaBean (modelo de datos),
mientras que ProductoFacade, MensajeBean, PreferenciasBean y ValidadorProducto son Managed Beans (gestionados por CDI).

Anotaciones principales y su función
@Named: Expone el bean a la vista (JSF/EL). Permite usar #{bean} en .xhtml.
@ApplicationScoped:Un solo bean compartido por toda la aplicación (vida = tiempo de despliegue). Ideal para servicios o métricas globales.
@RequestScoped:Se crea y destruye con cada request HTTP. Perfecto para mensajes o validaciones temporales.
@SessionScoped: Persiste durante la sesión de usuario. Mantiene preferencias o filtros personales.
@Dependent: Sin contexto propio, se destruye con quien lo usa. Ideal para utilidades livianas sin estado.


Diagramas:
<img width="806" height="921" alt="Mapa Conceptual" src="https://github.com/user-attachments/assets/72b5f4c8-46bf-4f13-a53c-c1cc3a0f1dc0" />
<img width="1350" height="1079" alt="Diagrama 1 UML" src="https://github.com/user-attachments/assets/d04a1076-f126-4224-8ff0-c8ed01f332e2" />


Capturas de funcionamiento:
![Captura de pantalla 2025-10-20 173332](https://github.com/user-attachments/assets/c204d2f5-8d52-4bb2-b2c3-5d09713f3445)
![Captura de pantalla 2025-10-20 173431](https://github.com/user-attachments/assets/78441da4-5d80-48ea-a880-ba8859da3351)
![Captura de pantalla 2025-10-20 173404](https://github.com/user-attachments/assets/32eb3164-f578-47bd-bc18-0f1a2edc1f67)
