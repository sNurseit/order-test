
CREATE TABLE users (
                       username TEXT PRIMARY KEY,
                       password TEXT NOT NULL,
                       enable BOOLEAN NOT NULL
);

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                       user_id TEXT NOT NULL,
                       role_id BIGINT NOT NULL,
                       PRIMARY KEY (user_id, role_id),
                       FOREIGN KEY (user_id) REFERENCES users(username),
                       FOREIGN KEY (role_id) REFERENCES roles(id)
);


INSERT INTO roles (name) VALUES
                             ('ROLE_USER'),
                             ('ROLE_ADMIN'),
                             ('ROLE_MANAGER');
