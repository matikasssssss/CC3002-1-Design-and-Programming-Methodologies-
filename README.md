# Final Reality Tactics (Actualización entrega final 3)
Aquí se aclararán algunos puntos importantes respecto a esta entrega final 1, para que así sean tomados en consideración y puedan aclarar más que una duda.

ACTUALIZACIÓN ENTREGA FINAL 2: Para la entrega final 2 se realizó la implementación del double dispatch para las acciones, además de implementar las exceptions, generando así el poder desambiguar clases y dejar más funcional nuestro juego.

Lamentablemente, para esta entrega no logré terminar todos los test, aunque el programa compila, no están hechos todos los test, por lo cual queda pendiente para la siguiente entrega, abajo detallaré un poco más lo que hice.

ACTUALIZACIÓN ENTREGA FINAL 3: Para esta entrega final se realizó la implementación del observer para así notificar al GameController lo que está sucediendo, y así poder tener más control sobre el juego.

Desafortunadamente, no logré implementar el patrón de diseño Fabric, por cosas de tiempo más que nada, pero queda como futura implementación pues creo que el proyecto va bien encaminado.

## Carpeta Models
-[Actions](#actions)
-[Entity](#entity)
-[Panels](#panels)
-[Player](#player)
-[Scheduler](#scheduler)
-[Utilizable](#utilizable)
-[Double Dispatch](#double-dispatch-actualización-entrega-final-2)
-[Exceptions](#exceptions-actualización-entrega-final-2)
-[Pattern Matching](#pattern-matching-actualización-entrega-final-3)
-[Observer](#observer-actualización-entrega-final-3)

## Actions
Para este apartado no hubo mucho más allá que detallar la verdad, fue bastante guíado.
1. Primero tenemos la creación del Trait 'Actions', el cual tiene la definición de los atributos y métodos generales para cada clase.
2. Se subdivió en carpetas cada una de estas "acciones", quedando 4 en total (attack, equip, magic, movement), donde cada una tiene su documentación respectiva para lograr entender qué hace cada cosa.
3. Con respecto a los test de este apartado, se tomó el tiempo de testear cada clase e implementación hecha.
4. (Actualización) Ahora se implementó cada acción haciendo uso de DD, lo cual a mi parecer fue bien llevado a cabo.

## Entity
Para este apartado, también fue bastante guiado, se focalizó en crearse dos carpetas que almacenaran todos los tipos de unidades presentes en el juego (character y enemy).
1. Character contiene los personajes que son mágicos y comunes, los cuales nacen de un trait llamado Entity, en el cual se definen los atributos generales de cada uno.
2. Enemy por su parte, contiene a los enemigos, tomé esta decisión ya que me parecía lo más "ordenado" más que nada, el hecho de que tenga su propio trait y no extienda desde el mismo que un "Character" es meramente por la estética y porque en un principio no me calzaban todos los atributos. El trait del cual extiende enemy se llama "IEnemy" y contiene todos los atributos de un enemigo.
3. Para esta entrega no era necesario crear métodos para equipar un arma o alguna cosa relacionada con el inventario, solo había que atribuirle al personaje tales capacidades, por lo que tanto el "weaponsSlot" como el "inventory" son atributos que pertenecen a cada "character" y son inicialmente vacios o none, en los test, estos apartados fueron atribuidos apenas se instanciaba el personaje en cuestión, por ejemplo, para el apartado del arma del personaje, se le atribuye manualmente a este (se puede ver mejor reflejado en los test), para que así funcionara todo correctamente. Otro consideración a tener, es que se creó el método "getWeapon", el cual simplemente muestra el si hay un "weaponsSlot", esto ayudó bastante para el programador de turnos.
4. (Actualización) Se creo un trait Global que engloba tanto Enemys como characters comunes, además se testeo todo lo descontado en la entrega anterior.
5. (Actualizacion entrega final 3) Se implementó todo lo faltante con pattern matching, haciendo que cada tipo específico de este superia qué arma equiparse o qué posición consumir.

## Panels
En este caso, tuve que hacer uso de la imaginación y cómo creí que se vería mejor, por lo que se subdividió en dos carpetas (map y panels).
1. La carpeta map, contiene la clase Map_, la cual como su nombre lo dice, representa el "mapa" el cual está formado por estos paneles, en este se implementa un método que nos dice si un panel existe realmente en las coordenadas entregadas, si este no existe se devuelve un None. Esta clase es de un rasgo "auxiliar", ya que le sirve bastante a la clase que viene a continuación.
2. Por otro Panel extiende de su trait llamado IPanel, el cual define todos los atributos necesarios para definir un panel. Por su parte, Panel implementa el método para obtener los vecinos de un panel, este método en concreto recibe como parámetro un Map_, y de vuelve una lista con los paneles que en teoría son vecinos al panel consultado.
3. Tomé esta decisión en hacer este apartado de tal forma, ya que más que nada me permitía tener control en todo momento de lo que pasaba, además logra su cometido de forma bastante eficaz por como se muestra en los test hechos.
4. (Actualización) Aquí también se testeo todo lo descontado anteriormente, además de hacerla extender ahora de target, por lo cual se tuvo que implementar todo lo que se tenía antes.
5. (Actualizacion entrega final 3) Se agregaron las exceptions faltantes para que lograra compilar bien.

## Player
Para este apartado, fue bastante guíado, consta de una sola clase.
1. Se crean dos métodos, los cuales sirven para saber si un jugador fue vencido o no, y otro para obtener las unidades de tal jugador.
2. Un rasgo a tener a consideración, es que para la implementación del visualizador se le tuvo que agregar el atributo "id", pero este en teoría no se usa, pero para el correcto funcionamiento de toJson se tuvo que agregar, por lo que en los test también tienen un id cualquiera.
3. (Actualizacion entrega final 3) Se agregó el método removeUnit, para así poder remover una unidad dependiendo si fue derrotada o no.

## Scheduler
El programador de turnos fue uno de los apartados donde más tuve que tomar responsabilidades y asumir alguna que otra cosa. Consta de una sola clase, la cual implementa los métodos solicitados en la última entrega, procederé a mencionar las consideraciones a tener,
1. Se crearon los "values" maxActionBar y currentActionBar, los cuales deben devolver un Map mutable. Esto se hizo así más que nada para poder lograr implementar de buena forma y tener constancia de cuál era la barra de acción máxima y cómo esta variaba en el transcurso del juego y así que el programador de turnos pueda ir almacenando cada uno de estos valores.
2. El método "getUnitsWithFullActionBar", se realizó de esta forma ya que en un principio me arrojaba error de tipos, por lo que me tuve que poner a investigar, ya que primeramente se filtran todas las unidades que no han completado su action bar, y luego se reordenan, pero debía devolver un ListBuffer, así que buscando en internet me di cuenta que el operador "_*" me servía para pasar todo lo que estaba haciendo a un ListBuffer, no sé si es algo que se puede utilizar en el curso, pero no encontré otra forma. Este método es privado ya que luego se usa en "getUnitTurn", en un principio el usuario no necesita saber las unidades que ya completaron su action bar, solo necesita saber de quién es el próximo turno.
3. Los demás métodos fueron bastante guíados, teniendo en cuenta que estamos trabajando con Maps, por lo que cada método fue ajustado a eso.
4. (Actualización) Se testeo todo lo que no se había testeado en la anterior entrega, menos una que no supe cómo resolver, que es sobre el control de todas las unidades que tienen su barra de acción al máximo, lo solventaré para la próxima entrega.
5. (Actualización entrega final 3) Se hizo extender de nuestro AbstractSubject, para que así tenerlo como el "objeto" observado. AbstractSubject implementa los métodos addObserver, removeObserver y notify, así podremos agregar observadores a nuestro TurnScheduler (en este caso agregarémos como observador al GameController) y así poder notificarle de cualquier cambio que ocurra.

## Utilizable
Este apartado fue bastante guíado, consta de un trait general llamado "Utilizable" y dos carpetas (Potions y Weapons).
1. Con respecto a Potions no hay mucho que recalcar, bastante similar a lo que se hizo con las "Actions".
2. Por otro lado, para las Weapons tuve un problema con el método "equals", el cual necesitaba que fuera distinto para las armas mágicas y las comunes, pero no supe cómo lograrlo, por lo que el equal lo dejé definido en la abstract class, pero para las armas mágicas quería que también se considerara que un arma mágica es igual a otra cuando también tienen el mismo ataque mágico.
3. (Actualización) En cada utilizable se realizó alguna implementación que luego sirvió para hacer el DD.
4. (Actualizacion entrega final 3) También aquí se implementó todo lo faltante relacionado a las pociones con pattern matching, para que lograran realizar su efecto de buena forma.

## Double Dispatch (actualización entrega final 2)
Aquí el eje principal fue desambiguar los tipos para cada target o source, de tal forma que al obtener tales traits, definí los métodos que ya tenía anteriormente, donde simplemente luego el programa se encargará de derivar a los métodos de los source/target correspondientes.
Algunos métodos no correspondian a algunos tipos, por ejemplo para enemy que no puede equiparse armas o consumir posiciones, para ello fue el próximo apartado.
Se definieron variados handle para cada tipo de acción, de tal forma que quedara ordenado y el DD se realizara de buena forma.
(Actualizacion entrega final 3) Se cambió todo por pattern matching.

## Exceptions (actualización entrega final 2)
Los exceptions fueron de mucha utilidad a la hora de no saber qué hacer con métodos que no podían ser implementados en algún tipo, como se dio anteriormente como ejemplo, enemy que no puede equiparse weapons, debemos lanzar una exception que diga esto, de tal forma nuestro programa no se caerá, creo haber abarcado a todas las exceptions dichas, a lo más se me pasaron 1 o 2. Casi todas las exceptions definidas fueron testeadas y se logró el cometido con esto.
(Actualizacion entrega final 3) Se implementaron algunas faltantes y se borraron otras.

## Pattern Matching (actualización entrega final 3)
Fue un remedio para todo el caos que tenía anteriormente, logré implementar todo lo que no pude con DD en la anterior entrega, además de haciendo uso exhaustivo para lograr tener la mayoría de implementaciones testeadas, me sirvió mucho además para implementar los métodos en el GameController y creo que se logró el cometido para la implementación general del Obsever.

## Observer (actualización entrega final 3)
Sirvió para, como se dijo anteriormente, notificar a nuestro GameController toda acción que esté sucediendo, y dependiendo de qué mensaje arroja, haga una cosa u otra. Se testeó todo lo relacionado a este, además de testear todo lo implementado en el GameController.
