DELETE FROM books;
DELETE FROM users;

INSERT INTO books (
  id, isbn, title, author_name, category,
  available_quantity, total_quantity, created_at, updated_at
) VALUES
    ('book-001', '9780132350884', 'Clean Code', 'Robert C. Martin', 'Programming', 3, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('book-002', '9780321125215', 'Domain-Driven Design', 'Eric Evans', 'Architecture', 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('book-003', '9780201633610', 'Design Patterns', 'Gang of Four', 'Programming', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('book-004', '9780134685991', 'Effective Java', 'Joshua Bloch', 'Programming', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('book-005', '9780596007126', 'Head First Design Patterns', 'Eric Freeman', 'Programming', 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Usuários de exemplo
INSERT INTO users (id, name, email, cpf, user_type, created_at) 
VALUES 
    ('user-001', 'João Silva', 'joao.silva@email.com', '12345678901', 'STUDENT', CURRENT_TIMESTAMP),
    ('user-002', 'Maria Santos', 'maria.santos@email.com', '98765432109', 'PROFESSOR', CURRENT_TIMESTAMP),
    ('user-003', 'Pedro Oliveira', 'pedro.oliveira@email.com', '11122233344', 'COMMON', CURRENT_TIMESTAMP);