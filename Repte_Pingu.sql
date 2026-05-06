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
nombre VARCHAR2(10) NOT NULL,
color VARCHAR2(7) NOT NULL,

foca VARCHAR2(2) NOT NULL CHECK(foca IN ('SI', 'NO')),

num_partidas_jugadas NUMBER(3) DEFAULT 0,
num_partidas_ganadas NUMBER(3) DEFAULT 0,

posicion NUMBER(2) NOT NULL,
turnos_perdidos NUMBER(2) NOT NULL,

id_partida NUMBER(10),

CONSTRAINT fk_partida_jugador
FOREIGN KEY (id_partida)
REFERENCES PARTIDA(id_partida)
);
CREATE TABLE INVENTARIO (
id_inventario NUMBER(5) PRIMARY KEY,
num_peces NUMBER(2) NOT NULL,
num_bolanieve NUMBER(2) NOT NULL,
num_dado_normal NUMBER(2) NOT NULL,
num_dado_rapido NUMBER(2) NOT NULL,
num_dado_lento NUMBER(2) NOT NULL,
id_jugador NUMBER(10),

CONSTRAINT fk_jugador_inventario
FOREIGN KEY (id_jugador)
REFERENCES JUGADOR(id_jugador)
);

CREATE OR REPLACE FUNCTION existe (v_nombre VARCHAR2, v_contraseña VARCHAR2)
RETURN VARCHAR2

IS
    v_res VARCHAR2(2);
    v_num NUMBER(10);
    
BEGIN
    SELECT COUNT(*) INTO v_num
    FROM USUARIO
    WHERE nombre = v_nombre AND contraseña = v_contraseña;
    
    IF v_num > 0 THEN
    v_res := 's';
    
    ELSE
    v_res := 'n';
    END IF;
RETURN v_res;
END;
/

SET SERVEROUTPUT ON;

BEGIN
EXISTE('zoro', 'reto');
END;
/

-- PL/SQL QUE ES DEMANA PL JOC DEL PINGU:

-- 1
CREATE SEQUENCE seq_partida
START WITH 1
INCREMENT BY 1;
/

-- 2
CREATE OR REPLACE TRIGGER trg_id_partida
BEFORE INSERT ON PARTIDA
FOR EACH ROW

BEGIN

    :NEW.id_partida := seq_partida.NEXTVAL;

END;
/

-- 3
ALTER TABLE PARTIDA --afegim aquestes columnes perquè de principi no està acabada la partida.
ADD (
    ganador NUMBER(10),
    finalizada VARCHAR2(2) DEFAULT 'NO'
);

CREATE OR REPLACE TRIGGER trg_sumar_victoria
AFTER UPDATE OF finalizada
ON PARTIDA
FOR EACH ROW

BEGIN

    IF :NEW.finalizada = 'SI' THEN

        UPDATE JUGADOR
        SET num_partidas_ganadas =
            NVL(num_partidas_ganadas,0) + 1

        WHERE id_jugador = :NEW.ganador;

    END IF;

END;
/

-- 4
CREATE OR REPLACE FUNCTION record_victorias
RETURN NUMBER

IS

    v_record NUMBER;

BEGIN

    SELECT MAX(num_partidas_ganadas)
    INTO v_record
    FROM JUGADOR;

    RETURN v_record;

END;
/

-- 5
CREATE OR REPLACE PROCEDURE jugadores_record
IS
BEGIN

    FOR jugador IN (
        SELECT nombre,
               num_partidas_ganadas
        FROM JUGADOR
        WHERE num_partidas_ganadas =
              (SELECT MAX(num_partidas_ganadas)
               FROM JUGADOR)
    )
    LOOP

        DBMS_OUTPUT.PUT_LINE(
            jugador.nombre || ' - ' ||
            jugador.num_partidas_ganadas || ' victorias'
        );
    END LOOP;
END;
/

-- 6
CREATE OR REPLACE FUNCTION media_victorias
RETURN NUMBER

IS

    v_media NUMBER;

BEGIN

    SELECT AVG(num_partidas_ganadas)
    INTO v_media
    FROM JUGADOR;

    RETURN ROUND(v_media,2);

END;
/

-- 7
CREATE OR REPLACE PROCEDURE jugadores_superior_media

IS
    v_media NUMBER;
    
BEGIN
    SELECT AVG(num_partidas_ganadas)
    INTO v_media
    FROM JUGADOR;

    FOR jugador IN (
        SELECT nombre,
               num_partidas_ganadas
        FROM JUGADOR
        WHERE num_partidas_ganadas > v_media
    )
    LOOP

        DBMS_OUTPUT.PUT_LINE(
            jugador.nombre || ' - ' ||
            jugador.num_partidas_ganadas
        );
    END LOOP;
END;
/

-- 8
CREATE OR REPLACE FUNCTION porcentaje_inferior(v_victorias NUMBER)

RETURN NUMBER
IS
    v_total NUMBER;
    v_inferior NUMBER;

BEGIN

    SELECT COUNT(*)
    INTO v_total
    FROM JUGADOR;

    IF v_total = 0 THEN
        RETURN 0;
    END IF;

    SELECT COUNT(*)
    INTO v_inferior
    FROM JUGADOR
    WHERE num_partidas_ganadas < v_victorias;

    RETURN ROUND( (v_inferior / v_total) * 100, 2 );

END;
/

-- 9
CREATE OR REPLACE TRIGGER trg_porcentaje
AFTER UPDATE OF num_partidas_ganadas
ON JUGADOR
FOR EACH ROW

DECLARE
    v_porcentaje NUMBER;
    v_total NUMBER;
    v_inferior NUMBER;

BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM JUGADOR;

    SELECT COUNT(*)
    INTO v_inferior
    FROM JUGADOR
    WHERE num_partidas_ganadas < :NEW.num_partidas_ganadas;

    v_porcentaje :=
        ROUND((v_inferior / v_total) * 100,2);

    DBMS_OUTPUT.PUT_LINE(
        'Porcentaje inferior: ' ||
        v_porcentaje || '%'
    );

END;
/

-- 10
CREATE OR REPLACE PROCEDURE ranking_jugadores
IS
BEGIN

    FOR jugador IN (
        SELECT nombre,
               num_partidas_jugadas
        FROM JUGADOR
        ORDER BY num_partidas_jugadas DESC
    )
    LOOP

        DBMS_OUTPUT.PUT_LINE(
            jugador.nombre || ' - ' ||
            jugador.num_partidas_jugadas ||
            ' partidas' );

    END LOOP;
END;
/

-- 11
CREATE OR REPLACE FUNCTION posicion_ranking(v_nombre VARCHAR2)
RETURN NUMBER
IS
    v_posicion NUMBER;
    v_existe NUMBER;
    v_partidas NUMBER;

BEGIN
    SELECT COUNT(*)
    INTO v_existe
    FROM JUGADOR
    WHERE nombre = v_nombre;

    IF v_existe = 0 THEN

        RAISE_APPLICATION_ERROR( -20001,'El jugador no existe');

    END IF;

    SELECT num_partidas_jugadas
    INTO v_partidas
    FROM JUGADOR
    WHERE nombre = v_nombre;

    IF v_partidas = 0 THEN

        RAISE_APPLICATION_ERROR( -20002, 'El jugador no ha jugado partidas');

    END IF;

    SELECT posicion
    INTO v_posicion

    FROM (

        SELECT nombre,

               RANK() OVER (ORDER BY num_partidas_jugadas DESC) posicion

        FROM JUGADOR
        )

    WHERE nombre = v_nombre;

    RETURN v_posicion;

END;
/

