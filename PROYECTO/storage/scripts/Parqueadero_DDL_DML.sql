-- database: storage\Databases\Parqueadero.sqlite


DROP TABLE IF EXISTS Parqueadero;
DROP TABLE IF EXISTS RegistroMovimiento;
DROP TABLE IF EXISTS SensorEvento;


CREATE TABLE Parqueadero (
     IdParqueadero   INTEGER PRIMARY KEY AUTOINCREMENT
    ,Nombre          VARCHAR(30) NOT NULL UNIQUE
    ,CapacidadTotal  INTEGER     NOT NULL
    ,Estado          CHAR(1)     NOT NULL DEFAULT 'A'
    ,FechaCreacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModifica   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);


CREATE TABLE RegistroMovimiento (
     IdRegistro          INTEGER PRIMARY KEY AUTOINCREMENT
    ,IdParqueadero       INTEGER NOT NULL DEFAULT 1 REFERENCES Parqueadero (IdParqueadero)
    ,Placa               VARCHAR(10) NOT NULL
    ,NombreResponsable   VARCHAR(50) NOT NULL
    ,CedulaResponsable   VARCHAR(10) NOT NULL
    ,FechaIngreso        DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaSalida         DATETIME NULL
    ,Estado              CHAR(1)  NOT NULL DEFAULT 'A' 
    ,FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModifica       DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE SensorEvento (
     IdEvento        INTEGER PRIMARY KEY AUTOINCREMENT
    ,DistanciaCM     REAL        NOT NULL
    ,Decision        VARCHAR(10) NOT NULL  -- REGISTRADO / IGNORADO
    ,Estado          CHAR(1)     NOT NULL DEFAULT 'A'
    ,FechaCreacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModifica   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);


INSERT INTO Parqueadero (Nombre, CapacidadTotal) 
VALUES ('Parqueadero EPN', 2);



SELECT * FROM Parqueadero;

SELECT COUNT(*) AS Ocupados FROM RegistroMovimiento WHERE Estado = 'X';

SELECT 
     P.Nombre
    ,P.CapacidadTotal
    ,(SELECT COUNT(*) FROM RegistroMovimiento WHERE Estado = 'A') AS Ocupados
    ,(P.CapacidadTotal - (SELECT COUNT(*) FROM RegistroMovimiento WHERE Estado = 'D')) AS Libres
FROM Parqueadero P
WHERE P.IdParqueadero = 1;