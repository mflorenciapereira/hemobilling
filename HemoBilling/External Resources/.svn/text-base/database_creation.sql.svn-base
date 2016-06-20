-- USUARIOS Y ROLES -----------------------------------------------------------

CREATE
    TABLE users
    (
        username VARCHAR(50) NOT NULL,
        password VARCHAR(50) NOT NULL,
        enabled TINYINT(1) NOT NULL,
        name VARCHAR(50) NOT NULL,
        PRIMARY KEY (username)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE
    TABLE authority_types
    (
        authority VARCHAR(50) NOT NULL,
        description text NOT NULL,
        PRIMARY KEY (authority)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE
    TABLE authorities
    (
        username VARCHAR(50) NOT NULL,
        authority VARCHAR(50) NOT NULL,
        PRIMARY KEY (username, authority),
        INDEX idx_authorities_type (authority), 
        CONSTRAINT fk_authorities_type FOREIGN KEY (authority) REFERENCES authority_types (authority),
        CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- CLIENTES  ---------------------------------------------------------------------------

CREATE
    TABLE clientes
    (
    	codigo bigint NOT NULL AUTO_INCREMENT,
        codigoContable VARCHAR(50) NULL,
        nombre VARCHAR(300) NOT NULL,
        direccion VARCHAR(100),
        localidad VARCHAR(50),
        provincia VARCHAR(50),
        pais VARCHAR(50),
        codigoPostal VARCHAR(50),
        telefono VARCHAR(50),
        telefonoGratuito VARCHAR(50),
        email VARCHAR(50),
        website VARCHAR(50),
        tipoIVAid bigint,
        cuit VARCHAR(20),
        PRIMARY KEY (codigo), 
        INDEX idx_cuit (codigo) 
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Clientes de Hemobilling';


-- PRESTACIONES ---------------------------------------------------------------------------

CREATE TABLE prestaciones ( codigo bigint(20) NOT NULL, descripcion varchar(50) NOT NULL, PRIMARY KEY (codigo) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE prestacionesAsociadas ( codigoModulo bigint(20) NOT NULL, codigoDeterminacion bigint(20) NOT NULL, orden int not null ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE UNIQUE INDEX ix_codigoModulo_codigoDeterminacion ON prestacionesAsociadas (codigoModulo, codigoDeterminacion);
CREATE INDEX fk_prestacion_asociada ON prestacionesAsociadas (codigoDeterminacion);
ALTER TABLE prestacionesAsociadas ADD CONSTRAINT fk_prestaciones_determinacion FOREIGN KEY (codigoDeterminacion) REFERENCES prestaciones (codigo) ;
ALTER TABLE prestacionesAsociadas ADD CONSTRAINT fk_prestaciones_modulo FOREIGN KEY (codigoModulo) REFERENCES prestaciones (codigo);


-- TIPOS DE IVA ----------------------------------------------------------------------------

CREATE TABLE tiposIVA ( id bigint(20) NOT NULL, descripcion varchar(50) NOT NULL, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- OBRAS SOCIALES Y PLANES ------------------------------------

CREATE
    TABLE obrassociales
    (
        codigo bigint NOT NULL AUTO_INCREMENT,
        nombre VARCHAR(300) NOT NULL,
        gerenciadora VARCHAR(300),
        RegNOS VARCHAR(50),
        sigla VARCHAR(50),
        prestador VARCHAR(300),
        codigoContable VARCHAR(50) NULL,
        cuit VARCHAR(20),
        telefono VARCHAR(50),
        telefonoGratuito VARCHAR(50),
        email VARCHAR(50),
        website VARCHAR(50),
        direccion VARCHAR(100),
        localidad VARCHAR(50),
        provincia VARCHAR(50),
        pais VARCHAR(50),
        codigoPostal VARCHAR(50),
        tipoIVAid bigint,
        tipoFactura CHAR(1) DEFAULT 'D',
        PRIMARY KEY (codigo),
        CONSTRAINT FK_TIPOIVA FOREIGN KEY (tipoIVAid) REFERENCES tiposiva (id),
        INDEX FK_TIPOIVA (tipoIVAid)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE
    TABLE planes
    (
        id bigint(20) NOT NULL AUTO_INCREMENT,
        codigo VARCHAR(50) NULL,
        nombre VARCHAR(50) NULL,
        obrasocialid bigint(20) NOT NULL,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE planes ADD CONSTRAINT fk_obra_social FOREIGN KEY (obrasocialid) REFERENCES obrassociales (codigo);



-- LISTAS DE PRECIO ------------------------------------------

CREATE TABLE listasprecio ( codigo bigint(20) NOT NULL AUTO_INCREMENT,  nombre varchar(50) NULL, 
PRIMARY KEY (codigo)) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE
    TABLE itemslistaprecio
    (
        id bigint NOT NULL AUTO_INCREMENT,
        codigoSegunOS VARCHAR(50),
        precio DOUBLE,
        prestacionid bigint NOT NULL,
        listaprecioid bigint NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_LP_ITEMLP FOREIGN KEY (listaprecioid) REFERENCES listasprecio (codigo) ,
        CONSTRAINT fk_PRESTACION_ITEMLP FOREIGN KEY (prestacionid) REFERENCES prestaciones (codigo),
        INDEX fk_PRESTACION_ITEMLP (prestacionid),
        INDEX fk_LP_ITEMLP (listaprecioid)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1



-- ASOCIACION LISTA PRECIO Y OBRA SOCIAL ---------------------
CREATE TABLE asociacionoslp (  id bigint(20) NOT NULL AUTO_INCREMENT, desde DATE NULL, hasta DATE NULL, obrasocialid bigint(20) NOT NULL, listaprecioid bigint(20) NOT NULL, 
PRIMARY KEY (id), 
INDEX IN_OBRA_SOCIAL_ASOCIADA(obrasocialid),
INDEX IN_LISTA_PRECIO_ASOCIADA(listaprecioid)) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE asociacionoslp ADD CONSTRAINT FK_OBRASOCIAL_ASOCIADA FOREIGN KEY (obrasocialid) REFERENCES obrassociales (codigo);
ALTER TABLE asociacionoslp ADD CONSTRAINT FK_LISTAPRECIO_ASOCIADA FOREIGN KEY (listaprecioid) REFERENCES listasprecio (codigo);



-- PACIENTES -----------------------------------------------

CREATE
    TABLE pacientes
    (
        id bigint NOT NULL AUTO_INCREMENT,
        dni VARCHAR(30),
        tipoDocumento VARCHAR(5),
        nombreyApellido VARCHAR(50) NOT NULL,
        numHistoriaClinica bigint,
        sexo VARCHAR(1),
        estadoCivil VARCHAR(1),
        fechaNacimiento DATE,
        direccion VARCHAR(50),
        localidad VARCHAR(50),
        provincia VARCHAR(50),
        pais VARCHAR(30),
        codigoPostal VARCHAR(20),
        telefono VARCHAR(50),
        celular VARCHAR(50),
        email VARCHAR(50),
        diagnostico VARCHAR(30),
        severidad VARCHAR(30),
        usaInibidor VARCHAR(1) DEFAULT '0' NOT NULL,
        tipoHemofilia VARCHAR(30),
        grupoSanguineo VARCHAR(30),
        factorSangre VARCHAR(30),
        nroAfiliadoOSActual VARCHAR(30),
        codObraSocialActual bigint  NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (codObraSocialActual) REFERENCES obrassociales (codigo),
        INDEX codObraSocialActual (codObraSocialActual)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    
CREATE  
    TABLE obrassocialespacientes
    (
        idPaciente bigint NOT NULL,
        codObraSocial bigint NOT NULL,
        PRIMARY KEY (idPaciente, codObraSocial),
        FOREIGN KEY (idPaciente) REFERENCES pacientes (id),
        FOREIGN KEY (codObraSocial) REFERENCES obrassociales (codigo),
        INDEX codObraSocial (codObraSocial), 
        INDEX idPaciente (idPaciente) 
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

    
    
    
-- PRESTACIONES BRINDADAS -----------------------------------------------

CREATE
    TABLE prestacionesbrindadas
    (
        codigo bigint NOT NULL AUTO_INCREMENT,
        fecha DATE NOT NULL,
        fechaEgreso DATE,
        idPaciente bigint NOT NULL,
        cantidadDePrestaciones bigint NOT NULL,
        codigoPrestacion bigint NOT NULL,
        autorizacion VARCHAR(30),
        profesional VARCHAR(300),
        observaciones VARCHAR(300),
        fechaImportacion DATETIME,
        precioManual DOUBLE,
        PRIMARY KEY (codigo),
        FOREIGN KEY (idPaciente) REFERENCES pacientes (id),
        FOREIGN KEY (codigoPrestacion) REFERENCES prestaciones (codigo),
        INDEX idPaciente (idPaciente),
        INDEX codigoPrestacion (codigoPrestacion)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
 -- observaciones -----------------------------------------------
    
    CREATE
    TABLE observaciones
    (
        descripcion VARCHAR(300),
        PRIMARY KEY (descripcion),
        INDEX descripcion (descripcion)
        
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

    
    
-- FACTURAS CARGADAS EN EL SISTEMA -----------------------------------------------
    
CREATE
    TABLE facturas
    (
        id bigint NOT NULL AUTO_INCREMENT,
        numero VARCHAR(20) NOT NULL,
        fecha DATE NOT NULL,
        codigoContable VARCHAR(20) NULL,
        observaciones VARCHAR(300) NULL,
        anulada TINYINT(1) DEFAULT '0' NOT NULL,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE
    TABLE facturasPrestaciones
    (
        id bigint NOT NULL,
        codigoOS bigint,
        idPaciente bigint,
        PRIMARY KEY (id),
        INDEX idx_factura (id), 
        INDEX idx_codigo_os (codigoOS),
        INDEX idx_idpaciente (idPaciente), 
        CONSTRAINT fk_factura_prestaciones FOREIGN KEY (id) REFERENCES facturas (id),
        CONSTRAINT fk_codigo_os FOREIGN KEY (codigoOS) REFERENCES obrassociales (codigo),
        CONSTRAINT fk_id_paciente FOREIGN KEY (idPaciente) REFERENCES pacientes (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;
    

CREATE
    TABLE facturasServicios
    (
        id bigint NOT NULL,
        codigoCliente bigint NOT NULL,
        PRIMARY KEY (id),
        INDEX idx_idFactura (id), 
        INDEX idx_codigo_cliente (codigoCliente), 
        CONSTRAINT fk_factura_servicio FOREIGN KEY (id) REFERENCES facturas (id),
        CONSTRAINT fk_codigo_cliente FOREIGN KEY (codigoCliente) REFERENCES clientes (codigo)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;    
    
    
CREATE
    TABLE serviciosbrindadosfacturados
    (
        id bigint NOT NULL AUTO_INCREMENT,
        codigo VARCHAR(30),
        fecha DATE,
        cantidad VARCHAR(10),
        descripcion VARCHAR(400) NOT NULL,
        importeUnitario DOUBLE NOT NULL,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
    
    
CREATE
    TABLE prestacionesBrindadasFacturadas
    (
        id bigint NOT NULL AUTO_INCREMENT,
        fecha DATE NOT NULL,
        codigo VARCHAR(30),
        cantidad VARCHAR(10) NOT NULL,
        codPrestacionBrindada bigint,
		idPacienteFacturado bigint,
        importeUnitario DOUBLE NOT NULL,
        PRIMARY KEY (id), 
		INDEX idx_idpbf (id), 
        INDEX idx_codPrestacion_pbf (codPrestacionBrindada),
        INDEX idx_codPaciente_pbf (idPacienteFacturado), 
        CONSTRAINT fk_prestacion_pbf FOREIGN KEY (codPrestacionBrindada) REFERENCES prestacionesbrindadas (codigo),
        CONSTRAINT fk_paciente_pbf FOREIGN KEY (idPacienteFacturado) REFERENCES pacientes (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;    
    
   
    
CREATE
    TABLE asociacionServiciosFacturados
    (
        id_factura bigint NOT NULL,
        id_servicioFacturado bigint NOT NULL,
        PRIMARY KEY (id_factura, id_servicioFacturado),
        INDEX idx_factura (id_factura), 
        INDEX idx_servicio_brindado (id_servicioFacturado), 
        CONSTRAINT fk_id_factura_asf FOREIGN KEY (id_factura) REFERENCES facturas (id),
        CONSTRAINT fk_id_servicio_asf FOREIGN KEY (id_servicioFacturado) REFERENCES serviciosBrindadosFacturados (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;

    
CREATE
    TABLE asociacionPrestacionesBrindadasFacturadas
    (
        id_factura bigint NOT NULL,
        id_prestacionBrindadaFacturada bigint NOT NULL,
        PRIMARY KEY (id_factura, id_prestacionBrindadaFacturada),
        INDEX idx_factura (id_factura), 
        INDEX idx_pbf (id_prestacionBrindadaFacturada), 
        CONSTRAINT fk_id_factura FOREIGN KEY (id_factura) REFERENCES facturas (id),
        CONSTRAINT fk_id_pbf FOREIGN KEY (id_prestacionBrindadaFacturada) REFERENCES prestacionesBrindadasFacturadas (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    
    
-- TABLA DE BACKUPS --------------------------------------------------------

CREATE
    TABLE hemobilling.backupsRealizados
    (
        id BIGINT NOT NULL AUTO_INCREMENT,
        fechaRealizado DATETIME NOT NULL, 
        PRIMARY KEY (id),
        INDEX id_backup (id)  
    )
    ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    