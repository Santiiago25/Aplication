-- Insertar permisos
INSERT INTO permissions (name) VALUES
('CREATE'),
('READ'),
('UPDATE'),
('DELETE'),
('REFACTOR');

-- Insertar roles
INSERT INTO roles (role_name) VALUES
('ADMIN'),
('USER'),
('INVITED'),
('DEVELOPER');

-- Asignar permisos a roles (ejemplo para el rol ADMIN)
INSERT INTO role_permissions (role_id, permission_id)
SELECT
    (SELECT id FROM roles WHERE role_name = 'ADMIN' LIMIT 1),
    permissions.id
FROM permissions
WHERE permissions.name IN ('CREATE', 'READ', 'UPDATE', 'DELETE', 'REFACTOR');

-- Insertar empresa
INSERT INTO companie (nit, company, manager, email, phone, address, department, municipality)
VALUES
(123456, 'MyCompany', 'Manager Name', 'manager@company.com', '123456789', 'Company Address', 'Company Department', 'Company Municipality');

-- Insertar usuario con rol de administrador
INSERT INTO users (username, password, is_enable, account_no_expired, account_no_locked, credential_no_expired, estado, company_id, name, last_name, type_document, num_document, phone, address)
VALUES
('admin', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true, true, 'active', (SELECT id FROM companie WHERE company = 'MyCompany' LIMIT 1), 'Admin', 'User', 'Passport', '123456789', '555-1234', '1234 Elm Street');

-- Verificar si el usuario fue creado correctamente
SELECT id FROM users WHERE username = 'admin';

-- Asegúrate de que el usuario fue creado correctamente y tiene un ID asignado
DO $$
DECLARE
    user_id INTEGER;
    role_id INTEGER;
BEGIN
    -- Obtener el ID del usuario
    SELECT id INTO user_id FROM users WHERE username = 'admin' LIMIT 1;

    -- Obtener el ID del rol de administrador
    SELECT id INTO role_id FROM roles WHERE role_name = 'ADMIN' LIMIT 1;

    -- Insertar la relación usuario-rol
    IF user_id IS NOT NULL AND role_id IS NOT NULL THEN
        INSERT INTO user_roles (user_id, role_id) VALUES (user_id, role_id);
    END IF;
END $$;
