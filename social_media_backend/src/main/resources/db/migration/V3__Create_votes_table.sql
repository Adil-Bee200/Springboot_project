CREATE TABLE IF NOT EXISTS votes (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    direction INTEGER NOT NULL CHECK (direction IN (1, -1)),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);