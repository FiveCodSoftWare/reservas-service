CREATE TABLE tbl_profesional (
                                 pro_id VARCHAR(36) NOT NULL PRIMARY KEY,
                                 pro_nombres VARCHAR(100) NOT NULL,
                                 pro_apellidos VARCHAR(100) NOT NULL,
                                 pro_especialidad VARCHAR(100) NOT NULL,
                                 pro_estado_activo BIT NOT NULL DEFAULT 1
);

CREATE TABLE tbl_cliente (
                             cli_id VARCHAR(36) NOT NULL PRIMARY KEY,
                             cli_nombres VARCHAR(100) NOT NULL,
                             cli_apellidos VARCHAR(100) NOT NULL,
                             cli_email VARCHAR(150) NOT NULL,
                             cli_telefono VARCHAR(20) NOT NULL,
                             cli_estado_activo BIT NOT NULL DEFAULT 1,
                             CONSTRAINT uq_cliente_email UNIQUE (cli_email)
);

CREATE TABLE tbl_horario_disponible (
                                        hor_id VARCHAR(36) NOT NULL PRIMARY KEY,
                                        hor_profesional_id VARCHAR(36) NOT NULL,
                                        hor_fecha DATE NOT NULL,
                                        hor_hora_inicio TIME NOT NULL,
                                        hor_hora_fin TIME NOT NULL,
                                        hor_estado BIT NOT NULL DEFAULT 1,
                                        CONSTRAINT fk_horario_profesional FOREIGN KEY (hor_profesional_id)
                                            REFERENCES tbl_profesional(pro_id)
);

CREATE TABLE tbl_reserva (
                             res_id VARCHAR(36) NOT NULL PRIMARY KEY,
                             res_fecha DATE NOT NULL,
                             res_hora_inicio TIME NOT NULL,
                             res_hora_fin TIME NOT NULL,
                             res_cliente_id VARCHAR(36) NOT NULL,
                             res_profesional_id VARCHAR(36) NOT NULL,
                             res_estado ENUM('CREADA','CANCELADA','COMPLETADA') NOT NULL DEFAULT 'CREADA',
                             CONSTRAINT fk_reserva_cliente FOREIGN KEY (res_cliente_id)
                                 REFERENCES tbl_cliente(cli_id),
                             CONSTRAINT fk_reserva_profesional FOREIGN KEY (res_profesional_id)
                                 REFERENCES tbl_profesional(pro_id)
);

CREATE INDEX idx_horario_pro_fecha ON tbl_horario_disponible(hor_profesional_id, hor_fecha);
CREATE INDEX idx_horario_estado ON tbl_horario_disponible(hor_estado);
CREATE INDEX idx_reserva_pro_fecha ON tbl_reserva(res_profesional_id, res_fecha);
CREATE INDEX idx_reserva_cliente ON tbl_reserva(res_cliente_id);
CREATE INDEX idx_reserva_estado ON tbl_reserva(res_estado);