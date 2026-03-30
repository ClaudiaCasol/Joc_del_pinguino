CREATE OR REPLACE TYPE casillas_varray AS VARRAY(50) OF VARCHAR2(20);
/

CREATE TABLE USUARIO (
id_usuario NUMBER(4) PRIMARY KEY,
nombre VARCHAR2(10) NOT NULL,
contraseña VARCHAR2(10) NOT NULL
);

CREATE TABLE PARTIDA (
id_partida NUMBER(10) PRIMARY KEY,
turnos NUMBER(3) NOT NULL,
jugador_actual NUMBER(1),
tablero casillas_varray NOT NULL,
fecha_partida DATE DEFAULT SYSDATE,
id_usuario NUMBER(4),

CONSTRAINT fk_usuario_partida
FOREIGN KEY (id_usuario)
REFERENCES USUARIO(id_usuario)
);

CREATE TABLE JUGADOR (
id_jugador NUMBER(10) PRIMARY KEY,
nombre VARCHAR2(10) NOT NULL UNIQUE, 
color VARCHAR(7) NOT NULL,
foca VARCHAR2(2) NOT NULL CHECK(foca IN ('SI', 'NO')),
num_partidas_jugadas NUMBER(2),
posicion NUMBER(2) NOT NULL,
turnos_perdidos NUMBER(2) NOT NULL,
id_partida NUMBER(10),

CONSTRAINT fk_partida_jugador
FOREIGN KEY (id_partida)
REFERENCES PARTIDA(id_partida)
);

CREATE TABLE INVENTARIO (
id_inventario NUMBER(5) PRIMARY KEY,
num_peces NUMBER(1) NOT NULL,
num_bolanieve NUMBER(1) NOT NULL,
num_dado_normal NUMBER(1) NOT NULL,
num_dado_rapido NUMBER(1) NOT NULL,
num_dado_lento NUMBER(1) NOT NULL,
id_jugador NUMBER(10),

CONSTRAINT fk_jugador_inventario
FOREIGN KEY (id_jugador)
REFERENCES JUGADOR(id_jugador)
);
