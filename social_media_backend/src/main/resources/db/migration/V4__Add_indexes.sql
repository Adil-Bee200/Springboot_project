CREATE INDEX IF NOT EXISTS idx_posts_owner_id ON posts(owner_id);
CREATE INDEX IF NOT EXISTS idx_votes_user_id ON votes(user_id);
CREATE INDEX IF NOT EXISTS idx_votes_post_id ON votes(post_id);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email); 